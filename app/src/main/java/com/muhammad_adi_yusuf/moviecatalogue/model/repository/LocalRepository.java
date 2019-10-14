package com.muhammad_adi_yusuf.moviecatalogue.model.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.muhammad_adi_yusuf.moviecatalogue.model.localDatabase.pojo.FavoriteTable;
import com.muhammad_adi_yusuf.moviecatalogue.model.localDatabase.room.FavoriteDao;
import com.muhammad_adi_yusuf.moviecatalogue.model.localDatabase.room.MovieDbDatabase;

import java.util.List;

public class LocalRepository {
    private FavoriteDao favDao;
    private LiveData<List<FavoriteTable>> favoriteList;

    public LocalRepository(Application application) {
        MovieDbDatabase database = MovieDbDatabase.getMovieDb(application);
        favDao = database.favDao();
    }

    //addFavorite
    public void addFavorite(FavoriteTable fav) {
        new addFav(favDao).execute(fav);
    }

    private static class addFav extends AsyncTask<FavoriteTable, Void, Void> {
        private FavoriteDao dao;

        private addFav(FavoriteDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(FavoriteTable... table) {
            dao.addFavorite(table[0]);
            return null;
        }

    }

    //delete Favorite Where id and type
    public void delFavDet(FavoriteTable fav) {
        new delFavDe(favDao).execute(fav);
    }

    private static class delFavDe extends AsyncTask<FavoriteTable, Void, Void> {
        private FavoriteDao dao;

        private delFavDe(FavoriteDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(FavoriteTable... table) {
            dao.delFavDetail(table[0].getId(), table[0].getType());
            return null;
        }

    }

    //deleteAllMovie
    public void delAllMovie() {
        new delAllMovie(favDao).execute();
    }

    private static class delAllMovie extends AsyncTask<Void, Void, Void> {
        private FavoriteDao dao;

        private delAllMovie(FavoriteDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            dao.deleteAllMovie();
            return null;
        }

    }

    //deleteAllTV
    public void delAllTv() {
        new delAllTv(favDao).execute();
    }

    private static class delAllTv extends AsyncTask<Void, Void, Void> {
        private FavoriteDao dao;

        private delAllTv(FavoriteDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            dao.deleteAllTv();
            return null;
        }

    }

    //deleteAllTV
    public void delAll() {
        new delAll(favDao).execute();
    }

    private static class delAll extends AsyncTask<Void, Void, Void> {
        private FavoriteDao dao;

        private delAll(FavoriteDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            dao.deleteAll();
            return null;
        }

    }

    //set and get favorite list
    public void setFav() {
        favoriteList = favDao.getFavorite();
    }

    public LiveData<List<FavoriteTable>> getFav() {
        return favoriteList;
    }

    //check
    public void checkFav(int id, String type) {
        favoriteList = new MutableLiveData<>();
        favoriteList = favDao.checkFav(id, type);
    }

    public LiveData<List<FavoriteTable>> dataCheck() {
        return favoriteList;
    }


}
