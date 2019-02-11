package com.example.softmills.phlog.ui.notification.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.softmills.phlog.R;
import com.example.softmills.phlog.base.BaseFragment;
import com.example.softmills.phlog.base.widgets.CustomRecyclerView;
import com.example.softmills.phlog.base.widgets.PagingController;
import com.example.softmills.phlog.ui.notification.model.NotificationResponse;
import com.example.softmills.phlog.ui.notification.model.NotificationSortedObj;
import com.example.softmills.phlog.ui.notification.presenter.NotificationPresenter;
import com.example.softmills.phlog.ui.notification.presenter.NotificationPresenterImp;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by abdalla_maged On Nov,2018
 */
public class NotificationFragment extends BaseFragment implements NotificationFragmentView {
    private View mainView;
    private ProgressBar notificationProgress;
    private CustomRecyclerView notificationRv;
    private NotificationPresenter notificationPresenter;
    private NotificationAdapter notificationAdapter;
    private PagingController pagingController;
    private List<NotificationResponse> allNotificationResponseList = new ArrayList<>();
    private List<NotificationResponse> recentNotificationResponseList = new ArrayList<>();
    private List<NotificationResponse> oldNotificationResponseList = new ArrayList<>();
    private List<NotificationResponse> notificationResponseList = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_notification, container, false);
        return mainView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initPresenter();
        initViews();
        initListener();
        notificationPresenter.getNotification("0");
    }

    @Override
    public void showToast(String msg) {
        super.showToast(msg);
    }

    @Override
    protected void initPresenter() {

        notificationPresenter = new NotificationPresenterImp(getContext(), this);

    }


    @Override
    protected void initViews() {


        notificationRv = mainView.findViewById(R.id.notification_rv);
        notificationProgress = mainView.findViewById(R.id.notification_progress);
        notificationAdapter = new NotificationAdapter(notificationResponseList);
        notificationRv.setAdapter(notificationAdapter);

    }

    private void initListener() {
        pagingController = new PagingController(notificationRv) {
            @Override
            public void getPagingControllerCallBack(int page) {
                notificationPresenter.getNotification(String.valueOf(page));
            }
        };
    }

    @Override
    public void viewNotification(NotificationSortedObj notificationSortedObj) {

        this.notificationResponseList.clear();
        NotificationResponse notificationResponse = new NotificationResponse();
        notificationResponse.entityId = NotificationAdapter.itemType_SEPARATOR;

        this.recentNotificationResponseList.addAll(notificationSortedObj.newNotificationList);
        this.oldNotificationResponseList.addAll(notificationSortedObj.oldNotificationList);

        if (oldNotificationResponseList.size() > 0) {

            notificationResponse.message = Objects.requireNonNull(getActivity()).getString(R.string.old);
            oldNotificationResponseList.set(0, notificationResponse);
        }

        notificationResponseList.addAll(recentNotificationResponseList);
        notificationResponseList.addAll(oldNotificationResponseList);


        if (recentNotificationResponseList.size() > 0) {
            notificationResponse.message = Objects.requireNonNull(getActivity()).getString(R.string.recent);
            notificationResponseList.set(0, notificationResponse);
        }

        notificationAdapter.notifyDataSetChanged();


    }

    @Override
    public void viewNotificationProgress(boolean state) {
        if (state) {
            notificationProgress.setVisibility(View.VISIBLE);
        } else {
            notificationProgress.setVisibility(View.GONE);
        }
    }
}

