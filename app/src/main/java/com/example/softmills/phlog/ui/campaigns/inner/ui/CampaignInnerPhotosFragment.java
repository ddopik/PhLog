package com.example.softmills.phlog.ui.campaigns.inner.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.softmills.phlog.R;
import com.example.softmills.phlog.base.commonmodel.BaseImage;
import com.example.softmills.phlog.base.widgets.PagingController;
import com.example.softmills.phlog.base.BaseFragment;
import com.example.softmills.phlog.base.widgets.CustomRecyclerView;
import com.example.softmills.phlog.ui.album.view.AllAlbumImgActivity;
import com.example.softmills.phlog.ui.campaigns.inner.presenter.CampaignInnerPhotosFragmentPresenter;
import com.example.softmills.phlog.ui.campaigns.inner.presenter.CampaignInnerPhotosFragmentPresenterImpl;
import com.example.softmills.phlog.ui.photographerprofile.view.ph_photos.view.PhotoGrapherPhotosAdapter;

import java.util.ArrayList;
import java.util.List;

import static com.example.softmills.phlog.ui.album.view.AllAlbumImgActivity.ALL_ALBUM_IMAGES;
import static com.example.softmills.phlog.ui.album.view.AllAlbumImgActivity.LIST_NAME;
import static com.example.softmills.phlog.ui.album.view.AllAlbumImgActivity.SELECTED_IMG_ID;

/**
 * Created by abdalla_maged on 10/8/2018.
 */
public class CampaignInnerPhotosFragment extends BaseFragment implements CampaignInnerPhotosFragmentView {

    private String campaignID;
    private boolean shouldLoadImage;
    private View mainView;
    private CustomRecyclerView campaignInnerRv;
    private ProgressBar campaignInnerProgressBar;
    private List<BaseImage> photoGrapherPhotoList = new ArrayList<>();
    private PhotoGrapherPhotosAdapter photoGrapherPhotosAdapter;
    private PagingController pagingController;
    private CampaignInnerPhotosFragmentPresenter campaignInnerPhotosFragmentPresenter;

    public static CampaignInnerPhotosFragment getInstance(String campaignID, boolean shouldLoadImages) {
        CampaignInnerPhotosFragment fragment = new CampaignInnerPhotosFragment();
        fragment.campaignID = campaignID;
        fragment.shouldLoadImage = shouldLoadImages;
        return fragment;

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
        if (!shouldLoadImage)
            mainView.findViewById(R.id.still_running_prompt).setVisibility(View.VISIBLE);
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
                campaignInnerPhotosFragmentPresenter.getCampaignInnerPhotos(campaignID, page);
            }
        };

        photoGrapherPhotosAdapter.photoAction = photoGrapherSavedPhoto -> {

            Intent intent = new Intent(getActivity(), AllAlbumImgActivity.class);
            intent.putExtra(SELECTED_IMG_ID, photoGrapherSavedPhoto.id);
            intent.putExtra(LIST_NAME, photoGrapherSavedPhoto.photographer.userName);
            intent.putParcelableArrayListExtra(ALL_ALBUM_IMAGES, (ArrayList<? extends Parcelable>) photoGrapherPhotoList);
            startActivity(intent);
        };
    }


    @Override
    public void getInnerCampaignPhotos(List<BaseImage> campaignInnerPhotoList) {
        this.photoGrapherPhotoList.addAll(campaignInnerPhotoList);
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
