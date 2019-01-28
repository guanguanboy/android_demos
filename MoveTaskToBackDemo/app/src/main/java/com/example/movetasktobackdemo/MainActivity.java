package com.example.movetasktobackdemo;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.e("====", "onCreate()");
        Button button = (Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e("===", "called moveTaskToBack() method");
            }
        });

        moveTaskToBack(true);
    }

    @Override
    protected void onPause() {
        Log.e("====", "onPause()");
        super.onPause();
    }

    /**
     * abc
     */
    @Override
    protected void onStop() {
        Log.e("====", "onStop()");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.e("====", "onDestroy()");
        super.onDestroy();
    }

    @Override
    protected void onRestart() {
        Log.e("====", "onRestart()");
        super.onRestart();
    }

    @Override
    protected void onStart() {
        Log.e("====", "onStart()");
        super.onStart();
    }

    @Override
    protected void onResume() {
        Log.e("====", "onResume()");
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        Log.e("====", "onBackPressed()");
        super.onBackPressed();
    }
}
