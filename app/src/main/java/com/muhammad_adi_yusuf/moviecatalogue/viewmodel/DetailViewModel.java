package com.muhammad_adi_yusuf.moviecatalogue.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.muhammad_adi_yusuf.moviecatalogue.model.localDatabase.pojo.FavoriteTable;
import com.muhammad_adi_yusuf.moviecatalogue.model.repository.ApiRepository;
import com.muhammad_adi_yusuf.moviecatalogue.model.repository.LocalRepository;
import com.muhammad_adi_yusuf.moviecatalogue.model.restWebService.pojo.MovieCatalogueResultsItem;

import java.util.List;

public class DetailViewModel extends AndroidViewModel {

    private MutableLiveData<List<MovieCatalogueResultsItem>> dataDetail;
    private LocalRepository repository;

    public DetailViewModel(@NonNull Application application) {
        super(application);
        repository = new LocalRepository(application);
    }

    //set data to check data
    public void checkFav(int id, String type) {
        repository.checkFav(id, type);
    }

    //get data check
    public LiveData<List<FavoriteTable>> dataCheck() {
        return repository.dataCheck();
    }

    //add favorite
    public void addFav(FavoriteTable fav) {
        repository.addFavorite(fav);
    }

    //delete favorite
    public void delFav(FavoriteTable fav) {
        repository.delFavDet(fav);
    }

    //set detail data
    public void setDetail(int idItem, String language, String typeItem) {
        if (dataDetail != null) {
            return;
        }
        ApiRepository repo = ApiRepository.getApiRepo();
        if (typeItem.equals("Movie")) {
            dataDetail = repo.getMovieDetail(language, idItem);
        } else {
            dataDetail = repo.getTvDetail(language, idItem);
        }
    }

    //get detail data
    public LiveData<List<MovieCatalogueResultsItem>> getDetail() {
        return dataDetail;
    }


}
