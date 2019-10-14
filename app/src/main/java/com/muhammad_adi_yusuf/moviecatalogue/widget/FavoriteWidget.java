package com.muhammad_adi_yusuf.moviecatalogue.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

import androidx.annotation.NonNull;

import com.muhammad_adi_yusuf.moviecatalogue.R;
import com.muhammad_adi_yusuf.moviecatalogue.view.detailPage.DetailActivity;

/**
 * Implementation of App Widget functionality.
 */
public class FavoriteWidget extends AppWidgetProvider {
    public static final String ACTION_OPEN = "com.android.muhammad_adi_yusuf.moviecatalogue.WIDGET_OPEN_DETAIL";
    public static final String EXTRA_ID_ITEM = "com.android.muhammad_adi_yusuf.moviecatalogue.EXTRA_ID_ITEM";
    public static final String EXTRA_TYPE_ITEM = "com.android.muhammad_adi_yusuf.moviecatalogue.EXTRA_TYPE_ITEM";

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        Intent intentService = new Intent(context, FavoriteWidgetService.class);
        intentService.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        intentService.setData(Uri.parse(intentService.toUri(Intent.URI_INTENT_SCHEME)));

        appWidgetManager.updateAppWidget(appWidgetId, null);

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.favorite_widget);
        views.setRemoteAdapter(R.id.sv_favorite, intentService);
        views.setEmptyView(R.id.sv_favorite, R.id.nothing_favorite);

        appWidgetManager.updateAppWidget(appWidgetId, views);

        Intent intentFavorite = new Intent(context, FavoriteWidget.class);
        intentFavorite.setAction(FavoriteWidget.ACTION_OPEN);
        intentFavorite.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        intentService.setData(Uri.parse(intentFavorite.toUri(Intent.URI_INTENT_SCHEME)));

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intentFavorite, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setPendingIntentTemplate(R.id.sv_favorite, pendingIntent);

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    public static void changeWidget(@NonNull Context context) {
        AppWidgetManager widManager = AppWidgetManager.getInstance(context);
        int[] widId = widManager.getAppWidgetIds(new ComponentName(context, FavoriteWidget.class));
        widManager.notifyAppWidgetViewDataChanged(widId, R.id.sv_favorite);

        Intent update = new Intent(context, FavoriteWidget.class);
        update.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        update.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, widId);
        context.sendBroadcast(update);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (intent.getAction() != null) {
            if (intent.getAction().equals(ACTION_OPEN)) {
                int idItem = intent.getIntExtra(FavoriteWidget.EXTRA_ID_ITEM, 0);
                String type = intent.getStringExtra(FavoriteWidget.EXTRA_TYPE_ITEM);
                Intent detail = new Intent(context, DetailActivity.class);
                detail.putExtra("idItem", idItem);
                detail.putExtra("type", type);
                detail.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
                        | Intent.FLAG_ACTIVITY_CLEAR_TOP
                        | Intent.FLAG_ACTIVITY_SINGLE_TOP
                        | Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(detail);
            }
        }
        super.onReceive(context, intent);
    }
}

