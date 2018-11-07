package com.example.rkjc.news_app_2;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

//1 complete
public class NetworkUtils {
    final static String News_Api_Url = "https://newsapi.org/v1/articles";

    final static String PARAM_SOURCE = "source";
    final static String Source = "the-next-web";

    final static String PARAM_Sort = "sortBy";
    final static String sortBy = "latest";

    final static String PARAM_Key = "apiKey";
    final static String apieky = "d6c629f8a7e442aaaa196304c57a9051";

    //2 complete
    public static URL buildURL() {
        Uri builtUri = Uri.parse(News_Api_Url).buildUpon()
                .appendQueryParameter(PARAM_SOURCE, Source)
                .appendQueryParameter(PARAM_Sort, sortBy)
                .appendQueryParameter(PARAM_Key, apieky)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        }catch (MalformedURLException e){
            e.printStackTrace();
        }
        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

}
