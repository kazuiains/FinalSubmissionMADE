package com.muhammad_adi_yusuf.fav.model;

import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {

    private static final String AUTHORITY = "com.muhammad_adi_yusuf.moviecatalogue";
    private static final String SCHEME = "content";

    public static final class FavoriteColumns implements BaseColumns {
        private static final String TABLE_NAME = "favorite";
        static final String COLUMN_ID_ITEM = "id";
        static final String COLUMN_TYPE = "type";
        static final String COLUMN_TITLE = "title";
        static final String COLUMN_VOTE = "voteAverage";
        static final String COLUMN_RELEASE = "release_date";
        static final String COLUMN_PATH_POSTER = "path";
        static final String COLUMN_PATH_BACKGROUND = "path2";

        public static final Uri FAVORITE_URI = new Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build();
    }


}
