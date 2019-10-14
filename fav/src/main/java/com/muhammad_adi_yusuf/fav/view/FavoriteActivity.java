package com.muhammad_adi_yusuf.fav.view;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.muhammad_adi_yusuf.fav.R;
import com.muhammad_adi_yusuf.fav.model.Favorite;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import static com.muhammad_adi_yusuf.fav.model.DatabaseContract.FavoriteColumns.FAVORITE_URI;
import static com.muhammad_adi_yusuf.fav.model.MappingHelper.toArray;

public class FavoriteActivity extends AppCompatActivity implements LoadCallBack {

    private AdapterFavorite adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        //adapter
        adapter = new AdapterFavorite(this);
        //recyclerView
        RecyclerView recyclerView = findViewById(R.id.rv_fav);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        HandlerThread handlerThread = new HandlerThread("dataObserver");
        handlerThread.start();

        Handler handler = new Handler(handlerThread.getLooper());

        DataObserver dataObs = new DataObserver(handler, this);

        getContentResolver().registerContentObserver(FAVORITE_URI, true, dataObs);
        loadData();
    }

    @Override
    public void postExecute(Cursor fav) {

        ArrayList<Favorite> listFav = toArray(fav);
        if (listFav.size() > 0) {
            adapter.setData(listFav);
        } else {
            Toast.makeText(this, "Don't Have Data", Toast.LENGTH_SHORT).show();
            adapter.setData(new ArrayList<Favorite>());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData(){
        new getData(this, this).execute();
    }

    private static class getData extends AsyncTask<Void, Void, Cursor> {
        private final WeakReference<Context> weakContext;
        private final WeakReference<LoadCallBack> weakCallback;

        private getData(Context context, LoadCallBack callback) {
            weakContext = new WeakReference<>(context);
            weakCallback = new WeakReference<>(callback);
        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            return weakContext.get().getContentResolver().query(FAVORITE_URI, null, null, null, null);
        }

        @Override
        protected void onPostExecute(Cursor data) {
            super.onPostExecute(data);
            weakCallback.get().postExecute(data);
        }

    }

    static class DataObserver extends ContentObserver {

        final Context context;

        DataObserver(Handler handler, Context context) {
            super(handler);
            this.context = context;
        }

        @Override
        public void onChange(boolean change) {
            super.onChange(change);
            new getData(context, (FavoriteActivity) context).execute();
        }
    }
}
