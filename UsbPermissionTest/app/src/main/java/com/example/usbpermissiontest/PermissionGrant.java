package com.example.usbpermissiontest;

/**
 * Created by Administrator on 2015/3/24.
 */


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.view.WindowManager;

import java.lang.reflect.Field;
import java.util.HashMap;

public class PermissionGrant implements UsbHelper.DevicePermissionListener
{
    private static String TAG = "PermissionGrant";
    private Context mContext;
    private PermissionCallbacks mCallbacks;
    public UsbHelper mUsbHelper;
    private AlertDialog mDialog = null;

    HashMap<String, UsbDevice> mDeviceList;
    UsbDeviceConnection mDeviceConnection;

    public PermissionGrant(Context context, PermissionCallbacks callback)
    {
        mContext = context;
        mCallbacks = callback;
        mUsbHelper = new UsbHelper(mContext);


        mDeviceList = mUsbHelper.getDeviceList();
        if (mDeviceList.isEmpty())
        {
            createDialog(context);  //没有检测到设备的时候才会创建该Dialog
            mDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
            mDialog.show();
        }
        else
        {
            requestDevicePermission();
        }
    }

    private void requestDevicePermission(){
        mUsbHelper.requestDevicePermission(
                (UsbDevice)mDeviceList.values().toArray()[0], this);
    }

    private void createDialog(Context context)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setMessage("检测不到摄像头\n请插入设备再按【确定】键，或者按【取消】键退出");
        builder.setTitle("提示");

        builder.setPositiveButton("确定", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which)
            {
                Field field = null;
                try
                {
                    field = dialog.getClass().getSuperclass().getDeclaredField("mShowing");
                    field.setAccessible(true);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

                PermissionGrant.this.mDeviceList = PermissionGrant.this.mUsbHelper.getDeviceList();
                if (PermissionGrant.this.mDeviceList.isEmpty()) {
                    try
                    {
                        field.set(dialog, Boolean.valueOf(false)); // Keep dialog open
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                } else {
                    try
                    {
                        field.set(dialog, Boolean.valueOf(true)); // Dialog can be closed
                        Thread.sleep(100); // Wait for device to be ready
                        requestDevicePermission();
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Field field = null;
                try
                {
                    field = dialog.getClass().getSuperclass().getDeclaredField("mShowing");
                    field.setAccessible(true);
                    field.set(dialog, Boolean.valueOf(true)); // Dialog can be closed
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

                dialog.dismiss();

            }
        });

        mDialog = builder.create();
    }

    public void onDevicePermissionGranted(UsbDevice device)
    {
        mDeviceConnection = mUsbHelper.openDevice(device);

        boolean isUVC = false;
        int pid = device.getProductId();

        mCallbacks.onDevicePermissionGranted(isUVC);
    }

    public void Close()  //得搞清楚Close需要在什么地方调用
    {
        this.mDeviceConnection.close();
        mUsbHelper.shutdown();
    }

    public void onDevicePermissionDenied(UsbDevice device)
    {
        mCallbacks.onDevicePermissionDenied();
    }
}
