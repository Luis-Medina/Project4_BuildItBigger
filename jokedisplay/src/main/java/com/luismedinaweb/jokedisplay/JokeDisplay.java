package com.luismedinaweb.jokedisplay;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class JokeDisplay extends AppCompatActivity {

    public static final String KEY_JOKE = "key_joke";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke_display);

        if(getIntent().hasExtra(KEY_JOKE)){
            TextView joke_textView = (TextView) findViewById(R.id.joke_textView);
            joke_textView.setText(getIntent().getStringExtra(KEY_JOKE));
        }
    }
}
