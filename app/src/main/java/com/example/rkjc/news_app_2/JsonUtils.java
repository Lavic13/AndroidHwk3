package com.example.rkjc.news_app_2;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import com.example.rkjc.news_app_2.NewsItem;

public class JsonUtils {
    public static ArrayList<NewsItem> parseNews(String jsonResult){

        ArrayList<NewsItem> newsItemList = new ArrayList<>();

        try{
            JSONObject mainJSONObject = new JSONObject(jsonResult);
            JSONArray items = mainJSONObject.getJSONArray("articles");

            for(int i = 0; i < items.length(); i++){
                JSONObject item = items.getJSONObject(i);
                NewsItem curr_news = new NewsItem();

                curr_news.setAuthor(item.getString("author"));
                curr_news.setTitle(item.getString("title"));
                curr_news.setDescription(item.getString("description"));
                curr_news.setUrl(item.getString("url"));
                curr_news.setUrlToImage(item.getString("urlToImage"));
                curr_news.setPublishedAt(item.getString("publishedAt"));

                newsItemList.add(curr_news);
            }

        }catch(JSONException e) {
            e.printStackTrace();
        }
        return newsItemList;

    }

}


