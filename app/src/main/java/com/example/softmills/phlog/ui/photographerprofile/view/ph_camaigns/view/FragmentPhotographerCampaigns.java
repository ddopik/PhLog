package com.example.softmills.phlog.ui.photographerprofile.view.ph_camaigns.view;
/**
 * Created by Abdalla_maged on 9/30/2018.
 */
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.softmills.phlog.R;
import com.example.softmills.phlog.base.BaseFragment;
import com.example.softmills.phlog.base.widgets.CustomRecyclerView;
import com.example.softmills.phlog.ui.photographerprofile.view.ph_camaigns.presenter.FragmentPhotoGrapherCampaignsPresenter;
import com.example.softmills.phlog.ui.photographerprofile.view.ph_camaigns.presenter.FragmentPhotoGrapherCampaignsPresenterImpl;
import com.example.softmills.phlog.ui.userprofile.view.campaigns.model.Campaign;

import java.util.ArrayList;
import java.util.List;


public class FragmentPhotographerCampaigns extends BaseFragment implements FragmentPhotoGrapherCampaignsView {

    private View mainView;
    private List<Campaign> campaignList = new ArrayList<Campaign>();
    private photographerCampaignsAdapter photoGrapherCampaignsAdapter;
    private FragmentPhotoGrapherCampaignsPresenter photoGrapherCampaignsPresenter;
    private LinearLayoutManager mLayoutManager;
    private CustomRecyclerView campaignsRv;
    // The total number of items in the dataset after the last load
    private int previousTotalItemCount = 0;
    private boolean loading = true;
    private int visibleThreshold = 9;
    int firstVisibleItem, visibleItemCount, totalItemCount;
    private int startingPageIndex = 0;
    private int currentPage = 0;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.fragment_photographer_campains, container, false);

        mLayoutManager = new LinearLayoutManager(getContext());
        return mainView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initPresenter();
        initViews();
        initListener();
        photoGrapherCampaignsPresenter.getPhotographerCampaigns(0);
    }

    @Override
    protected void initPresenter() {
        photoGrapherCampaignsPresenter = new FragmentPhotoGrapherCampaignsPresenterImpl(this);

    }

    @Override
    protected void initViews() {
        photoGrapherCampaignsAdapter = new photographerCampaignsAdapter(getContext(), campaignList);
        campaignsRv = mainView.findViewById(R.id.campaigns_rv);
        campaignsRv.setAdapter(photoGrapherCampaignsAdapter);

    }

    private void initListener() {
        campaignsRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(campaignsRv, dx, dy);
                LinearLayoutManager mLayoutManager = (LinearLayoutManager) campaignsRv
                        .getLayoutManager();

                visibleItemCount = campaignsRv.getChildCount();
                totalItemCount = mLayoutManager.getItemCount();
                firstVisibleItem = mLayoutManager.findFirstVisibleItemPosition();
                onScroll(firstVisibleItem, visibleItemCount, totalItemCount);
            }
        });
    }

    public void onScroll(int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        // If the total item count is zero and the previous isn't, assume the
        // list is invalidated and should be reset back to initial state
        if (totalItemCount < previousTotalItemCount) {
            this.currentPage = this.startingPageIndex;
            this.previousTotalItemCount = totalItemCount;
            if (totalItemCount == 0) {
                this.loading = true;
            }
        }
        // If it’s still loading, we check to see if the dataset count has
        // changed, if so we conclude it has finished loading and update the current page
        // number and total item count.
        if (loading && (totalItemCount > previousTotalItemCount)) {
            loading = false;
            previousTotalItemCount = totalItemCount;
            currentPage++;
        }

        // If it isn’t currently loading, we check to see if we have breached
        // the visibleThreshold and need to reload more data.
        // If we do need to reload some more data, we execute onLoadMore to fetch the data.
        if (!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem +
                visibleThreshold)) {
//            onLoadMore(currentPage + 1, totalItemCount);
            photoGrapherCampaignsPresenter.getPhotographerCampaigns(currentPage + 1);
            loading = true;
        }
    }

    @Override
    public void showCampaigns(List<Campaign> campaignList) {
        this.campaignList.clear();
        this.campaignList.addAll(campaignList);
        photoGrapherCampaignsAdapter.notifyDataSetChanged();
    }
}