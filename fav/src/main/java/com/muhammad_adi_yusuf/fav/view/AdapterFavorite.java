package com.muhammad_adi_yusuf.fav.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.muhammad_adi_yusuf.fav.R;
import com.muhammad_adi_yusuf.fav.model.Favorite;

import java.util.ArrayList;

import jp.wasabeef.glide.transformations.BlurTransformation;

public class AdapterFavorite extends RecyclerView.Adapter<AdapterFavorite.listViewHolder> {

    private Context context;
    AdapterFavorite(Context context) {
        this.context = context;
    }


    private final ArrayList<Favorite> listFav = new ArrayList<>();

    void setData(ArrayList<Favorite> listFav) {
        this.listFav.clear();
        this.listFav.addAll(listFav);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AdapterFavorite.listViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemRow = LayoutInflater.from(context).inflate(R.layout.item_recycler, parent, false);
        return new AdapterFavorite.listViewHolder(itemRow);
    }

    @Override
    public void onBindViewHolder(@NonNull final AdapterFavorite.listViewHolder listHolder, final int position) {

        Favorite item = listFav.get(position);
        final String title = item.getTitle();
        final String release = item.getRelease_date();
        final String type = item.getType();
        final Double rate = item.getVoteAverage();
        final String image = "https://image.tmdb.org/t/p/w185" + item.getPath();
        final String image2 = "https://image.tmdb.org/t/p/w1400_and_h450_face" + item.getPath2();

        listHolder.dataTitle.setText(title);
        listHolder.dataRelease.setText(release);
        listHolder.dataType.setText(type);
        listHolder.dataRate.setText(String.valueOf(rate));

        Glide.with(context)
                .load(image)
                .apply(new RequestOptions().override(185, 278))
                .into(listHolder.dataImage);

        Glide.with(context)
                .load(image2)
                .apply(new RequestOptions().override(1400, 450))
                .apply(RequestOptions.bitmapTransform(new BlurTransformation(25, 3)))
                .into(listHolder.dataBackground);
    }

    @Override
    public int getItemCount() {
        return listFav.size();
    }

    class listViewHolder extends RecyclerView.ViewHolder {
        ImageView dataImage, dataBackground;
        TextView dataTitle, dataRelease, dataType, dataRate;

        listViewHolder(@NonNull View itemView) {
            super(itemView);
            dataImage = itemView.findViewById(R.id.iv_fav_poster);
            dataBackground = itemView.findViewById(R.id.iv_bg_fav);
            dataRate = itemView.findViewById(R.id.tv_fav_rate);
            dataTitle = itemView.findViewById(R.id.tv_fav_title);
            dataRelease = itemView.findViewById(R.id.tv_fav_release);
            dataType = itemView.findViewById(R.id.tv_fav_type);
        }
    }
}
