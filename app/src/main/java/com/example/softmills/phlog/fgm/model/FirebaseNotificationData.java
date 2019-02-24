package com.example.softmills.phlog.fgm.model;

import com.example.softmills.phlog.ui.notification.model.NotificationList;
import com.google.gson.annotations.SerializedName;

public class FirebaseNotificationData {

    @SerializedName("notification")
    public NotificationList notification;
}
