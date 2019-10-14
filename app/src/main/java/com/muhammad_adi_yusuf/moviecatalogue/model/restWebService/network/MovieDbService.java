package com.muhammad_adi_yusuf.moviecatalogue.model.restWebService.network;

import com.muhammad_adi_yusuf.moviecatalogue.model.restWebService.pojo.MovieCatalogueResponse;
import com.muhammad_adi_yusuf.moviecatalogue.model.restWebService.pojo.MovieCatalogueResultsItem;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieDbService {

    @GET("discover/movie")
    Call<MovieCatalogueResponse> getMovieList(
            @Query("api_key") String apiKey,
            @Query("language") String language);

    @GET("movie/{id_item}")
    Call<MovieCatalogueResultsItem> getDetailMovie(
            @Path("id_item") int id,
            @Query("api_key") String apiKey,
            @Query("language") String language);

    @GET("discover/tv")
    Call<MovieCatalogueResponse> getTvList(
            @Query("api_key") String apiKey,
            @Query("language") String language);

    @GET("tv/{id_item}")
    Call<MovieCatalogueResultsItem> getDetailTv(
            @Path("id_item") int id,
            @Query("api_key") String apiKey,
            @Query("language") String language);

    @GET("search/movie")
    Call<MovieCatalogueResponse> searchMovie(
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("query") String search);

    @GET("search/tv")
    Call<MovieCatalogueResponse> searchTv(
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("query") String search);

    @GET("discover/movie")
    Call<MovieCatalogueResponse> reminderMovie(
            @Query("api_key") String apiKey,
            @Query("primary_release_date.gte") String gte,
            @Query("primary_release_date.lte") String lte);

}
