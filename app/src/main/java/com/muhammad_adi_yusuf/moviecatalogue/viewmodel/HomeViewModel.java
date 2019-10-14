package com.muhammad_adi_yusuf.moviecatalogue.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.muhammad_adi_yusuf.moviecatalogue.model.repository.ApiRepository;
import com.muhammad_adi_yusuf.moviecatalogue.model.restWebService.pojo.MovieCatalogueResultsItem;

import java.util.List;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<List<MovieCatalogueResultsItem>> movieList;
    private MutableLiveData<List<MovieCatalogueResultsItem>> tvList;

    public void setMovie(String language) {
        if (movieList != null) {
            return;
        }
        ApiRepository repoMovie = ApiRepository.getApiRepo();
        movieList = new MutableLiveData<>();
        movieList = repoMovie.getMovieList(language);
    }

    public LiveData<List<MovieCatalogueResultsItem>> getMovie() {
        return movieList;
    }

    public void setTv(String language) {
        if (tvList != null) {
            return;
        }
        ApiRepository repoTv = ApiRepository.getApiRepo();
        tvList = new MutableLiveData<>();
        tvList = repoTv.getTvList(language);
    }

    public LiveData<List<MovieCatalogueResultsItem>> getTv() {
        return tvList;
    }
}
