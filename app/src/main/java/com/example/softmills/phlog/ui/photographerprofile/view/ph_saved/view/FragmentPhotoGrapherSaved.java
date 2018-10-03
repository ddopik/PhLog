package com.example.softmills.phlog.ui.photographerprofile.view.ph_saved.view;

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
import com.example.softmills.phlog.ui.photographerprofile.view.ph_photos.view.FragmentPhotoGrapherPhotos;
import com.example.softmills.phlog.ui.photographerprofile.view.ph_saved.model.PhotoGrapherSavedPhoto;
import com.example.softmills.phlog.ui.photographerprofile.view.ph_saved.presenter.FragmentPhotoGrapherSavedPresenter;
import com.example.softmills.phlog.ui.photographerprofile.view.ph_saved.presenter.FragmentPhotoGrapherSavedPresenterImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Abdalla_maged on 9/30/2018.
 */
public class FragmentPhotoGrapherSaved extends BaseFragment implements FragmentPhotoGrapherSavedPhotosView {

    private View mainView;
    private List<PhotoGrapherSavedPhoto> photoGrapherSavedPhotoList = new ArrayList<PhotoGrapherSavedPhoto>();
    private PhotographerSavedPhotoAdapter photographerSavedPhotoAdapter;
    private FragmentPhotoGrapherSavedPresenter fragmentPhotoGrapherSavedPresenter;
    private LinearLayoutManager mLayoutManager;
    private CustomRecyclerView savedPhotosRv;
    // The total number of items in the dataset after the last load
    private int previousTotalItemCount = 0;
    private boolean loading = true;
    private int visibleThreshold = 9;
    int firstVisibleItem, visibleItemCount, totalItemCount;
    private int startingPageIndex = 0;
    private int currentPage = 0;

    private OnFragmentScroll onFragmentScroll;



    public static FragmentPhotoGrapherSaved getInstance() {
        return new FragmentPhotoGrapherSaved();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.fragment_photographer_saved, container, false);
        mLayoutManager = new LinearLayoutManager(getContext());
        return mainView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initPresenter();
        initViews();
        initListener();
        fragmentPhotoGrapherSavedPresenter.getPhotographerSavedPhotos("0"); //initialPage
    }

    @Override
    protected void initPresenter() {
        fragmentPhotoGrapherSavedPresenter = new FragmentPhotoGrapherSavedPresenterImpl(getContext(),this);
    }

    @Override
    protected void initViews() {
        photographerSavedPhotoAdapter = new PhotographerSavedPhotoAdapter(getContext(), photoGrapherSavedPhotoList);
        savedPhotosRv = mainView.findViewById(R.id.saved_photos_rv);
        savedPhotosRv.setAdapter(photographerSavedPhotoAdapter);
    }

    private void initListener() {
        savedPhotosRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(savedPhotosRv, dx, dy);
                LinearLayoutManager mLayoutManager = (LinearLayoutManager) savedPhotosRv
                        .getLayoutManager();
                visibleItemCount = savedPhotosRv.getChildCount();
                totalItemCount = mLayoutManager.getItemCount();
                firstVisibleItem = mLayoutManager.findFirstVisibleItemPosition();
                onScroll(firstVisibleItem, visibleItemCount, totalItemCount);
                if (dy > 0) {
                    // Scrolling up
                    onFragmentScroll.onScrollAction(false);
                } else {
                    onFragmentScroll.onScrollAction(true);
                    // Scrolling down
                }
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
            fragmentPhotoGrapherSavedPresenter.getPhotographerSavedPhotos(String.valueOf(currentPage + 1));
            loading = true;
        }
    }

    @Override
    public void showSavedPhotos(List<PhotoGrapherSavedPhoto> photosList) {
        this.photoGrapherSavedPhotoList.addAll(photosList);
        photographerSavedPhotoAdapter.notifyDataSetChanged();
    }

    @Override
    public void showMessage(String msg) {
        showToast(msg);
    }

    public void setOnFragmentScroll(OnFragmentScroll onFragmentScroll) {
        this.onFragmentScroll = onFragmentScroll;
    }

    public interface OnFragmentScroll {
        void onScrollAction(boolean state);
    }
}
