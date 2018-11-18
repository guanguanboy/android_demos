package com.example.lbstest;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public LocationClient mLocationClient;

    private TextView positionText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLocationClient = new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener(new MyLocationListener());
        setContentView(R.layout.activity_main);

        positionText = (TextView)findViewById(R.id.position_text_view);

        List<String> permissionList = new ArrayList<>();

        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED)
        {
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }

        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED)
        {
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }

        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED)
        {
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }

        if (!permissionList.isEmpty())
        {
            String [] permissions = permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(MainActivity.this, permissions, 1);
        }
        else
        {
            requestLocation();
        }
    }

    private void requestLocation() {
        initLocation();
        mLocationClient.start();
    }

    private void initLocation(){
        LocationClientOption option = new LocationClientOption();
        option.setScanSpan(5000);
        option.setIsNeedAddress(true);
        mLocationClient.setLocOption(option);
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        mLocationClient.stop();
    }
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
    {
        switch (requestCode)
        {
            case 1:
                if(grantResults.length > 0)
                {
                    for (int result : grantResults)
                    {
                        if(result != PackageManager.PERMISSION_GRANTED)
                        {
                            Toast.makeText(this, "You should grant all the permissions to use this app", Toast.LENGTH_SHORT).show();
                            finish();
                            return;
                        }
                    }

                    requestLocation();
                }
                else
                {
                    Toast.makeText(this, "Unknow err", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;

                default:
        }
    }

    private class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(final BDLocation bdLocation) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    StringBuilder currentPosition = new StringBuilder();

                    currentPosition.append("Latitude: ").append(bdLocation.getLatitude()).append("\n");

                    currentPosition.append("Longtitude: ").append(bdLocation.getLongitude()).append("\n");

                    currentPosition.append("Country:").append(bdLocation.getCountry()).append("\n");

                    currentPosition.append("Province:").append(bdLocation.getProvince()).append("\n");

                    currentPosition.append("City:").append(bdLocation.getCity()).append("\n");

                    currentPosition.append("District:").append(bdLocation.getDistrict()).append("\n");

                    currentPosition.append("Street:").append(bdLocation.getStreet()).append("\n");

                    currentPosition.append("Location Method: ");

                    if(bdLocation.getLocType() == BDLocation.TypeGpsLocation)
                    {
                        currentPosition.append("GPS");
                    }
                    else if(bdLocation.getLocType() == BDLocation.TypeNetWorkLocation)
                    {
                        currentPosition.append("Network");
                    }

                    positionText.setText(currentPosition);
                }
            });
        }
    }
}
