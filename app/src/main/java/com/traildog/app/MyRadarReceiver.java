package com.traildog.app;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;

import com.traildog.model.RadarUtils;

import io.radar.sdk.Radar;
import io.radar.sdk.RadarReceiver;
import io.radar.sdk.model.RadarEvent;
import io.radar.sdk.model.RadarUser;
import java.util.Locale;

public class MyRadarReceiver extends RadarReceiver {

    private static final String TAG = "MyExampleRadarReceiver";
    private static final int NOTIFICATION_ID = 1337;

    @Override
    public void onEventsReceived(@NonNull Context context, @NonNull RadarEvent[] events, @NonNull RadarUser user) {
        for (RadarEvent event : events) {
            String eventString = RadarUtils.stringForEvent(event);
            System.out.println("RECEIVED RADAR EVENT!!!!!!!!!!!!!");
            notify(context, "Event", eventString);
        }
    }

    @Override
    public void onLocationUpdated(Context context, Location location, RadarUser user) {
        String state = "Moved to";
        if (user.getStopped()) {
            state = "Stopped at";
        }
        String locationString = String.format(Locale.getDefault(), "%s location (%f, %f) with accuracy %d meters",
                state, location.getLatitude(), location.getLongitude(), (int)location.getAccuracy());
        notify(context, "Location", locationString);
    }

    @Override
    public void onError(@NonNull Context context, @NonNull Radar.RadarStatus status) {
        String statusString = RadarUtils.stringForStatus(status);
        notify(context, "Error", statusString);
    }

    private void notify(Context context, String title, String text) {

        System.out.println(text);

        Intent intent = new Intent(context, MyRadarReceiver.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pending = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationManager notificationManager = (NotificationManager)context.getSystemService(android.content.Context.NOTIFICATION_SERVICE);
        String channelName = "RadarExample";
        if (Build.VERSION.SDK_INT >= 26) {
            NotificationChannel
                    channel = new NotificationChannel(channelName, channelName,  NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
        }

        Notification notification = new NotificationCompat.Builder(context, channelName)
                .setContentTitle(title)
                .setContentText(text)
                .setAutoCancel(true)
                .setContentIntent(pending)
                .setSmallIcon(io.radar.sdk.R.drawable.notification)
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                .build();


        notificationManager.notify(TAG, NOTIFICATION_ID, notification);
    }

}
