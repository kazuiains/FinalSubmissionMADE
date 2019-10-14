package com.muhammad_adi_yusuf.moviecatalogue.widget;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.muhammad_adi_yusuf.moviecatalogue.R;
import com.muhammad_adi_yusuf.moviecatalogue.model.localDatabase.pojo.FavoriteTable;
import com.muhammad_adi_yusuf.moviecatalogue.model.localDatabase.room.FavoriteDao;
import com.muhammad_adi_yusuf.moviecatalogue.model.localDatabase.room.MovieDbDatabase;

import java.util.List;

public class RvFactoryFavorite implements RemoteViewsService.RemoteViewsFactory {

    private Context context;
    private List<FavoriteTable> list;

    RvFactoryFavorite(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        getData();
    }

    private void getData() {
        MovieDbDatabase database = MovieDbDatabase.getMovieDb(context);
        FavoriteDao favoriteDao = database.favDao();
        list = favoriteDao.getWidget();
    }

    @Override
    public void onDestroy() {
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public RemoteViews getViewAt(int i) {
        RemoteViews remote = new RemoteViews(context.getPackageName(), R.layout.widget_favorite_item);

        FavoriteTable item = list.get(i);

        final String title = item.getTitle();
        final String type = item.getType();
        final Double rate = item.getVoteAverage();
        final String image = "https://image.tmdb.org/t/p/w185" + item.getPath();

        try {
            Bitmap bitmap = Glide.with(context)
                    .asBitmap()
                    .load(image)
                    .submit().get();
            remote.setImageViewBitmap(R.id.iv_favorite_widget, bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }

        remote.setTextViewText(R.id.tv_widget_title, title);
        remote.setTextViewText(R.id.tv_widget_type, type);
        remote.setTextViewText(R.id.tv_widget_rate, (String.valueOf(rate)));

        Bundle extras = new Bundle();
        extras.putInt(FavoriteWidget.EXTRA_ID_ITEM, item.getId());
        extras.putString(FavoriteWidget.EXTRA_TYPE_ITEM, type);
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);

        remote.setOnClickFillInIntent(R.id.item_widget_fav, fillInIntent);
        return remote;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
