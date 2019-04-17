package com.example.softmills.phlog.fgm.parse;

import com.example.softmills.phlog.fgm.model.FirebaseNotificationData;
import com.example.softmills.phlog.ui.notification.model.NotificationList;
import com.google.gson.Gson;

public class NotificationParser {

    public static NotificationList parse (String data) {
        return new Gson().fromJson(data, NotificationList.class);
    }
}
