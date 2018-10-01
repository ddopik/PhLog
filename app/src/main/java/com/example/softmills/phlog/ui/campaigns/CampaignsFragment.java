package com.example.softmills.phlog.ui.campaigns;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.softmills.phlog.R;
import com.example.softmills.phlog.base.BaseActivity;
import com.example.softmills.phlog.base.BaseFragment;
import com.example.softmills.phlog.base.widgets.CustomRecyclerView;

/**
 * Created by abdalla_maged on 10/1/2018.
 */
public class CampaignsFragment extends BaseFragment {


    private View mainView;
    private CustomRecyclerView allCampaignsRv;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.fragment_campaigns, container, false);
        return mainView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initPresenter();
        initViews();
    }




    @Override
    protected void initViews() {
        allCampaignsRv = mainView.findViewById(R.id.all_campaigns_rv);
    }
    @Override
    protected void initPresenter() {

    }
    @Override
    public void showToast(String msg) {
        super.showToast(msg);
    }
}
