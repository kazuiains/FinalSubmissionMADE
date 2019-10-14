package com.muhammad_adi_yusuf.moviecatalogue.model.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.muhammad_adi_yusuf.moviecatalogue.model.restWebService.network.ConfigRetrofit;
import com.muhammad_adi_yusuf.moviecatalogue.model.restWebService.network.MovieDbService;
import com.muhammad_adi_yusuf.moviecatalogue.model.restWebService.pojo.MovieCatalogueResponse;
import com.muhammad_adi_yusuf.moviecatalogue.model.restWebService.pojo.MovieCatalogueResultsItem;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.muhammad_adi_yusuf.moviecatalogue.BuildConfig.DBAPIKEY;

public class ApiRepository {

    private MovieDbService apiData;
    private static ApiRepository newRepo;

    private MutableLiveData<List<MovieCatalogueResultsItem>> movies;
    private MutableLiveData<List<MovieCatalogueResultsItem>> series;
    private MutableLiveData<List<MovieCatalogueResultsItem>> search;

    public static ApiRepository getApiRepo() {
        if (newRepo == null) {
            newRepo = new ApiRepository();
        }
        return newRepo;
    }

    private ApiRepository() {
        apiData = ConfigRetrofit.serVice(MovieDbService.class);
    }

    //Get ALl Movie Data
    public MutableLiveData<List<MovieCatalogueResultsItem>> getMovieList(String language) {

        movies = new MutableLiveData<>();
        apiData.getMovieList(DBAPIKEY, language).enqueue(new Callback<MovieCatalogueResponse>() {

            @Override
            public void onResponse(@NonNull Call<MovieCatalogueResponse> call, @NonNull Response<MovieCatalogueResponse> response) {
                if (response.isSuccessful()) {
                    MovieCatalogueResponse iTem = response.body();
                    assert iTem != null;
                    movies.postValue(iTem.getResults());
                }
            }

            @Override
            public void onFailure(@NonNull Call<MovieCatalogueResponse> call, @NonNull Throwable t) {
                movies.postValue(null);
            }
        });
        return movies;
    }

    //Get All Tv Data
    public MutableLiveData<List<MovieCatalogueResultsItem>> getTvList(String language) {

        series = new MutableLiveData<>();
        apiData.getTvList(DBAPIKEY, language).enqueue(new Callback<MovieCatalogueResponse>() {

            @Override
            public void onResponse(@NonNull Call<MovieCatalogueResponse> call, @NonNull Response<MovieCatalogueResponse> response) {
                if (response.isSuccessful()) {
                    MovieCatalogueResponse iTem = response.body();
                    assert iTem != null;
                    series.postValue(iTem.getResults());
                }
            }

            @Override
            public void onFailure(@NonNull Call<MovieCatalogueResponse> call, @NonNull Throwable t) {
                series.postValue(null);
            }
        });
        return series;
    }

    //Get Movie Detail
    public MutableLiveData<List<MovieCatalogueResultsItem>> getMovieDetail(String language, int idItem) {

        movies = new MutableLiveData<>();
        apiData.getDetailMovie(idItem, DBAPIKEY, language).enqueue(new Callback<MovieCatalogueResultsItem>() {

            @Override
            public void onResponse(@NonNull Call<MovieCatalogueResultsItem> call, @NonNull Response<MovieCatalogueResultsItem> response) {
                if (response.isSuccessful()) {
                    movies.postValue(Collections.singletonList(response.body()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<MovieCatalogueResultsItem> call, @NonNull Throwable t) {
                movies.postValue(null);
            }
        });
        return movies;
    }

    //Get Tv Detail
    public MutableLiveData<List<MovieCatalogueResultsItem>> getTvDetail(String language, int idItem) {

        series = new MutableLiveData<>();
        apiData.getDetailTv(idItem, DBAPIKEY, language).enqueue(new Callback<MovieCatalogueResultsItem>() {

            @Override
            public void onResponse(@NonNull Call<MovieCatalogueResultsItem> call, @NonNull Response<MovieCatalogueResultsItem> response) {
                if (response.isSuccessful()) {
                    series.postValue(Collections.singletonList(response.body()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<MovieCatalogueResultsItem> call, @NonNull Throwable t) {
                series.postValue(null);
            }
        });
        return series;
    }

    //Get All Search Movie
    public MutableLiveData<List<MovieCatalogueResultsItem>> getSearchMovie(String language, String searchText) {

        search = new MutableLiveData<>();
        apiData.searchMovie(DBAPIKEY, language, searchText).enqueue(new Callback<MovieCatalogueResponse>() {

            @Override
            public void onResponse(@NonNull Call<MovieCatalogueResponse> call, @NonNull Response<MovieCatalogueResponse> response) {
                if (response.isSuccessful()) {
                    MovieCatalogueResponse iTem = response.body();
                    assert iTem != null;
                    search.postValue(iTem.getResults());
                }
            }

            @Override
            public void onFailure(@NonNull Call<MovieCatalogueResponse> call, @NonNull Throwable t) {
                search.postValue(null);
            }
        });
        return search;
    }

    //Get All Search Tv
    public MutableLiveData<List<MovieCatalogueResultsItem>> getSearchTv(String language, String searchText) {

        search = new MutableLiveData<>();
        apiData.searchTv(DBAPIKEY, language, searchText).enqueue(new Callback<MovieCatalogueResponse>() {

            @Override
            public void onResponse(@NonNull Call<MovieCatalogueResponse> call, @NonNull Response<MovieCatalogueResponse> response) {
                if (response.isSuccessful()) {
                    MovieCatalogueResponse iTem = response.body();
                    assert iTem != null;
                    search.postValue(iTem.getResults());
                }
            }

            @Override
            public void onFailure(@NonNull Call<MovieCatalogueResponse> call, @NonNull Throwable t) {
                search.postValue(null);
            }
        });
        return search;
    }

}
