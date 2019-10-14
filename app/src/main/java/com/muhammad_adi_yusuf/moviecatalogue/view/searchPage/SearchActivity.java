package com.muhammad_adi_yusuf.moviecatalogue.view.searchPage;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.muhammad_adi_yusuf.moviecatalogue.R;
import com.muhammad_adi_yusuf.moviecatalogue.model.restWebService.pojo.MovieCatalogueResultsItem;
import com.muhammad_adi_yusuf.moviecatalogue.view.adapter.AdapterSearchRecyclerView;
import com.muhammad_adi_yusuf.moviecatalogue.view.detailPage.DetailActivity;
import com.muhammad_adi_yusuf.moviecatalogue.view.helper.ErrorNotificationLayout;
import com.muhammad_adi_yusuf.moviecatalogue.view.helper.IcsRecyclerView;
import com.muhammad_adi_yusuf.moviecatalogue.viewmodel.SearchViewModel;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private AdapterSearchRecyclerView adapter;
    private SearchViewModel viewModel;
    private RecyclerView recyclerView;
    private ErrorNotificationLayout errorLayout;
    private Boolean value;

    private ArrayList<MovieCatalogueResultsItem> searchListView = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

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
        Toolbar tbScroll = findViewById(R.id.tb_activity_search);
        setSupportActionBar(tbScroll);
        //Preference
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String keyPreference = getString(R.string.key_movie);
        value = preferences.getBoolean(keyPreference, true);
        //viewModel
        viewModel = ViewModelProviders.of(this).get(SearchViewModel.class);
        viewModel.getSearch().observe(this, getData);
        //adapter
        adapter = new AdapterSearchRecyclerView(this, searchListView, value);
        //recyclerView
        recyclerView = findViewById(R.id.rv_search);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        //ErrorLayout
        errorLayout = new ErrorNotificationLayout(this);
        errorLayout.initialization(false);

    }

    private void setAndGetData(String text) {
        viewModel.setSearch(getString(R.string.idLanguage), text, value);
        viewModel.getSearch().observe(this, getData);
    }

    private Observer<List<MovieCatalogueResultsItem>> getData = new Observer<List<MovieCatalogueResultsItem>>() {
        @Override
        public void onChanged(List<MovieCatalogueResultsItem> searchList) {
            if (searchList != null) {
                adapter.setData(searchList);
                errorLayout.notification(false);
                if (searchList.isEmpty()) {
                    errorLayout.setAndViewMessage(getString(R.string.option1), true);
                }
            } else {
                adapter.setClear();
                errorLayout.setAndViewMessage(getString(R.string.option3), true);
            }
        }
    };

    private void clickRecyclerView() {
        IcsRecyclerView.addTo(recyclerView).setOnItemClickListener(new IcsRecyclerView.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {

                MovieCatalogueResultsItem iTem = searchListView.get(position);
                int idItem = iTem.getId();
                String type;
                if (value) {
                    type = "Movie";
                } else {
                    type = "Tv Series";
                }
                Intent intentJump = new Intent(getBaseContext(), DetailActivity.class);
                intentJump.putExtra("idItem", idItem);
                intentJump.putExtra("type", type);
                startActivity(intentJump);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);

        final SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        SearchView searchView = (SearchView) (menu.findItem(R.id.itmSearch2)).getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.onActionViewExpanded();

        if (searchManager != null) {

            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
            searchView.setQueryHint(getResources().getString(R.string.text_search));
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    errorLayout.progress(true);
                    setAndGetData(query);
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    return false;
                }
            });
        }

        return super.onCreateOptionsMenu(menu);
    }

}
