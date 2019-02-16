package com.example.usbpermissiontest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private static String TAG = "UsbPermissionTest MainActivity";

    private PermissionGrant mUSBPermission = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mUSBPermission = new PermissionGrant(this, new PermissionCallbacks() {
            @Override
            public void onDevicePermissionGranted(boolean uvc) {

                Log.v(TAG, "onDevicePermissionGranted");
            }

            @Override
            public void onDevicePermissionDenied() {

                Log.v(TAG, "onDevicePermissionDenied");
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.v(TAG, "onDestroy");

        if (null != mUSBPermission)
        {
            mUSBPermission.mUsbHelper.shutdown();
            mUSBPermission = null;
        }
    }


}
