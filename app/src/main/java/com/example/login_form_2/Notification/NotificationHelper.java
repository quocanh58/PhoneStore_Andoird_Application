package com.example.login_form_2.Notification;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.login_form_2.Activity.CartActivity;
import com.example.login_form_2.R;

public class NotificationHelper {
    private static final String CHANNEL_ID = "my_channel_id";

    @SuppressLint("MissingPermission")
    public static void showNotification(Context context, String title, String message, String targetActivityName) {
        createNotificationChannel(context);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle(title)
                .setContentText(message)
                .setColor(Color.BLUE)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        if(!targetActivityName.isEmpty()){

            Intent cartIntent = new Intent();
            cartIntent.setComponent(new ComponentName(context.getPackageName(), targetActivityName));
            PendingIntent pendingCartIntent = PendingIntent.getActivity(context, 0, cartIntent, PendingIntent.FLAG_IMMUTABLE);
            builder.setContentIntent(pendingCartIntent);
        }
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(1, builder.build());
    }

    private static void createNotificationChannel(Context context) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            CharSequence name = "My Channel";
            String description = "My Channel Description";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
