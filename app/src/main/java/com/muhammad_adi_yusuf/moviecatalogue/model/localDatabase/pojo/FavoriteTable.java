package com.muhammad_adi_yusuf.moviecatalogue.model.localDatabase.pojo;


import android.database.Cursor;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import static android.provider.BaseColumns._ID;

@Entity(tableName = FavoriteTable.TABLE_NAME)
public class FavoriteTable {

    public static final String TABLE_NAME = "favorite";
    public static final String COLUMN_NUMBER = _ID;
    public static final String COLUMN_ID_ITEM = "id";
    public static final String COLUMN_TYPE = "type";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_VOTE = "voteAverage";
    private static final String COLUMN_RELEASE = "release_date";
    private static final String COLUMN_PATH_POSTER = "path";
    private static final String COLUMN_PATH_BACKGROUND = "path2";

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(index = true, name = COLUMN_NUMBER)
    private int number;

    @ColumnInfo(name = COLUMN_ID_ITEM)
    private int id;

    @ColumnInfo(name = COLUMN_TYPE)
    private String type;

    @ColumnInfo(name = COLUMN_TITLE)
    private String title;

    @ColumnInfo(name = COLUMN_VOTE)
    private double voteAverage;

    @ColumnInfo(name = COLUMN_RELEASE)
    private String release_date;

    @ColumnInfo(name = COLUMN_PATH_POSTER)
    private String path;

    @ColumnInfo(name = COLUMN_PATH_BACKGROUND)
    private String path2;

    public FavoriteTable(int id, String type, String title, double voteAverage, String release_date, String path, String path2) {
        this.id = id;
        this.type = type;
        this.title = title;
        this.voteAverage = voteAverage;
        this.release_date = release_date;
        this.path = path;
        this.path2 = path2;
    }

    public FavoriteTable(Cursor cursor) {
        this.number = getClmInt(cursor, COLUMN_NUMBER);
        this.id = getClmInt(cursor, COLUMN_ID_ITEM);
        this.type = getClmStr(cursor, COLUMN_TYPE);
        this.title = getClmStr(cursor, COLUMN_TITLE);
        this.voteAverage = getClmDbl(cursor);
        this.release_date = getClmStr(cursor, COLUMN_RELEASE);
        this.path = getClmStr(cursor, COLUMN_PATH_POSTER);
        this.path2 = getClmStr(cursor, COLUMN_PATH_BACKGROUND);
    }

    private static String getClmStr(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndex(columnName));
    }

    private static int getClmInt(Cursor cursor, String columnName) {
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }
    private static double getClmDbl(Cursor cursor) {
        return cursor.getDouble(cursor.getColumnIndex(FavoriteTable.COLUMN_VOTE));
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public String getRelease_date() {
        return release_date;
    }

    public String getPath() {
        return path;
    }

    public String getPath2() {
        return path2;
    }
}
