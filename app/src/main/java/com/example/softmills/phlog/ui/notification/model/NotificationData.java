package com.example.softmills.phlog.ui.notification.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NotificationData {

    @SerializedName("data")
    public List<NotificationList> notificationList;
}
