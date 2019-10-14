package com.muhammad_adi_yusuf.moviecatalogue.view.favoritePage;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.muhammad_adi_yusuf.moviecatalogue.R;
import com.muhammad_adi_yusuf.moviecatalogue.model.localDatabase.pojo.FavoriteTable;
import com.muhammad_adi_yusuf.moviecatalogue.view.adapter.AdapterFavRecyclerView;
import com.muhammad_adi_yusuf.moviecatalogue.view.detailPage.DetailActivity;
import com.muhammad_adi_yusuf.moviecatalogue.view.helper.ErrorNotificationLayout;
import com.muhammad_adi_yusuf.moviecatalogue.view.helper.IcsRecyclerView;
import com.muhammad_adi_yusuf.moviecatalogue.viewmodel.FavoriteViewModel;
import com.muhammad_adi_yusuf.moviecatalogue.widget.FavoriteWidget;

import java.util.ArrayList;
import java.util.List;

public class FavoriteActivity extends AppCompatActivity {
    private AdapterFavRecyclerView adapter;
    private FavoriteViewModel viewModel;
    private RecyclerView recyclerView;
    private ErrorNotificationLayout errorLayout;

    private ArrayList<FavoriteTable> movieList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        initialize();
        clickRecyclerView();

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

    }

    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private void initialize() {

        //toolbar
        Toolbar tbScroll = findViewById(R.id.tb_activity_fav);
        setSupportActionBar(tbScroll);
        setTitle(getString(R.string.favorite));
        //viewModel
        viewModel = ViewModelProviders.of(this).get(FavoriteViewModel.class);
        viewModel.getAll().observe(this, getData);
        //adapter
        adapter = new AdapterFavRecyclerView(this, movieList);
        //recyclerView
        recyclerView = findViewById(R.id.rv_fav);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        //ErrorLayout
        errorLayout = new ErrorNotificationLayout(this);
        errorLayout.initialization(true);
    }

    private Observer<List<FavoriteTable>> getData = new Observer<List<FavoriteTable>>() {
        @Override
        public void onChanged(List<FavoriteTable> moviesItem) {
            errorLayout.settingNotification(getString(R.string.option2));
            if (moviesItem.isEmpty()) {
                adapter.data(moviesItem);
                errorLayout.notification(true);
            } else {
                movieList.clear();
                movieList.addAll(moviesItem);
                adapter.setData(moviesItem);
                errorLayout.notification(false);
            }
        }
    };

    private void clickRecyclerView() {
        IcsRecyclerView.addTo(recyclerView).setOnItemClickListener(new IcsRecyclerView.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {

                FavoriteTable iTem = movieList.get(position);
                int idItem = iTem.getId();
                String type = iTem.getType();
                Intent intentJump = new Intent(getBaseContext(), DetailActivity.class);
                intentJump.putExtra("idItem", idItem);
                intentJump.putExtra("type", type);
                startActivity(intentJump);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        menu.setGroupVisible(R.id.itmList, true);
        menu.getItem(3).setVisible(false);
        menu.getItem(4).setVisible(false);
        menu.getItem(5).setVisible(false);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_all:
                viewModel.delAll();
                FavoriteWidget.changeWidget(getBaseContext());
                return true;
            case R.id.delete_all_movie:
                viewModel.delAllMov();
                FavoriteWidget.changeWidget(getBaseContext());
                return true;
            case R.id.delete_all_tv:
                viewModel.delAllTv();
                FavoriteWidget.changeWidget(getBaseContext());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
