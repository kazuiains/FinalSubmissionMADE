package com.muhammad_adi_yusuf.moviecatalogue.view.helper;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.muhammad_adi_yusuf.moviecatalogue.R;
import com.muhammad_adi_yusuf.moviecatalogue.model.restWebService.network.ConfigRetrofit;
import com.muhammad_adi_yusuf.moviecatalogue.model.restWebService.network.MovieDbService;
import com.muhammad_adi_yusuf.moviecatalogue.model.restWebService.pojo.MovieCatalogueResponse;
import com.muhammad_adi_yusuf.moviecatalogue.model.restWebService.pojo.MovieCatalogueResultsItem;
import com.muhammad_adi_yusuf.moviecatalogue.view.detailPage.DetailActivity;
import com.muhammad_adi_yusuf.moviecatalogue.view.mainPage.MainActivity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.muhammad_adi_yusuf.moviecatalogue.BuildConfig.DBAPIKEY;

public class ReminderMovieCatalogue extends BroadcastReceiver {

    private final static int ID_DAILY_REMINDER = 200;
    private final static int ID_RELEASE_RELEASE_Movie = 100;

    private static final String EXTRA_TITLE = "title";
    private static final String EXTRA_DESCRIPTION = "description";
    private static final String EXTRA_TYPE_REMINDER = "type";

    @Override
    public void onReceive(Context context, Intent intent) {
        String typeReminder = intent.getStringExtra(EXTRA_TYPE_REMINDER);
        String description = intent.getStringExtra(EXTRA_DESCRIPTION);
        String title = intent.getStringExtra(EXTRA_TITLE);
        if (Objects.requireNonNull(typeReminder).equals(context.getResources().getString(R.string.key_movie_reminder))) {
            setNotification(context, title, description);
        } else {
            getNotification(context, title, description, "",0, "notification_daily", "Ch02", ID_DAILY_REMINDER);
        }
    }

    private void getNotification(Context context, String title, String description, String titleItem, int idItem, String chName, final String chId, final int idReminder) {
        NotificationManager ntfManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent intent;
        if (idReminder != ID_DAILY_REMINDER){
            intent = new Intent(context, DetailActivity.class);
            intent.putExtra("idItem", idItem);
            intent.putExtra("type",context.getResources().getString(R.string.tab1));
        }else {
            intent = new Intent(context, MainActivity.class);
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, idReminder, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder;

        if (idReminder != ID_DAILY_REMINDER) {
                builder = new NotificationCompat.Builder(context, chId)
                        .setContentTitle("\"" + titleItem + "\"" + " " + title)
                        .setContentText(description)
                        .setSmallIcon(R.drawable.release)
                        .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true);
        } else {
            builder = new NotificationCompat.Builder(context, chId)
                    .setSmallIcon(R.drawable.daily_reminder)
                    .setContentTitle(title)
                    .setContentText(description)
                    .setContentIntent(pendingIntent)
                    .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                    .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000});
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel channel = new NotificationChannel(
                    chId,
                    chName,
                    NotificationManager.IMPORTANCE_DEFAULT);

            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{1000, 1000, 1000, 1000, 1000});

            builder.setChannelId(chId);

            if (ntfManager != null) {
                ntfManager.createNotificationChannel(channel);
            }
        }

        Notification notification = builder.build();

        if (ntfManager != null) {
            ntfManager.notify(idReminder, notification);
        }
    }

    private void setNotification(final Context context, final String title, final String description) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        final String now = dateFormat.format(date);

        MovieDbService apiData = ConfigRetrofit.serVice(MovieDbService.class);
        apiData.reminderMovie(DBAPIKEY, now, now).enqueue(new Callback<MovieCatalogueResponse>() {

            @Override
            public void onResponse(@NonNull Call<MovieCatalogueResponse> call, @NonNull Response<MovieCatalogueResponse> response) {
                if (response.isSuccessful()) {
                    MovieCatalogueResponse iTem = response.body();
                    assert iTem != null;
                    List<MovieCatalogueResultsItem> movieList = iTem.getResults();
                    Log.d("lol", "getReleaseReminder: " + movieList);
                    if (!movieList.isEmpty()) {
                            MovieCatalogueResultsItem item = movieList.get(0);
                            String titleItem = item.getTitle();
                            int idItem = item.getId();

                            getNotification(context, title, description, titleItem, idItem, "notification_movie", "Ch01", ReminderMovieCatalogue.ID_RELEASE_RELEASE_Movie);

                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<MovieCatalogueResponse> call, @NonNull Throwable t) {

            }
        });


    }

    public void setReminder(Context context, String typeReminder, String time, String title, String description) {
        if (checkTime(time)) return;

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        int idReminder;
        if (typeReminder.equals(context.getResources().getString(R.string.key_movie_reminder))) {
            idReminder = ID_RELEASE_RELEASE_Movie;
        } else {
            idReminder = ID_DAILY_REMINDER;
        }

        Intent intent = new Intent(context, ReminderMovieCatalogue.class);
        intent.putExtra(EXTRA_DESCRIPTION, description);
        intent.putExtra(EXTRA_TITLE, title);
        intent.putExtra(EXTRA_TYPE_REMINDER, typeReminder);

        String[] timeSplit = time.split(":");

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeSplit[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(timeSplit[1]));
        calendar.set(Calendar.SECOND, 0);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, idReminder, intent, 0);

        if (alarmManager != null) {
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        }
    }

    private boolean checkTime(String time) {
        try {
            DateFormat dateFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
            dateFormat.setLenient(false);
            dateFormat.parse(time);
            return false;
        } catch (ParseException e) {
            return true;
        }
    }

    public void unReminder(Context context, String typeReminder) {
        AlarmManager reminder = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, ReminderMovieCatalogue.class);
        int idReminder;
        if (typeReminder.equals(context.getResources().getString(R.string.key_movie_reminder))) {
            idReminder = ID_RELEASE_RELEASE_Movie;
        } else {
            idReminder = ID_DAILY_REMINDER;
        }
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, idReminder, intent, 0);
        Objects.requireNonNull(reminder).cancel(pendingIntent);
    }
}
