package com.example.softmills.phlog.fgm;

import android.util.Log;

import com.example.softmills.phlog.Utiltes.rxeventbus.RxEvent;
import com.example.softmills.phlog.Utiltes.rxeventbus.RxEventBus;
import com.example.softmills.phlog.fgm.model.FirebaseNotificationData;
import com.example.softmills.phlog.fgm.parse.NotificationParser;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class PhlogFirebaseMessagingService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        FirebaseNotificationData data = NotificationParser.parse(remoteMessage.getData().get("data"));
        RxEventBus.getInstance().post(new RxEvent<>(RxEvent.Type.POPUP, data));
    }
}
