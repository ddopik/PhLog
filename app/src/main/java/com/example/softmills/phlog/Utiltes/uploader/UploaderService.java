package com.example.softmills.phlog.Utiltes.uploader;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Messenger;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.softmills.phlog.R;
import com.example.softmills.phlog.Utiltes.ErrorUtils;
import com.example.softmills.phlog.Utiltes.ImageUtils;
import com.example.softmills.phlog.Utiltes.PrefUtils;
import com.example.softmills.phlog.Utiltes.notification.NotificationFactory;
import com.example.softmills.phlog.Utiltes.uploader.UploaderService.Communicator.Action;
import com.example.softmills.phlog.base.commonmodel.UploadImageData;
import com.example.softmills.phlog.network.BaseNetworkApi;
import com.example.softmills.phlog.ui.signup.model.UploadImgResponse;

import java.io.File;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class UploaderService extends Service {

    public static final String TAG = UploaderService.class.getSimpleName();

    public static final int ADD_COMMUNICATOR = 0;
    public static final int REMOVE_COMMUNICATOR = 1;
    public static final int UPLOAD_FILE = 2;

    private NotificationFactory notificationFactory = new NotificationFactory();
    private CompositeDisposable disposables = new CompositeDisposable();
    private int uploading;

    private Communicator communicator;

    private Messenger messenger = new Messenger(new Handler(message -> {
        switch (message.what) {
            case ADD_COMMUNICATOR:
                if (message.obj instanceof Communicator)
                    communicator = (Communicator) message.obj;
                break;
            case REMOVE_COMMUNICATOR:
                communicator = null;
                break;
            case UPLOAD_FILE:
                if (communicator != null)
                    communicator.onAction(Action.UPLOAD_STARTED);
                if (message.obj instanceof UploadImageData) {
                    uploading++;
                    notificationFactory.changeNotificationContent(getApplicationContext(), getString(R.string.permanent_notification_channel_id), getString(R.string.permanent_notification_id), getString(R.string.phlog_uploading), R.drawable.phlog_logo);
                    UploadImageData object = (UploadImageData) message.obj;
                    Consumer<UploadImgResponse> responseConsumer = uploadImgResponse -> {
                        uploading--;
                        if (communicator != null)
                            communicator.onAction(Action.UPLOAD_FINISHED);
                        checkAndStop();
                    };


                    Consumer<Throwable> throwableConsumer = throwable -> {
                        uploading--;
                        if (communicator != null)
                            communicator.onAction(Action.UPLOAD_FAILED);
                        Log.e(TAG, throwable.toString());
                        try {
                            ErrorUtils.Companion.setError(getApplicationContext(), TAG, throwable);
                        } catch (Exception e) {
                            Log.e(TAG, e.getMessage());
                        }
                        checkAndStop();
                    };


                    String token = PrefUtils.getUserToken(getApplicationContext());
                    File imageFile = new File(ImageUtils.getBitMapFilePath(getBaseContext(), object.getBitMapUri()));
                    switch (object.getUploadImageType()) {


                        case NORMAL_IMG:
                            Disposable d1 = BaseNetworkApi.uploadPhotoGrapherPhoto(token, object.getImageCaption(), object.getImageLocation(), imageFile, object.getTags(), object.getFilters(), (bytesUploaded, totalBytes) -> {
                                int p = (int) ((bytesUploaded / (float) totalBytes) * 100);
                                communicator.onAction(Action.PROGRESS, p);
                            }).subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(responseConsumer, throwableConsumer);
                            disposables.add(d1);
                            break;
                        case CAMPAIGN_IMG:
                            Disposable d2 = BaseNetworkApi.uploadCampaignPhoto(token, object.getImageCaption(), object.getImageLocation(), imageFile, object.getTags(), object.getFilters(), object.getImageId())
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(responseConsumer, throwableConsumer);
                            disposables.add(d2);
                            break;
                    }

                }
                break;
        }
        return true;
    }));

    private void checkAndStop() {
        if (uploading == 0 && bound == 0) {
            notificationFactory.changeNotificationContent(getApplicationContext(), getString(R.string.permanent_notification_channel_id), getString(R.string.permanent_notification_id), getString(R.string.phlog_finished_uploading), R.drawable.phlog_logo);
            stopForeground(false);
            stopSelf();
        } else if (uploading == 0) {
            notificationFactory.changeNotificationContent(getApplicationContext(), getString(R.string.permanent_notification_channel_id), getString(R.string.permanent_notification_id), getString(R.string.phlog_finished_uploading), R.drawable.phlog_logo);
        }
    }

    public UploaderService() {
    }

    private int bound = 0;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        bound++;
        return messenger.getBinder();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        bound--;
        checkAndStop();
        return super.onUnbind(intent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        notificationFactory.createNotificationChannel(getApplicationContext()
                , getString(R.string.permanent_notification_channel_id)
                , getString(R.string.permanent_notification_channel_name)
                , getString(R.string.permanent_notification_channel_desc));
        Notification notification = notificationFactory.createNotification(getApplicationContext()
                , getString(R.string.permanent_notification_channel_id)
                , R.drawable.phlog_logo
                , getString(R.string.phlog_uploading)
                , null, false, null);
        startForeground(Integer.valueOf(getString(R.string.permanent_notification_id)), notification);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        disposables.dispose();
    }

    public interface Communicator {
        void onAction(Action action, Object... objects);

        enum Action {
            PROGRESS,
            UPLOAD_STARTED,
            UPLOAD_FINISHED,
            UPLOAD_FAILED
        }
    }
}
