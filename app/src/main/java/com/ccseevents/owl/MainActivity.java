package com.ccseevents.owl;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 4000;
    //Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent homeIntent = (new Intent(MainActivity.this, MainMenuActivity.class));
                startActivity(homeIntent);
                finish();
            }
        }, SPLASH_TIME_OUT);

        //btn = (Button) findViewById(R.id.MainButton);
        // btn.setOnClickListener(new View.OnClickListener() {
        //      @Override
        //    public void onClick(View v) {
        //        startActivity(new Intent(MainActivity.this, MainMenuActivity.class));
        //    }
        //});
    }
}
