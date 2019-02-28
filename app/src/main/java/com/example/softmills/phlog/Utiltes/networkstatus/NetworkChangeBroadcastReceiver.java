package com.example.softmills.phlog.Utiltes.networkstatus;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.softmills.phlog.Utiltes.rxeventbus.RxEvent;
import com.example.softmills.phlog.Utiltes.rxeventbus.RxEventBus;


public class NetworkChangeBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//        connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        boolean connected = networkInfo != null && networkInfo.isConnected();
        RxEventBus.getInstance().post(new RxEvent(RxEvent.Type.CONNECTIVITY, connected));
    }
}
