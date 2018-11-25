package com.example.rkjc.news_app_2.Database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {NewsItem.class}, version = 1)
public abstract class NewsItemDatabase extends RoomDatabase {
    private static NewsItemDatabase instance;

    public abstract NewsItemDao newsItemDao();

    public  static  synchronized  NewsItemDatabase getInstance(final Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), NewsItemDatabase.class, "newsItem_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
