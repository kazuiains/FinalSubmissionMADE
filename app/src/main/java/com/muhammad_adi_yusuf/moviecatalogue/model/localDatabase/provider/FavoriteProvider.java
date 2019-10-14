package com.muhammad_adi_yusuf.moviecatalogue.model.localDatabase.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import androidx.annotation.NonNull;

import com.muhammad_adi_yusuf.moviecatalogue.model.localDatabase.pojo.FavoriteTable;
import com.muhammad_adi_yusuf.moviecatalogue.model.localDatabase.room.FavoriteDao;
import com.muhammad_adi_yusuf.moviecatalogue.model.localDatabase.room.MovieDbDatabase;

public class FavoriteProvider extends ContentProvider {

    public static final String AUTHORITY = "com.muhammad_adi_yusuf.moviecatalogue";
//    private static final String SCHEME = "content";

    private static final int FAVORITE_ALL = 1;
    private static final int FAVORITE_ID = 2;

    private FavoriteDao favoriteDao;

//    public static final Uri FAVORITE_URI = new Uri.Builder().scheme(SCHEME)
//            .authority(AUTHORITY)
//            .appendPath(FavoriteTable.TABLE_NAME)
//            .build();

    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uriMatcher.addURI(AUTHORITY, FavoriteTable.TABLE_NAME, FAVORITE_ALL);
        uriMatcher.addURI(AUTHORITY,
                FavoriteTable.TABLE_NAME + "/#",
                FAVORITE_ID);
    }

    public FavoriteProvider() {
    }

    @Override
    public boolean onCreate() {
        MovieDbDatabase database = MovieDbDatabase.getMovieDb(getContext());
        favoriteDao = database.favDao();
        return true;
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        Cursor cursor;
        switch (uriMatcher.match(uri)) {
            case FAVORITE_ALL:
                cursor = favoriteDao.selectAll();
                break;
            case FAVORITE_ID:
                cursor = null;
                break;
            default:
                cursor = null;
                break;
        }

        return cursor;
    }

    @Override
    public String getType(@NonNull Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
