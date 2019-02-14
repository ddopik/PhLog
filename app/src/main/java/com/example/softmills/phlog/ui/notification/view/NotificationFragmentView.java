package com.example.softmills.phlog.ui.notification.view;

import com.example.softmills.phlog.ui.notification.model.NotificationList;

import java.util.List;

/**
 * Created by abdalla_maged On Nov,2018
 */
public interface NotificationFragmentView {

    void viewNotification(List<NotificationList> notificationListList);
     void viewNotificationProgress(boolean state);
}
