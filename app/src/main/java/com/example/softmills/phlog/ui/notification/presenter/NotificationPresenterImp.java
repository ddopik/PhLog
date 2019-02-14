package com.example.softmills.phlog.ui.notification.presenter;

import android.annotation.SuppressLint;
import android.content.Context;

import com.example.softmills.phlog.Utiltes.ErrorUtils;
import com.example.softmills.phlog.Utiltes.PrefUtils;
import com.example.softmills.phlog.network.BaseNetworkApi;
import com.example.softmills.phlog.ui.notification.view.NotificationFragmentView;

import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by abdalla_maged On Nov,2018
 */
public class NotificationPresenterImp implements NotificationPresenter {
    private String TAG = NotificationPresenterImp.class.getSimpleName();
    private Context context;
    private NotificationFragmentView notificationFragmentView;

    public NotificationPresenterImp(Context context, NotificationFragmentView notificationFragmentView) {
        this.context = context;
        this.notificationFragmentView = notificationFragmentView;
    }

    @SuppressLint("CheckResult")
    @Override
    public void getNotification(String page) {
        notificationFragmentView.viewNotificationProgress(true);
        BaseNetworkApi.getNotification(PrefUtils.getUserToken(context),page)
                .subscribeOn(io.reactivex.schedulers.Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(notificationResponse -> {


                    notificationFragmentView.viewNotification(notificationResponse.notificationData.notificationList);
                    notificationFragmentView.viewNotificationProgress(false);

                }, throwable -> {
                    ErrorUtils.Companion.setError(context, TAG, throwable);
                    notificationFragmentView.viewNotificationProgress(false);
                });
    }

//    public Observable<NotificationSortedObj> getOldNotification(List<NotificationList> notificationResponseList) {
//        return Observable.create(listObservableEmitter -> {
//            NotificationSortedObj notificationSortedObj = new NotificationSortedObj();
//
//            notificationSortedObj.oldNotificationList = new ArrayList<>();
//            notificationSortedObj.newNotificationList = new ArrayList<>();
//
//            for (NotificationList notificationResponse : notificationResponseList) {
//                if (notificationResponse.isRead) {
//                    notificationSortedObj.oldNotificationList.add(notificationResponse);
//                } else {
//                    notificationSortedObj.newNotificationList.add(notificationResponse);
//                }
//            }
//
//            listObservableEmitter.onNext(notificationSortedObj);
//            listObservableEmitter.onComplete();
//        });
//    }



}
