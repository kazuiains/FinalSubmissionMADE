package com.muhammad_adi_yusuf.moviecatalogue.model.localDatabase.room;

import android.database.Cursor;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.muhammad_adi_yusuf.moviecatalogue.model.localDatabase.pojo.FavoriteTable;

import java.util.List;

@Dao
public interface FavoriteDao {

    @Insert
    void addFavorite(FavoriteTable favoriteTable);

    @Query("DELETE FROM "+FavoriteTable.TABLE_NAME+" WHERE "+FavoriteTable.COLUMN_ID_ITEM+" = :id AND "+FavoriteTable.COLUMN_TYPE+" = :type")
    void delFavDetail(int id, String type);

    @Query("DELETE FROM "+FavoriteTable.TABLE_NAME+" WHERE "+FavoriteTable.COLUMN_TYPE+" ='Movie'")
    void deleteAllMovie();

    @Query("DELETE FROM "+FavoriteTable.TABLE_NAME+" WHERE "+FavoriteTable.COLUMN_TYPE+" ='TV Series'")
    void deleteAllTv();

    @Query("DELETE FROM "+FavoriteTable.TABLE_NAME)
    void deleteAll();

    @Query("SELECT * FROM "+FavoriteTable.TABLE_NAME+" ORDER BY "+FavoriteTable.COLUMN_NUMBER+" DESC")
    LiveData<List<FavoriteTable>> getFavorite();

    @Query("SELECT * FROM "+FavoriteTable.TABLE_NAME+" WHERE "+FavoriteTable.COLUMN_ID_ITEM+" = :id AND "+FavoriteTable.COLUMN_TYPE+" = :type")
    LiveData<List<FavoriteTable>> checkFav(int id, String type);

    @Query("SELECT * FROM "+FavoriteTable.TABLE_NAME+" ORDER BY "+FavoriteTable.COLUMN_NUMBER+" DESC")
    Cursor selectAll();

    @Query("SELECT * FROM "+FavoriteTable.TABLE_NAME+" ORDER BY "+FavoriteTable.COLUMN_NUMBER+" DESC")
    List<FavoriteTable> getWidget();

}
