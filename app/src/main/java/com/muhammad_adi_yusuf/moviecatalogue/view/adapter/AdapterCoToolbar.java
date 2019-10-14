package com.muhammad_adi_yusuf.moviecatalogue.view.adapter;

import android.app.Activity;
import android.content.Context;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.muhammad_adi_yusuf.moviecatalogue.R;

public class AdapterCoToolbar {
    private Context conText;

    public AdapterCoToolbar(Context conText){
        this.conText = conText;
    }

    public void setColl(final String title) {

        final CollapsingToolbarLayout coolToolbar = ((Activity)conText).findViewById(R.id.toolbar_collapsing);
        coolToolbar.setTitle(" ");
        AppBarLayout appBarlayout = ((Activity)conText).findViewById(R.id.al_activity_detail);
        appBarlayout.setExpanded(true);

        appBarlayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarlayout, int verticalValue) {
                if (scrollRange == -1) {
                    scrollRange = appBarlayout.getTotalScrollRange();
                }
                if (scrollRange + verticalValue == 0) {
                    coolToolbar.setTitle(title);
                    isShow = true;
                } else if (isShow) {
                    coolToolbar.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }

}
