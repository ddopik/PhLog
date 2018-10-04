package com.example.softmills.phlog.ui.campaigns;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.softmills.phlog.R;

import com.example.softmills.phlog.Utiltes.pagingController;
import com.example.softmills.phlog.base.BaseFragment;
import com.example.softmills.phlog.base.widgets.CustomRecyclerView;
import com.example.softmills.phlog.ui.campaigns.model.Campaign;
import com.example.softmills.phlog.ui.campaigns.presenter.CampaignPresenter;
import com.example.softmills.phlog.ui.campaigns.presenter.CampaignPresenterImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abdalla_maged on 10/1/2018.
 */
public class CampaignsFragment extends BaseFragment implements CampaignFragmentView {


    private View mainView;
    private ProgressBar progressBar;
    private CustomRecyclerView allCampaignsRv;
    private AllCampaignsAdapter allCampaignsAdapter;
    private List<Campaign> campaignList = new ArrayList<>();
    private CampaignPresenter campaignPresenter;

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
        mainView = inflater.inflate(R.layout.fragment_campaigns, container, false);

//        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        return mainView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initPresenter();
        initViews();
        initListener();
        campaignPresenter.getAllCampaign("0");
    }


    @Override
    protected void initViews() {
        progressBar = mainView.findViewById(R.id.all_home_campaign_progress_bar);
        allCampaignsRv = mainView.findViewById(R.id.all_campaigns_rv);
        allCampaignsAdapter = new AllCampaignsAdapter(getContext(), campaignList);
        allCampaignsRv.setAdapter(allCampaignsAdapter);

    }

    @Override
    protected void initPresenter() {
        campaignPresenter = new CampaignPresenterImpl(getContext(), this);
    }

    private void initListener() {

        pagingController pagingController = new pagingController((RecyclerView) allCampaignsRv);
        pagingController.setPagingControllerCallBack(page -> {
            campaignPresenter.getAllCampaign(String.valueOf(currentPage + 1)); });

//        allCampaignsRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(allCampaignsRv, dx, dy);
//                LinearLayoutManager mLayoutManager = (LinearLayoutManager) allCampaignsRv
//                        .getLayoutManager();
//                visibleItemCount = allCampaignsRv.getChildCount();
//                totalItemCount = mLayoutManager.getItemCount();
//                firstVisibleItem = mLayoutManager.findFirstVisibleItemPosition();
//                onScroll(firstVisibleItem, visibleItemCount, totalItemCount);
//                if (dy > 0) {
//                    // Scrolling up
//
//                } else {
//
//                    // Scrolling down
//                }
//            }
//        });
    }
//
//    public void onScroll(int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//        // If the total item count is zero and the previous isn't, assume the
//        // list is invalidated and should be reset back to initial state
//        if (totalItemCount < previousTotalItemCount) {
//            this.currentPage = this.startingPageIndex;
//            this.previousTotalItemCount = totalItemCount;
//            if (totalItemCount == 0) {
//                this.loading = true;
//            }
//        }
//        // If it’s still loading, we check to see if the dataset count has
//        // changed, if so we conclude it has finished loading and update the current page
//        // number and total item count.
//        if (loading && (totalItemCount > previousTotalItemCount)) {
//            loading = false;
//            previousTotalItemCount = totalItemCount;
//            currentPage++;
//        }
//
//        // If it isn’t currently loading, we check to see if we have breached
//        // the visibleThreshold and need to reload more data.
//        // If we do need to reload some more data, we execute onLoadMore to fetch the data.
//        if (!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem +
//                visibleThreshold)) {
////            onLoadMore(currentPage + 1, totalItemCount);
//            campaignPresenter.getAllCampaign(String.valueOf(currentPage + 1));
//            loading = true;
//        }
//    }

    @Override
    public void showToast(String msg) {
        super.showToast(msg);
    }

    @Override
    public void viewAllCampaign(List<Campaign> campaignList) {
        allCampaignsRv.setVisibility(View.VISIBLE);
        this.campaignList.clear();
        this.campaignList.addAll(campaignList);
        allCampaignsAdapter.notifyDataSetChanged();

    }

    @Override
    public void showAllCampaginProgress(boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }

    }
}
