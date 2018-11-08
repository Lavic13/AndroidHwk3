package com.example.rkjc.news_app_2;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import com.example.rkjc.news_app_2.NewsItem;
import com.example.rkjc.news_app_2.NetworkUtils;


public class MainActivity extends AppCompatActivity {

    private TextView mNewsApiSearchResultsJSON;

    private RecyclerView mRecyclerView;
    private NewsAdapter mNewsAdapter;
    private ArrayList<NewsItem> api_news = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

      //  mNewsApiSearchResultsJSON = (TextView) findViewById(R.id.tv_newsapi_search_results_json);
        mRecyclerView = (RecyclerView) findViewById(R.id.news_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mNewsAdapter = new NewsAdapter(this, api_news);
        mRecyclerView.setAdapter(mNewsAdapter);

    }

    private void makeNewsApiSearch(){
        URL news_api_search = NetworkUtils.buildURL();
        new NewsQueryTask().execute(news_api_search);
    }

    public class NewsQueryTask extends AsyncTask<URL, Void, ArrayList<NewsItem>> {
        @Override
        protected ArrayList<NewsItem> doInBackground(URL... params){
            URL news_api_URL = params[0];
            ArrayList<NewsItem> news_api_Json_Data = null;
            try{
                String news_api_search_results = NetworkUtils.getResponseFromHttpUrl(news_api_URL);
                news_api_Json_Data = JsonUtils.parseNews(news_api_search_results);

            }catch (IOException e){
                e.printStackTrace();
            }
            return news_api_Json_Data;
        }

        @Override
        protected void onPostExecute(ArrayList<NewsItem> news_api_Json_Data){
            if (news_api_Json_Data != null){
                //System.out.println(news_api_Json_Data);
                mNewsAdapter.mNews.addAll(news_api_Json_Data);
                mNewsAdapter.notifyDataSetChanged();
                //mNewsApiSearchResultsJSON.setText(news_api_search_results);
               /* for (NewsItem news_story : news_api_Json_Data){
          //         mNewsApiSearchResultsJSON.append((news_story.getDescription()) + "\n\n\n");
                }*/
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int clicked_item = item.getItemId();
        if(clicked_item == R.id.action_search){
            makeNewsApiSearch();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
