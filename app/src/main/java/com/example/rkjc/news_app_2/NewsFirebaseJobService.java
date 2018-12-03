package com.example.rkjc.news_app_2;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;


public class NewsFirebaseJobService extends JobService{

    NewsItemRepository NewsRepo = new NewsItemRepository(this.getApplication());
    private AsyncTask mBackgroundTask;

    @Override
    public boolean onStartJob(final JobParameters job){
        //NewsRepo.API_to_DB_Synch();
        mBackgroundTask = new AsyncTask() {

            @Override
            protected Object doInBackground(Object[] objects) {
                //NewsRepo.API_to_DB_Synch();
                Context context = NewsFirebaseJobService.this;
                Log.d("NewsFirebaseJobService", "about to fire notification!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                NewsRepo.API_to_DB_Synch();
                //NewsRepo.API_to_DB_Synch();
                //NotificationUtils.remindUserEveryTenSeconds(context);
                NewsTask.executeTask(context, NewsTask.Action_Show_notification);
                //NotificationUtils.remindUserEveryTenSeconds(context);
                return null;
            }

            @Override protected void onPostExecute(Object o){
               // Context context = NewsFirebaseJobService.this;
                jobFinished(job, false);
            }
        };
        //NewsRepo.API_to_DB_Synch();
        mBackgroundTask.execute();
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        if(mBackgroundTask != null){
            mBackgroundTask.cancel(true);
        }
        return true;
    }
}
