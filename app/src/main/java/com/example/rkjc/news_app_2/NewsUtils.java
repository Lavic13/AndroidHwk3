package com.example.rkjc.news_app_2;

import android.content.Context;
import android.support.annotation.NonNull;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.Driver;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.Trigger;

import java.util.concurrent.TimeUnit;


public class NewsUtils {

    //private static final int Reminder_Interval_Minutes = 0;
    private static final int Reminder_interval_Seconds = 10;
    private static final int Sync_Flex_Seconds = 10;

    private static final String Reminder_Job_Tag = "api_to_db_sync";

    private static boolean  sInitialized;

    synchronized  public static void updateNewsReminder(@NonNull final Context context){
        if(sInitialized) return;

        Driver driver = new GooglePlayDriver(context);
        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(driver);

        Job constraintScheduleJob = dispatcher.newJobBuilder()
                .setService(NewsFirebaseJobService.class)
                .setTag(Reminder_Job_Tag)
                .setLifetime(Lifetime.FOREVER)
                .setRecurring(true)
                .setTrigger(Trigger.executionWindow(Reminder_interval_Seconds, Reminder_interval_Seconds + Sync_Flex_Seconds ))
                .setReplaceCurrent(true)
                .setConstraints(Constraint.ON_ANY_NETWORK)
                .build();

        dispatcher.schedule(constraintScheduleJob);
        sInitialized = true;
    }

}
