package com.muhammad_adi_yusuf.moviecatalogue.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.muhammad_adi_yusuf.moviecatalogue.model.localDatabase.pojo.FavoriteTable;
import com.muhammad_adi_yusuf.moviecatalogue.model.repository.LocalRepository;

import java.util.List;

public class FavoriteViewModel extends AndroidViewModel {

    private LocalRepository repository;
    private LiveData<List<FavoriteTable>> favorite;

    public FavoriteViewModel(@NonNull Application application) {
        super(application);
        repository = new LocalRepository(application);
        repository.setFav();
        favorite = repository.getFav();
    }

    public LiveData<List<FavoriteTable>> getAll() {
        return favorite;
    }

    public void delAllMov() {
        repository.delAllMovie();
    }

    public void delAllTv() {
        repository.delAllTv();
    }

    public void delAll() {
        repository.delAll();
    }
}