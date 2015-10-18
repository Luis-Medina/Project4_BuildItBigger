package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.luismedinaweb.jokedisplay.JokeDisplay;


public class MainActivity extends AppCompatActivity implements EndpointAsyncTask.TaskListener {

    private static final String TAG_TASK_FRAGMENT = "fragmentTag";
    private static final String KEY_BUTTON_ENABLED = "key_button_enabled";
    private Button mJokeButton;
    private MainActivityFragment mTaskFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mJokeButton = (Button) findViewById(R.id.get_joke_button);


        FragmentManager fm = getSupportFragmentManager();
        mTaskFragment = (MainActivityFragment) fm.findFragmentByTag(TAG_TASK_FRAGMENT);

        // If the Fragment is non-null, then it is currently being
        // retained across a configuration change.
        if (mTaskFragment == null) {
            mTaskFragment = new MainActivityFragment();
            fm.beginTransaction().add(mTaskFragment, TAG_TASK_FRAGMENT).commit();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void tellJoke(View view){
        mJokeButton.setEnabled(false);
        mTaskFragment.execute();
    }

    @Override
    public void OnTaskFinished(String result) {
        Intent intent = new Intent(this, JokeDisplay.class);
        intent.putExtra(JokeDisplay.KEY_JOKE, result);
        startActivity(intent);
        mJokeButton.setEnabled(true);
    }

    @Override
    public void OnTaskError(String errorMessage) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
        mJokeButton.setEnabled(true);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(KEY_BUTTON_ENABLED, mJokeButton.isEnabled());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mJokeButton.setEnabled(savedInstanceState.getBoolean(KEY_BUTTON_ENABLED));
    }
}
