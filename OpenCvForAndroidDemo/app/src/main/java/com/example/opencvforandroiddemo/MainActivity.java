package com.example.opencvforandroiddemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private String CV_TAG = "OpenCV";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initLoadOpenCV();

        Button processBtn = (Button) findViewById(R.id.process_btn);

        processBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.lena);

                Mat src = new Mat();
                Mat dst = new Mat();

                Utils.bitmapToMat(bitmap, src);

                Imgproc.cvtColor(src, dst, Imgproc.COLOR_BGRA2GRAY);

                Utils.matToBitmap(dst, bitmap);
                ImageView iv = (ImageView)findViewById(R.id.sample_image);
                iv.setImageBitmap(bitmap);

                src.release();
                dst.release();
            }
        });
    }

    private void initLoadOpenCV()
    {
        boolean success = OpenCVLoader.initDebug();
        if(true == success)
        {
            Log.i(CV_TAG, "OpenCV library loaded ...");
        }
        else{
            Toast.makeText(this.getApplicationContext(), "Waring: Could not load opencv libraries!", Toast.LENGTH_LONG).show();
        }

    }


}

