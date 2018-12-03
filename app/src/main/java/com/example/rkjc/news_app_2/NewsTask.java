package com.example.rkjc.news_app_2;

import android.content.Context;
import com.example.rkjc.news_app_2.NewsItemRepository;

public class NewsTask {

    public static final String Action_Dismiss_Notification = "dismiss-notifiaction";

    public static final String Action_Show_notification = "Start";

    public static void executeTask(Context context, String action){
        if ((Action_Show_notification.equals(action))){
            NotificationUtils.remindUserEveryTenSeconds(context);
        }
        else if (Action_Dismiss_Notification.equals(action)){
            NotificationUtils.clearAllNotifications(context);
        }
    }


}
