package com.muhammad_adi_yusuf.moviecatalogue.view.helper;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.muhammad_adi_yusuf.moviecatalogue.R;

public class ErrorNotificationLayout {
    private ProgressBar progressBar;
    private LinearLayout linearLayout;
    private TextView headText, bodyText;
    private Context context;

    public ErrorNotificationLayout(Context context) {
        this.context = context;
    }

    public void initialization(Boolean firstProgress) {
        progressBar = ((Activity) context).findViewById(R.id.progressBar_er);
        linearLayout = ((Activity) context).findViewById(R.id.ll_error_er);
        headText = ((Activity) context).findViewById(R.id.head_er);
        bodyText = ((Activity) context).findViewById(R.id.body_er);

        if (firstProgress) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    public void initializationInFragment(Boolean firstProgress, View view) {
        progressBar = view.findViewById(R.id.progressBar_er);
        linearLayout = view.findViewById(R.id.ll_error_er);
        headText = view.findViewById(R.id.head_er);
        bodyText = view.findViewById(R.id.body_er);

        if (firstProgress) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    public void progress(Boolean state) {
        linearLayout.setVisibility(View.GONE);
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    public void notification(Boolean state) {
        progressBar.setVisibility(View.GONE);
        if (state) {
            linearLayout.setVisibility(View.VISIBLE);
        } else {
            linearLayout.setVisibility(View.GONE);
        }
    }

    public void settingNotification(String message) {
        if (message.equals("not_found")) {
            headText.setText(R.string.info);
            bodyText.setText(R.string.body_not_found);
        } else if (message.equals("favorite")) {
            headText.setText(R.string.info);
            bodyText.setText(R.string.body_favorite);
        } else {
            headText.setText(R.string.head_error);
            bodyText.setText(R.string.body_error);
        }
    }

    public void setAndViewMessage(String option, Boolean message) {
        if (option.equals("not_found")) {
            headText.setText(R.string.info);
            bodyText.setText(R.string.body_not_found);
        } else if (option.equals("favorite")) {
            headText.setText(R.string.info);
            bodyText.setText(R.string.body_favorite);
        } else {
            headText.setText(R.string.head_error);
            bodyText.setText(R.string.body_error);
        }

        progressBar.setVisibility(View.GONE);

        if (message) {
            linearLayout.setVisibility(View.VISIBLE);
        } else {
            linearLayout.setVisibility(View.GONE);
        }

    }


}
