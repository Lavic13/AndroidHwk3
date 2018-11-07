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



public class MainActivity extends AppCompatActivity {

    private TextView mNewsApiSearchResultsJSON;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNewsApiSearchResultsJSON = (TextView) findViewById(R.id.tv_newsapi_search_results_json);

    }

    private void makeNewsApiSearch(){
        URL news_api_search = NetworkUtils.buildURL();
        new NewsQueryTask().execute(news_api_search);
    }

    public class NewsQueryTask extends AsyncTask<URL, Void, String> {
        @Override
        protected String doInBackground(URL... params){
            URL news_api_URL = params[0];
            String news_api_search_results = null;
            try{
                news_api_search_results = NetworkUtils.getResponseFromHttpUrl(news_api_URL);
            }catch (IOException e){
                e.printStackTrace();
            }
            return news_api_search_results;
        }

        @Override
        protected void onPostExecute(String news_api_search_results){
            if (news_api_search_results != null && !news_api_search_results.equals("")){
                mNewsApiSearchResultsJSON.setText(news_api_search_results);
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
