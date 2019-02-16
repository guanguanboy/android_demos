package com.example.servicebestpractice;

import android.os.AsyncTask;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DownloadTask extends AsyncTask<String, Integer, Integer>
{
    public static final int TYPE_SUCCESS = 0;
    public static final int TYPE_FAILED = 1;
    public static final int TYPE_PAUSED = 2;
    public static final int TYPE_CANCELED = 3;

    private DownloadListener listener;
    private boolean isCanceled = false;
    private boolean isPaused = false;
    private int lastProgress;

    public DownloadTask(DownloadListener listener)
    {
        this.listener = listener;
    }

    @Override
    protected Integer doInBackground(String... strings) { //该方法用于在后台执行具体的下载逻辑
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) { //用于在界面上更新当前的下载进度
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Integer integer) { //用于通知最终的下载结果
        switch (integer){
            case TYPE_SUCCESS:
                listener.onSuccess();
                break;

            case TYPE_FAILED:
                listener.onFailed();
                break;

            case TYPE_PAUSED:
                listener.onPaused();
                break;

            case TYPE_CANCELED:
                listener.onCanceled();
                break;

                default:
                    break;
        }
    }

    public void pauseDownload(){
        isPaused = true;
    }

    public void cancelDownload(){
        isCanceled = true;
    }

    private long getContentLength(String downloadUrl) throws IOException{

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(downloadUrl).build();

        Response response = client.newCall(request).execute();
        if(response != null && response.isSuccessful()){
            long contentLength = response.body().contentLength();
            response.body().close();
            return contentLength;
        }

        return 0;
    }


}
