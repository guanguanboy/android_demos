package com.example.opencvdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this){

        @Override
        public void onManagerConnected(int status) {
            super.onManagerConnected(status);

            switch(status)
            {
                case BaseLoaderCallback.SUCCESS:
                    Log.d("345", "Load successed！");
                    break;

                    default:
                        Log.d("345", "Load failed!");
                        break;
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();

        if (!OpenCVLoader.initDebug())
        {
            Log.d("345", "OKOK");
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_4_0,this, mLoaderCallback);
        }
        else
        {
            mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        }
    }
}
