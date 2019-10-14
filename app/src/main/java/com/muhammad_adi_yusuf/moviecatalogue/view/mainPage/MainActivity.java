package com.muhammad_adi_yusuf.moviecatalogue.view.mainPage;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.muhammad_adi_yusuf.moviecatalogue.R;
import com.muhammad_adi_yusuf.moviecatalogue.view.adapter.AdapterViewPager;
import com.muhammad_adi_yusuf.moviecatalogue.view.favoritePage.FavoriteActivity;
import com.muhammad_adi_yusuf.moviecatalogue.view.searchPage.SearchActivity;
import com.muhammad_adi_yusuf.moviecatalogue.view.settingPage.SettingActivity;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //declaration scroll toolbar
        Toolbar tbScroll = findViewById(R.id.tb_activity_main);
        setSupportActionBar(tbScroll);

        //TabLayout and view pager
        TabLayout tabLayout = findViewById(R.id.tl_activity_main);
        ViewPager viePager = findViewById(R.id.vp_activity_main);

        //declaration adapter view pager and list
        AdapterViewPager adapter = new AdapterViewPager(getSupportFragmentManager(), 2);
        adapter.AddFragment(new MovieFragment(), getString(R.string.tab1));
        adapter.AddFragment(new TvFragment(), getString(R.string.tab2));

        //set adapter viewpager dan set view pager
        viePager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viePager);

        //action bar
        ActionBar actionBar = getSupportActionBar();
        Objects.requireNonNull(actionBar).setElevation(0);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.itmFavorite) {
            Intent newIntent = new Intent(getBaseContext(), FavoriteActivity.class);
            startActivity(newIntent);
        } else if (item.getItemId() == R.id.itmSetting) {
            Intent newIntent = new Intent(getBaseContext(), SettingActivity.class);
            startActivity(newIntent);
        }else if (item.getItemId() == R.id.itmSearch) {
            Intent newIntent = new Intent(getBaseContext(), SearchActivity.class);
            startActivity(newIntent);
        }
        return super.onOptionsItemSelected(item);
    }

}
