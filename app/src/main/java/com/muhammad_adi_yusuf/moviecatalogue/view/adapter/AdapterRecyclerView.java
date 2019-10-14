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

public class AdapterRecyclerView extends RecyclerView.Adapter<AdapterRecyclerView.gridViewHolder> {
    private Context context;
    private List<MovieCatalogueResultsItem> resultsItem;

    public void setData(List<MovieCatalogueResultsItem> items) {
        resultsItem.addAll(items);
        notifyDataSetChanged();
    }

    public AdapterRecyclerView(Context conText, List<MovieCatalogueResultsItem> dataList) {
        this.context = conText;
        this.resultsItem = dataList;
    }

    @NonNull
    @Override
    public AdapterRecyclerView.gridViewHolder onCreateViewHolder(@NonNull ViewGroup paRent, int viewType) {
        View itemRow = LayoutInflater.from(context).inflate(R.layout.rv_item, paRent, false);
        return new AdapterRecyclerView.gridViewHolder(itemRow);
    }

    @Override
    public void onBindViewHolder(@NonNull final AdapterRecyclerView.gridViewHolder gridHolder, final int position) {

        MovieCatalogueResultsItem item = resultsItem.get(position);
        final String title = item.getTitle();
        final String release = item.getReleaseDate();
        final double rate = item.getVoteAverage();
        final String image = "https://image.tmdb.org/t/p/w185" + item.getPosterPath();

        gridHolder.dataTitle.setText(title);
        gridHolder.dataRelease.setText(release);
        gridHolder.dataRate.setText(String.valueOf(rate));

        Glide.with(context)
                .load(image)
                .apply(new RequestOptions().override(185, 278))
                .into(gridHolder.dataImage);
    }

    @Override
    public int getItemCount() {
        return resultsItem.size();
    }

    class gridViewHolder extends RecyclerView.ViewHolder {
        ImageView dataImage;
        TextView dataTitle, dataRelease, dataRate;

        gridViewHolder(@NonNull View itemView) {
            super(itemView);
            dataImage = itemView.findViewById(R.id.iv_image);
            dataTitle = itemView.findViewById(R.id.tv_title);
            dataRelease = itemView.findViewById(R.id.tv_release);
            dataRate = itemView.findViewById(R.id.tv_rating);
        }
    }
}
