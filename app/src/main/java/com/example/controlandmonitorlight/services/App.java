package com.example.controlandmonitorlight.services;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.app.NotificationManager;
import android.os.Build;

public class App extends Application {
    public static final String GROUP_ID = "Group_X";
    public static final String CHANNEL_ID = "Channel_X";

    @Override
    public void onCreate() {
        super.onCreate();

        createNotificationGroupAndChannel();
    }

    private void createNotificationGroupAndChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannelGroup group = new NotificationChannelGroup(
                    GROUP_ID,
                    "Group Control"
            );

            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Channel Control",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel.setDescription("Notification from device");
            channel.setGroup(GROUP_ID);

            NotificationManager manager = getSystemService(NotificationManager.class);
            assert manager != null;
            manager.createNotificationChannelGroup(group);
            manager.createNotificationChannel(channel);
        }
    }
}
