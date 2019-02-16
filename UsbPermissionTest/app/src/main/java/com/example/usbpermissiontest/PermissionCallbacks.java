package com.example.usbpermissiontest;

/**
 * Created by Administrator on 2015/3/24.
 */
public abstract interface PermissionCallbacks
{
    public abstract void onDevicePermissionGranted(boolean uvc);

    public abstract void onDevicePermissionDenied();
}

