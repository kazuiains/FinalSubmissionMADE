package com.muhammad_adi_yusuf.fav.model;

import android.database.Cursor;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.muhammad_adi_yusuf.fav.model.DatabaseContract.FavoriteColumns.COLUMN_ID_ITEM;
import static com.muhammad_adi_yusuf.fav.model.DatabaseContract.FavoriteColumns.COLUMN_PATH_BACKGROUND;
import static com.muhammad_adi_yusuf.fav.model.DatabaseContract.FavoriteColumns.COLUMN_PATH_POSTER;
import static com.muhammad_adi_yusuf.fav.model.DatabaseContract.FavoriteColumns.COLUMN_RELEASE;
import static com.muhammad_adi_yusuf.fav.model.DatabaseContract.FavoriteColumns.COLUMN_TITLE;
import static com.muhammad_adi_yusuf.fav.model.DatabaseContract.FavoriteColumns.COLUMN_TYPE;
import static com.muhammad_adi_yusuf.fav.model.DatabaseContract.FavoriteColumns.COLUMN_VOTE;

public class MappingHelper {
    public static ArrayList<Favorite> toArray(Cursor favCursor) {
        ArrayList<Favorite> favoriteList = new ArrayList<>();

        while (favCursor.moveToNext()) {
            int number = favCursor.getInt(favCursor.getColumnIndexOrThrow(_ID));
            int id = favCursor.getInt(favCursor.getColumnIndexOrThrow(COLUMN_ID_ITEM));
            String type = favCursor.getString(favCursor.getColumnIndexOrThrow(COLUMN_TYPE));
            String title = favCursor.getString(favCursor.getColumnIndexOrThrow(COLUMN_TITLE));
            double voteAverage = favCursor.getDouble(favCursor.getColumnIndexOrThrow(COLUMN_VOTE));
            String release_date = favCursor.getString(favCursor.getColumnIndexOrThrow(COLUMN_RELEASE));
            String path = favCursor.getString(favCursor.getColumnIndexOrThrow(COLUMN_PATH_POSTER));
            String path2 = favCursor.getString(favCursor.getColumnIndexOrThrow(COLUMN_PATH_BACKGROUND));
            favoriteList.add(new Favorite(number, id, type, title, voteAverage, release_date, path,path2));
        }

        return favoriteList;
    }
}
