package com.muhammad_adi_yusuf.fav.model;

public class Favorite {
    private int number;
    private int id;
    private String type;
    private String title;
    private double voteAverage;
    private String release_date;
    private String path;
    private String path2;

    Favorite(int number, int id, String type, String title, double voteAverage, String release_date, String path, String path2) {
        this.number = number;
        this.id = id;
        this.type = type;
        this.title = title;
        this.voteAverage = voteAverage;
        this.release_date = release_date;
        this.path = path;
        this.path2 = path2;
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

    public int getNumber() {
        return number;
    }

    public int getId() {
        return id;
    }
}
