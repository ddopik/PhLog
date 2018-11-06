package com.example.softmills.phlog.ui.campaigns.inner.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ProgressBar;

import com.example.softmills.phlog.R;
import com.example.softmills.phlog.base.widgets.PagingController;
import com.example.softmills.phlog.base.BaseFragment;
import com.example.softmills.phlog.base.widgets.CustomRecyclerView;
import com.example.softmills.phlog.ui.campaigns.inner.model.CampaignInnerPhoto;
import com.example.softmills.phlog.ui.campaigns.inner.presenter.CampaignInnerPhotosFragmentPresenter;
import com.example.softmills.phlog.ui.campaigns.inner.presenter.CampaignInnerPhotosFragmentPresenterImpl;
import com.example.softmills.phlog.ui.photographerprofile.view.ph_photos.model.PhotoGrapherPhoto;
import com.example.softmills.phlog.ui.photographerprofile.view.ph_photos.view.PhotoGrapherPhotosAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abdalla_maged on 10/8/2018.
 */
public class CampaignInnerPhotosFragment extends BaseFragment implements CampaignInnerPhotosFragmentView {

    private String campaignID;
    private View mainView;
    private CustomRecyclerView campaignInnerRv;
    private ProgressBar campaignInnerProgressBar;
    private List<PhotoGrapherPhoto> photoGrapherPhotoList = new ArrayList<PhotoGrapherPhoto>();
    private PhotoGrapherPhotosAdapter photoGrapherPhotosAdapter;
    private PagingController pagingController;
    private CampaignInnerPhotosFragmentPresenter campaignInnerPhotosFragmentPresenter;

    public static CampaignInnerPhotosFragment getInstance(String campaignID) {
        CampaignInnerPhotosFragment campaignInnerPhotosFragment = new CampaignInnerPhotosFragment();
        campaignInnerPhotosFragment.campaignID = campaignID;
        return campaignInnerPhotosFragment;

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mainView = inflater.inflate(R.layout.fragment_campaign_inner_photos, container, false);
        return mainView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initPresenter();
        initViews();
        initLister();
    }

    @Override
    protected void initViews() {
        campaignInnerRv = mainView.findViewById(R.id.campaign_inner_rv);
        campaignInnerProgressBar = mainView.findViewById(R.id.campaign_inner_progress_bar);

        photoGrapherPhotosAdapter = new PhotoGrapherPhotosAdapter(getContext(), photoGrapherPhotoList);
        campaignInnerRv.setAdapter(photoGrapherPhotosAdapter);
        campaignInnerPhotosFragmentPresenter.getCampaignInnerPhotos(campaignID, 0);
    }

    @Override
    protected void initPresenter() {
        campaignInnerPhotosFragmentPresenter = new CampaignInnerPhotosFragmentPresenterImpl(getActivity().getBaseContext(), this);
    }

    private void initLister() {
        pagingController = new PagingController(campaignInnerRv) {
            @Override
            public void getPagingControllerCallBack(int page) {
                campaignInnerPhotosFragmentPresenter.getCampaignInnerPhotos(campaignID, page + 1);
            }
        };
    }

    public static void setHeightDynamically(GridView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i = i + 2) {
            view = listAdapter.getView(i, view, listView);
            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight;
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    @Override
    public void getInnerCampaignPhotos(List<CampaignInnerPhoto> campaignInnerPhotoList) {
        photoGrapherPhotoList.addAll(campaignInnerPhotoList);
        photoGrapherPhotosAdapter.notifyDataSetChanged();

    }


    @Override
    public void viewCampaignInnerPhotosProgress(boolean state) {
        if (state) {
            campaignInnerProgressBar.setVisibility(View.VISIBLE);
        } else {
            campaignInnerProgressBar.setVisibility(View.GONE);
        }

    }


}
