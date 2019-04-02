package com.example.softmills.phlog.ui.campaigns;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.softmills.phlog.R;

import com.example.softmills.phlog.Utiltes.ErrorUtils;
import com.example.softmills.phlog.base.commonmodel.Campaign;
import com.example.softmills.phlog.base.widgets.PagingController;
import com.example.softmills.phlog.base.BaseFragment;
import com.example.softmills.phlog.base.widgets.CustomRecyclerView;
import com.example.softmills.phlog.ui.campaigns.inner.ui.CampaignInnerActivity;
import com.example.softmills.phlog.ui.campaigns.presenter.CampaignPresenter;
import com.example.softmills.phlog.ui.campaigns.presenter.CampaignPresenterImpl;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by abdalla_maged on 10/1/2018.
 */
public class CampaignsFragment extends BaseFragment implements CampaignFragmentView {

    private static final String TAG = CampaignsFragment.class.getSimpleName();

    private View mainView;
    private ProgressBar progressBar;
    private CustomRecyclerView allCampaignsRv;
    private AllCampaignsAdapter allCampaignsAdapter;
    private List<Campaign> homeCampaignList = new ArrayList<>();
    private CampaignPresenter campaignPresenter;
    private PagingController pagingController;
    private ConstraintLayout noCampaignsPrompt;

    private CompositeDisposable disposables = new CompositeDisposable();

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
        initListener();
        campaignPresenter.getAllCampaign(0);
    }


    @Override
    protected void initViews() {
        progressBar = mainView.findViewById(R.id.all_home_campaign_progress_bar);
        allCampaignsRv = mainView.findViewById(R.id.all_campaigns_rv);
        allCampaignsAdapter = new AllCampaignsAdapter(getContext(), homeCampaignList);
        allCampaignsRv.setAdapter(allCampaignsAdapter);
        noCampaignsPrompt = mainView.findViewById(R.id.no_added_campaign_prompt);

    }

    @Override
    protected void initPresenter() {
        campaignPresenter = new CampaignPresenterImpl(getContext(), this);
    }

    private void initListener() {

        pagingController = new PagingController(allCampaignsRv) {
            @Override
            public void getPagingControllerCallBack(int page) {
                campaignPresenter.getAllCampaign(page);
            }
        };
        allCampaignsAdapter.campaignLister =  new AllCampaignsAdapter.CampaignLister() {
            @Override
            public void onCampaignClicked(String campaignID) {
                Intent intent = new Intent(getContext(), CampaignInnerActivity.class);
                intent.putExtra(CampaignInnerActivity.CAMPAIGN_ID, String.valueOf(campaignID));
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }

            @Override
            public void onFollowCampaign(Campaign campaign, int i) {
                progressBar.setVisibility(View.VISIBLE);
                   Disposable disposable = campaignPresenter.joinCampaign(campaign.id.toString())
                           .subscribeOn(Schedulers.io())
                           .observeOn(AndroidSchedulers.mainThread())
                           .doFinally(() -> progressBar.setVisibility(View.GONE))
                           .subscribe(success -> {
                               if (success) {
                                   campaign.isJoined = true;
                                   allCampaignsAdapter.notifyItemChanged(homeCampaignList.indexOf(campaign));
                                   onCampaignClicked(String.valueOf(campaign.id));
                               }
                           }, throwable -> {
                               ErrorUtils.Companion.setError(getContext(), TAG, throwable);
                           });
                   disposables.add(disposable);
            }
        };

    }




    @Override
    public void viewAllCampaign(List<Campaign> homeCampaignList) {
        allCampaignsRv.setVisibility(View.VISIBLE);
        this.homeCampaignList.addAll(homeCampaignList);
        allCampaignsAdapter.notifyDataSetChanged();
        if (this.homeCampaignList.isEmpty()) {
            noCampaignsPrompt.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showAllCampaignProgress(boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }

    }

    @Override
    public void showMessage(String msg) {
        showToast(msg);
    }
}
