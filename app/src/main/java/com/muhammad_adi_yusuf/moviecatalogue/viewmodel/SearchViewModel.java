package com.muhammad_adi_yusuf.moviecatalogue.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.muhammad_adi_yusuf.moviecatalogue.model.repository.ApiRepository;
import com.muhammad_adi_yusuf.moviecatalogue.model.restWebService.pojo.MovieCatalogueResultsItem;

import java.util.List;

public class SearchViewModel extends ViewModel {

    private MutableLiveData<List<MovieCatalogueResultsItem>> search = new MutableLiveData<>();

    public void setSearch(String language, String searchText, Boolean filter) {

        ApiRepository repoSearch = ApiRepository.getApiRepo();
        if (filter) {
            search = repoSearch.getSearchMovie(language, searchText);
        } else {
            search = repoSearch.getSearchTv(language, searchText);
        }
    }

    public LiveData<List<MovieCatalogueResultsItem>> getSearch() {
        return search;
    }
}
