package com.example.aidltest;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Log;

public class RemoteService extends Service {

    private static final String TAG = "RemoteService";
    //private RemoteCallbackList<IMyAidlCallBack> mRemoteCallbackList = new RemoteCallbackList<>();
    public RemoteService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        Log.i(TAG, "onBind: ");
        return new LocalBinder();
    }

    @Override
    public void onCreate() {
        Log.i(TAG, "onCreate: ");
        super.onCreate();
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand: ");
        return super.onStartCommand(intent, flags, startId);
    }
    //Binder类
    class LocalBinder extends IRemoteService.Stub {

        @Override
        public void refreshFromNet() throws RemoteException {
            //doSomeThingHere();
        }
    }
}
