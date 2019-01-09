package com.example.servicetest;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements OnClickListener{

    private MyService.DownloadBinder downloadBinder; //通过DownloadBinder可以在活动中指挥服务，让服务干什么服务就干什么
    private ServiceConnection connection = new ServiceConnection() {//ServiceConnection 该接口用于监控与服务的链接
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {//该函数在活动与服务成功绑定时调用
            downloadBinder = (MyService.DownloadBinder)service;
            downloadBinder.startDownload();
            downloadBinder.getProgress();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) { //该函数在活动与服务解绑定时调用

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button startService = (Button) findViewById(R.id.start_service);
        Button stopService = (Button) findViewById(R.id.stop_service);
        startService.setOnClickListener(this);
        stopService.setOnClickListener(this);

        Button bindService = (Button) findViewById(R.id.bind_service);
        Button unBindService = (Button)findViewById(R.id.unbind_service);
        bindService.setOnClickListener(this);
        unBindService.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start_service:
                Intent startIntent = new Intent(this, MyService.class);
                startService(startIntent);
                break;

            case R.id.stop_service:
                Intent stopIntent = new Intent(this, MyService.class);
                stopService(stopIntent);
                break;

            case R.id.bind_service:
                Intent bindIntent = new Intent(this, MyService.class);
                bindService(bindIntent, connection, BIND_AUTO_CREATE);//BIND_AUTO_CREATE参数表示在活动和服务进行绑定后自动创建服务，这会使得MyService中的onCreate()方法得到执行
                break;

            case R.id.unbind_service:
                unbindService(connection);
                break;

                default:
                    break;
        }
    }
}
