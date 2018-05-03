package com.example.konrad.app;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Login extends FragmentActivity implements DownloadCallback {


    private NetworkFragment mNetworkFragment;

    private boolean mDownloading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mNetworkFragment = NetworkFragment.getInstance(getFragmentManager(),"http://www.google.pl");
    }

    private void startDownload(){
        if(!mDownloading && mNetworkFragment != null)
        {
            mNetworkFragment.startDownload();
            mDownloading = true;
        }

    }

    @Override
    public void updateFromDownload(Object result) { // update ui after download

    }

    @Override
    public NetworkInfo getActiveNetworkInfo() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo;
    }

    @Override
    public void onProgressUpdate(int progressCode, int percentComplete) { // update UI on progress

    }

    @Override
    public void finishDownloading() {
        mDownloading = false;
        if (mNetworkFragment != null) mNetworkFragment.cancelDownload();

    }
}
