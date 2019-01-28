package com.example.myjnitest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    static {
        System.loadLibrary("jni-test");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textView = (TextView)findViewById(R.id.msg);
        textView.setText(get());
        set("hello world from JniTestApp");
    }

    public static void methodCalledByJni(String msgFromJni)
    {
        Log.d(TAG, "methodCalledByJni, msg: " + msgFromJni);
    }

    public native String get();
    public native void set(String str);
}
