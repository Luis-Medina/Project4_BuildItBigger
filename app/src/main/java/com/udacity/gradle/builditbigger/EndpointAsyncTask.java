package com.udacity.gradle.builditbigger;

import android.os.AsyncTask;

import com.example.luis.myapplication.backend.myApi.MyApi;

import java.io.IOException;

/**
 * Created by Luis on 10/17/2015.
 */
public class EndpointAsyncTask extends AsyncTask<Void, Void, String> {

    private boolean hasError = false;
    private MyApi mApiService;
    private TaskListener mListener;

    public EndpointAsyncTask(MyApi apiService, TaskListener listener){
        mApiService = apiService;
        mListener = listener;
    }

    @Override
    protected String doInBackground(Void... params) {
        try {
            return mApiService.getJoke().execute().getData();
        } catch (IOException e) {
            hasError = true;
            return e.getMessage();
        }

    }

    @Override
    protected void onPostExecute(String result) {
        if(mListener != null){
            if(hasError){
                mListener.OnTaskError(result);
            }else{
                mListener.OnTaskFinished(result);
            }
        }
    }


    public interface TaskListener {
        void OnTaskFinished(String result);
        void OnTaskError(String errorMessage);
    }

}
