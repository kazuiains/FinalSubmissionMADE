package com.muhammad_adi_yusuf.moviecatalogue.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class FavoriteWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new RvFactoryFavorite(this.getApplicationContext());
    }
}
