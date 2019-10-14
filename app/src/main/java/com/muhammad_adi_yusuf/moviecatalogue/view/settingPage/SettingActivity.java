package com.muhammad_adi_yusuf.moviecatalogue.view.settingPage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.muhammad_adi_yusuf.moviecatalogue.R;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        getSupportFragmentManager().beginTransaction().add(R.id.setting, new SettingFragment()).commit();
    }
}
