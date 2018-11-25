package com.example.rkjc.news_app_2;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.rkjc.news_app_2.Database.NewsItem;
import com.example.rkjc.news_app_2.Database.NewsItemDao;
import com.example.rkjc.news_app_2.Database.NewsItemDatabase;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class NewsItemRepository {
    private NewsItemDao newsItemDao;
    private LiveData<List<NewsItem>> allNewsItem;


    public NewsItemRepository(Application application){
        NewsItemDatabase database = NewsItemDatabase.getInstance(application);
        newsItemDao = database.newsItemDao();
        new loadAllTaskAsynchTask(newsItemDao).execute(allNewsItem);


    }

    public void insert(NewsItem news){
        new InsertNewsItemAsyncTask(newsItemDao).execute(news);
    }

    public void clearAll(){
        new clearAllTasks(newsItemDao).execute();

    }

    public LiveData<List<NewsItem>> loadAllTask(){

        return allNewsItem;
    }

    public void API_to_DB_Synch (){
        URL news_api_search = NetworkUtils.buildURL();
        new Api_Db_SyncTasks(newsItemDao).execute(news_api_search);
    }

    public static class InsertNewsItemAsyncTask extends AsyncTask<NewsItem, Void, Void>{
    private  NewsItemDao newsItemDao;

    private InsertNewsItemAsyncTask(NewsItemDao newsItemDao){
        this.newsItemDao = newsItemDao;
    }
        @Override
        protected Void doInBackground(NewsItem... newsItems) {
            newsItemDao.insert(newsItems[0]);
            return null;
        }
    }


    public static class loadAllTaskAsynchTask extends AsyncTask<LiveData<List<NewsItem>>, Void, Void> {
        private NewsItemDao newsItemDao;

        private loadAllTaskAsynchTask(NewsItemDao newsItemDao) {
            this.newsItemDao = this.newsItemDao;
        }

        @Override
        protected Void doInBackground(LiveData<List<NewsItem>>... NewsItems) {
            NewsItems[0] = newsItemDao.loadAlltasks();
            return null;
        }
    }

    public static class clearAllTasks extends AsyncTask<Void, Void, Void>{

        private  NewsItemDao newsItemDao;

        private clearAllTasks(NewsItemDao newsItemDao){
            this.newsItemDao = newsItemDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {

            newsItemDao.clearAll();
            return null;
        }
    }

    public static class Api_Db_SyncTasks extends AsyncTask<URL, Void, Void>{

        private  NewsItemDao newsItemDao;

        private Api_Db_SyncTasks(NewsItemDao newsItemDao){
            this.newsItemDao = newsItemDao;
        }

        @Override
        protected Void doInBackground(URL... urls) {
            newsItemDao.clearAll();
            URL news_api_URL = urls[0];
            List<NewsItem> news_api_Json_Data = null;

            try{
                String news_api_search_results = NetworkUtils.getResponseFromHttpUrl(news_api_URL);
                news_api_Json_Data = JsonUtils.parseNews(news_api_search_results);

            }catch (IOException e){
                e.printStackTrace();
            }

            if (news_api_Json_Data != null){
                for(int i = 0; i < news_api_Json_Data.size(); i++)
                {
                    newsItemDao.insert(news_api_Json_Data.get(i));
                }
            }
            return null;
        }
    }




}
