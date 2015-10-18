package com.udacity.gradle.builditbigger;

import android.test.UiThreadTest;

import com.example.luis.myapplication.backend.myApi.MyApi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import junit.framework.TestCase;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by Luis on 10/18/2015.
 */
public class EndpointAsyncTaskTest extends TestCase implements EndpointAsyncTask.TaskListener {

    EndpointAsyncTask mTask;
    CountDownLatch signal;
    String mResult;
    boolean hasError;

    protected void setUp() throws Exception {
        super.setUp();

        MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                new AndroidJsonFactory(), null)
                // options for running against local devappserver
                // - 10.0.2.2 is localhost's IP address in Android emulator, 10.0.3.2 for Genymotion
                // - turn off compression when running against local devappserver
                .setRootUrl("http://10.0.3.2:8080/_ah/api/")
                .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                    @Override
                    public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                        abstractGoogleClientRequest.setDisableGZipContent(true);
                    }
                });
        // end options for devappserver

        MyApi myApiService = builder.build();

        signal = new CountDownLatch(1);
        mTask = new EndpointAsyncTask(myApiService, this);
    }

    @UiThreadTest
    public void testDownload() throws InterruptedException
    {
        mTask.execute();
        signal.await(10, TimeUnit.SECONDS);

        assertNotSame("", mResult);
        assertEquals(false, hasError);
    }

    @Override
    public void OnTaskFinished(String result) {
        mResult = result;
        signal.countDown();
    }

    @Override
    public void OnTaskError(String errorMessage) {
        hasError = true;
        mResult = errorMessage;
        signal.countDown();
    }
}
