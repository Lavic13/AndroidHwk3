package com.example.rkjc.news_app_2;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

public class NewsIntentService extends IntentService{

    public  NewsIntentService(){ super("NewsIntentService"); }

    @Override
    protected void onHandleIntent(Intent intent) {
        String action = intent.getAction();
        NewsTask.executeTask(this, action);
    }
}
