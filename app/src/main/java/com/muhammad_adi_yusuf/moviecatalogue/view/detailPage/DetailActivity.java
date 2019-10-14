package com.muhammad_adi_yusuf.moviecatalogue.view.detailPage;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.muhammad_adi_yusuf.moviecatalogue.R;
import com.muhammad_adi_yusuf.moviecatalogue.model.localDatabase.pojo.FavoriteTable;
import com.muhammad_adi_yusuf.moviecatalogue.model.restWebService.pojo.MovieCatalogueResultsItem;
import com.muhammad_adi_yusuf.moviecatalogue.view.adapter.AdapterCoToolbar;
import com.muhammad_adi_yusuf.moviecatalogue.viewmodel.DetailViewModel;
import com.muhammad_adi_yusuf.moviecatalogue.widget.FavoriteWidget;

import java.util.List;
import java.util.Objects;

import jp.wasabeef.glide.transformations.BlurTransformation;

public class DetailActivity extends AppCompatActivity {

    //variable
    private String type;
    private int idItem, idFav;

    //Not in Activity
    private DetailViewModel viewModel;
    private AdapterCoToolbar collapsingToolbar;
    private FavoriteTable itemDetail;

    //in Activity
    private Toolbar toolBar;
    private ProgressBar progressBar;
    private LinearLayout linearLayout;
    private ConstraintLayout constraintLayout;
    ImageView dataImage, dataBackground;
    TextView dataTitle1, dataTitle2, dataRelease, dataOverview, dataStatus, dataRate1, dataRate2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //initialization
        initialize();

        collapsingToolbar = new AdapterCoToolbar(this);
        setSupportActionBar(toolBar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        setAndGetViewModel();
    }

    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private Observer<List<FavoriteTable>> getCheck = new Observer<List<FavoriteTable>>() {
        @Override
        public void onChanged(List<FavoriteTable> dataFav) {
            if (dataFav.isEmpty()) {
                idFav = 0;
            } else {
                idFav = 1;
            }
        }
    };

    private Observer<List<MovieCatalogueResultsItem>> getData = new Observer<List<MovieCatalogueResultsItem>>() {
        @Override
        public void onChanged(List<MovieCatalogueResultsItem> detail) {
            if (detail != null) {

                MovieCatalogueResultsItem item = detail.get(0);

                String titleItem = item.getTitle();
                double voteItem = item.getVoteAverage();
                String releaseItem = item.getReleaseDate();
                String pathItem = item.getPosterPath();
                String pathItem2 = item.getBackdropPath();

                dataTitle1.setText(titleItem);
                dataTitle2.setText(titleItem);
                dataRate1.setText(String.valueOf(voteItem));
                dataRate2.setText(String.valueOf(voteItem));
                dataRelease.setText(releaseItem);
                dataOverview.setText(item.getOverview());
                dataStatus.setText(item.getStatus());

                String linkImage1 = "https://image.tmdb.org/t/p/w185" + pathItem;
                Glide.with(getBaseContext())
                        .load(linkImage1)
                        .apply(new RequestOptions().override(185, 278))
                        .into(dataImage);

                String linkImage2 = "https://image.tmdb.org/t/p/w1400_and_h450_face" + pathItem2;
                Glide.with(getBaseContext())
                        .load(linkImage2)
                        .apply(new RequestOptions().override(1300, 350))
                        .apply(RequestOptions.bitmapTransform(new BlurTransformation(10,2)))
                        .into(dataBackground);

                itemDetail = new FavoriteTable(idItem, type, titleItem, voteItem, releaseItem, pathItem, pathItem2);

                collapsingToolbar.setColl(titleItem);
                progressBar.setVisibility(View.GONE);
                constraintLayout.setVisibility(View.GONE);
            } else {
                progressBar.setVisibility(View.GONE);
                linearLayout.setVisibility(View.VISIBLE);
            }
        }

    };

    private void setAndGetViewModel() {
        viewModel.checkFav(idItem, type);
        viewModel.dataCheck().observe(Objects.requireNonNull(this), getCheck);
        viewModel.setDetail(idItem, getString(R.string.idLanguage), type);
        viewModel.getDetail().observe(Objects.requireNonNull(this), getData);
    }

    private void initialize() {
        viewModel = ViewModelProviders.of(this).get(DetailViewModel.class);
        progressBar = findViewById(R.id.progressBar);
        linearLayout = findViewById(R.id.ll_error);
        constraintLayout = findViewById(R.id.cl_progressbar);
        toolBar = findViewById(R.id.tb_activity_detail);

        idItem = getIntent().getIntExtra("idItem", 0);
        type = getIntent().getStringExtra("type");

        dataTitle1 = findViewById(R.id.tv_detail_title1);
        dataTitle2 = findViewById(R.id.tv_detail_title2);
        dataRelease = findViewById(R.id.tv_detail_release);
        dataOverview = findViewById(R.id.tv_detail_overview);
        dataStatus = findViewById(R.id.tv_detail_status);
        dataRate1 = findViewById(R.id.tv_detail_rate1);
        dataRate2 = findViewById(R.id.tv_detail_rate2);

        dataImage = findViewById(R.id.iv_detail_image);
        dataBackground = findViewById(R.id.iv_background_poster);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_favorite, menu);
        if (idFav == 0){
            menu.getItem(0).setIcon(R.drawable.favorite_no_active);
        }
        else {
            menu.getItem(0).setIcon(R.drawable.favorite_active);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.itmFavorite) {
            if (idFav == 0){
                viewModel.addFav(itemDetail);
                FavoriteWidget.changeWidget(getBaseContext());
                item.setIcon(R.drawable.favorite_active);
            }else {
                viewModel.delFav(itemDetail);
                FavoriteWidget.changeWidget(getBaseContext());
                item.setIcon(R.drawable.favorite_no_active);
            }
        }
        return super.onOptionsItemSelected(item);
    }

}
