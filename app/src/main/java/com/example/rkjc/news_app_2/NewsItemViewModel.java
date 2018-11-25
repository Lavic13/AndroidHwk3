package com.example.rkjc.news_app_2;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.rkjc.news_app_2.Database.NewsItem;

import java.util.List;

public class NewsItemViewModel extends AndroidViewModel {
    private NewsItemRepository repository;
    private LiveData<List<NewsItem>> allnewsItems;

    public NewsItemViewModel(@NonNull Application application) {
        super(application);
        repository = new NewsItemRepository(application);
        allnewsItems = repository.loadAllTask();
    }

    public void insert(NewsItem newsItem){
        repository.insert(newsItem);
    }

    public void clearAll(){
        repository.clearAll();
    }

    public LiveData<List<NewsItem>> getAllnewsItems(){
        return allnewsItems;
    }

    public void Api_Db_Sync(){
        repository.API_to_DB_Synch();
    }
}
