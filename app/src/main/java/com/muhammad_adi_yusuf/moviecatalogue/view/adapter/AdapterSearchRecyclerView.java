package com.muhammad_adi_yusuf.moviecatalogue.view.adapter;

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
import com.muhammad_adi_yusuf.moviecatalogue.R;
import com.muhammad_adi_yusuf.moviecatalogue.model.restWebService.pojo.MovieCatalogueResultsItem;

import java.util.List;

import jp.wasabeef.glide.transformations.BlurTransformation;

public class AdapterSearchRecyclerView extends RecyclerView.Adapter<AdapterSearchRecyclerView.listViewHolder> {
    private Context context;
    private List<MovieCatalogueResultsItem> resultsItem;
    private Boolean filterSearch;

    public void setData(List<MovieCatalogueResultsItem> items) {
        resultsItem.clear();
        resultsItem.addAll(items);
        notifyDataSetChanged();
    }

    public void setClear() {
        resultsItem.clear();
        notifyDataSetChanged();
    }

    public AdapterSearchRecyclerView(Context context, List<MovieCatalogueResultsItem> dataList, Boolean filterSearch) {
        this.context = context;
        this.resultsItem = dataList;
        this.filterSearch = filterSearch;
    }

    @NonNull
    @Override
    public AdapterSearchRecyclerView.listViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemRow = LayoutInflater.from(context).inflate(R.layout.rv_item_favorite, parent, false);
        return new AdapterSearchRecyclerView.listViewHolder(itemRow);
    }

    @Override
    public void onBindViewHolder(@NonNull final AdapterSearchRecyclerView.listViewHolder listHolder, final int position) {

        MovieCatalogueResultsItem item = resultsItem.get(position);

        String typeMedia;
        if (filterSearch) {
            typeMedia = "Movie";
        } else {
            typeMedia = "Tv Series";
        }

        final String title = item.getTitle();
        final String release = item.getReleaseDate();
        final String type = typeMedia;
        final Double rate = item.getVoteAverage();
        final String image = "https://image.tmdb.org/t/p/w185" + item.getPosterPath();
        final String image2 = "https://image.tmdb.org/t/p/w1400_and_h450_face" + item.getBackdropPath();

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
        return resultsItem.size();
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
