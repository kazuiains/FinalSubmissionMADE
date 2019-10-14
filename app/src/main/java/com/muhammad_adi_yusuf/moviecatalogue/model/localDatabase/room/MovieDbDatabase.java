package com.muhammad_adi_yusuf.moviecatalogue.model.localDatabase.room;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.muhammad_adi_yusuf.moviecatalogue.model.localDatabase.pojo.FavoriteTable;

@Database(entities = {FavoriteTable.class}, version = 1, exportSchema = false)
public abstract class MovieDbDatabase extends RoomDatabase {
    private static MovieDbDatabase movieDb;

    public abstract FavoriteDao favDao();

    public static synchronized MovieDbDatabase getMovieDb(Context context) {
        if (movieDb == null) {
            movieDb = Room.databaseBuilder(context.getApplicationContext(),
                    MovieDbDatabase.class, "movieDb_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return movieDb;
    }

    private static Callback roomCallback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new dbAsyncTask(movieDb).execute();
        }
    };

    private static class dbAsyncTask extends AsyncTask<Void, Void, Void> {
        FavoriteDao favDao;

        private dbAsyncTask(MovieDbDatabase db) {
            favDao = db.favDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }

    }
}
