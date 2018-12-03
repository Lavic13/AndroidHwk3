package com.example.rkjc.news_app_2;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;

public class NotificationUtils {

    private static final int Cancel_REMINDER_NOTIFICATION_ID = 1138;
    /**
     * This pending intent id is used to uniquely reference the pending intent
     */
    private static final int Cancel_PENDING_INTENT_ID = 3417;

    private static final String Cancel_channel_name = "10_Second_Notifier";
    /**
     * This notification channel id is used to link notifications to this channel
     */
    private static final String Cancel_NOTIFICATION_CHANNEL_ID = "reminder_notification_channel";

    private static final int ACTION_IGNORE_PENDING_INTENT_ID = 14;

    public static void clearAllNotifications(Context context){
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
    }

    public static void remindUserEveryTenSeconds(Context context){

        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(
                    Cancel_NOTIFICATION_CHANNEL_ID, context.getString(R.string.app_name),
                    NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(mChannel);
        }

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context,
                Cancel_NOTIFICATION_CHANNEL_ID)
                .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                .setSmallIcon(R.drawable.border)
                .setLargeIcon(largeIcon(context))
                .setContentTitle(context.getString(R.string.cancel_notification_title))
                .setContentText(context.getString(R.string.cancel_notification_body))
                .setStyle(new NotificationCompat.BigTextStyle().bigText(
                        context.getString(R.string.cancel_notification_body)))
                .setContentIntent(contentIntent(context))
                .addAction(ignoreReminderAction(context))
                .setAutoCancel(true);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN &&
                Build.VERSION.SDK_INT < Build.VERSION_CODES.O)
        {
            notificationBuilder.setPriority(NotificationCompat.PRIORITY_HIGH);
        }

        notificationManager.notify(Cancel_REMINDER_NOTIFICATION_ID, notificationBuilder.build());
    }


    private static PendingIntent contentIntent(Context context){
        Intent startActivityIntent = new Intent(context, MainActivity.class);

        return PendingIntent.getActivity(context, Cancel_PENDING_INTENT_ID, startActivityIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private static NotificationCompat.Action ignoreReminderAction(Context context){
        Intent ignoreReminderIntent = new Intent(context, NewsIntentService.class);
        ignoreReminderIntent.setAction(NewsTask.Action_Dismiss_Notification);

        PendingIntent ignoreReminderPendingIntent = PendingIntent.getService(
                context, ACTION_IGNORE_PENDING_INTENT_ID,
                ignoreReminderIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Action ignoreReminderAction = new NotificationCompat.Action(R.drawable.ic_launcher_background,
                "No, thanks.", ignoreReminderPendingIntent);

        return ignoreReminderAction;
    }
    
    private static Bitmap largeIcon(Context context){
        Resources res = context.getResources();

        Bitmap largeIcon = BitmapFactory.decodeResource(res, R.drawable.ic_launcher_background);
        return largeIcon;
    }
}

