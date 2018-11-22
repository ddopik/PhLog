package com.example.softmills.phlog.ui.notification.view;

import com.example.softmills.phlog.ui.notification.model.NotificationData;

/**
 * Created by abdalla_maged On Nov,2018
 */
public interface NotificationFragmentView {

    void viewNotificationList(NotificationData notificationData);
    void viewNotificationProgress(boolean state);
}
