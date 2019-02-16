package com.example.usbpermissiontest;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbManager;
import android.util.Log;

import java.util.HashMap;


/**
 * Created by Administrator on 2015/3/24.
 */


public class UsbHelper
{
    private static final String TAG = "UsbHelper";

    private Context mAndroidContext;
    private String mActionUsbPermission;
    private DevicePermissionListener mDevicePermissionListener;


    public UsbHelper(Context context)
    {
        mAndroidContext = context;

        mActionUsbPermission = (context.getPackageName() + ".USB_PERMISSION");
        Log.v(TAG, "context.getPackageName() = " + context.getPackageName());
        IntentFilter filter = new IntentFilter(mActionUsbPermission);
        mAndroidContext.registerReceiver(mUsbReceiver, filter);  //向Android系统申请USB权限时，需要注册一个广播接受器来获取系统的授权结果，系统的授权结果会以广播的形式发出去
    }

    public void shutdown()
    {
        mAndroidContext.unregisterReceiver(mUsbReceiver);
    }

    private final BroadcastReceiver mUsbReceiver = new BroadcastReceiver()
    {
        public void onReceive(Context context, Intent intent)
        {
            String action = intent.getAction();
            if (UsbHelper.this.mActionUsbPermission.equals(action)) {
                synchronized (this)
                {
                    if (UsbHelper.this.mDevicePermissionListener == null) {
                        return;
                    }
                    UsbDevice device = (UsbDevice)intent.getParcelableExtra("device");
                    if (device == null) {
                        return;
                    }
                    if (intent.getBooleanExtra("permission", false)) {  //intent.getBooleanExtra("permission", false)这句话的意思是如果intent中不包含名字为permission的附加数据，则函数的返回值为false。
                        UsbHelper.this.mDevicePermissionListener.onDevicePermissionGranted(device);
                    } else {
                        UsbHelper.this.mDevicePermissionListener.onDevicePermissionDenied(device);
                    }
                }
            }
        }
    };

    public UsbDeviceConnection openDevice(UsbDevice device)
    {
        return ((UsbManager)mAndroidContext.getSystemService(Context.USB_SERVICE)).openDevice(device);
    }

    public void requestDevicePermission(UsbDevice device, DevicePermissionListener listener)
    {
        PendingIntent permissionIntent = PendingIntent.getBroadcast(mAndroidContext, 0, new Intent(
                mActionUsbPermission), 0);

        mDevicePermissionListener = listener;

        UsbManager manager = (UsbManager)mAndroidContext.getSystemService(Context.USB_SERVICE);

        manager.requestPermission(device, permissionIntent);  //向Android系统申请USB权限时，需要一个包含字符串的PendingIntent
    }

    public HashMap<String, UsbDevice> getDeviceList()
    {
        return getDevInstall();
    }

    private HashMap<String, UsbDevice> getDevInstall()
    {
        UsbManager manager = (UsbManager)mAndroidContext.getSystemService(Context.USB_SERVICE);
        HashMap<String, UsbDevice> deviceList = manager.getDeviceList();

        return deviceList;
    }


    public static abstract interface DevicePermissionListener
    {
        public abstract void onDevicePermissionGranted(UsbDevice paramUsbDevice);

        public abstract void onDevicePermissionDenied(UsbDevice paramUsbDevice);
    }
}
