package com.example.softmills.phlog.ui.photographerprofile.view.ph_photos.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.softmills.phlog.R;
import com.example.softmills.phlog.base.BaseFragment;
import com.example.softmills.phlog.base.widgets.CustomRecyclerView;
import com.example.softmills.phlog.ui.photographerprofile.view.ph_photos.model.PhotoGrapherPhoto;
import com.example.softmills.phlog.ui.photographerprofile.view.ph_photos.presenter.FragmentPhotoGrapherPhotosPresenter;
import com.example.softmills.phlog.ui.photographerprofile.view.ph_photos.presenter.FragmentPhotoGrapherPhotosPresenterImpl;
import com.example.softmills.phlog.ui.photographerprofile.view.ph_photos.presenter.PhotoGrapherPhotosAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Abdalla_maged on 9/30/2018.
 */
public class FragmentPhotoGrapherPhotos extends BaseFragment implements FragmentPhotoGrapherPhotosView {

    private static String TAG=FragmentPhotoGrapherPhotos.class.getSimpleName();
    private View mainView;
    private List<PhotoGrapherPhoto> photoGrapherPhotoList = new ArrayList<PhotoGrapherPhoto>();
    private PhotoGrapherPhotosAdapter photographerSavedPhotoAdapter;
    private FragmentPhotoGrapherPhotosPresenter fragmentPhotoGrapherPhotosPresenter;
    private LinearLayoutManager mLayoutManager;
    private CustomRecyclerView photosRv;
    private ProgressBar photosProgress;
    // The total number of items in the dataset after the last load
    private int previousTotalItemCount = 0;
    private boolean loading = true;
    private int visibleThreshold = 9;
    int firstVisibleItem, visibleItemCount, totalItemCount;
    private int startingPageIndex = 0;
    private int currentPage = 0;

    private OnFragmentScroll onFragmentScroll;

    public static FragmentPhotoGrapherPhotos getInstance() {
        return new FragmentPhotoGrapherPhotos();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.fragment_photographer_photos, container, false);
        mLayoutManager = new LinearLayoutManager(getContext());
        return mainView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initPresenter();
        initViews();
        initListener();
        fragmentPhotoGrapherPhotosPresenter.getPhotographerPhotos("0"); //initialPage
    }

    @Override
    protected void initPresenter() {
        fragmentPhotoGrapherPhotosPresenter = new FragmentPhotoGrapherPhotosPresenterImpl(getContext(),this);
    }

    @Override
    protected void initViews() {
        photographerSavedPhotoAdapter = new PhotoGrapherPhotosAdapter(getContext(), photoGrapherPhotoList);
        photosRv = mainView.findViewById(R.id.photos_rv);
        photosRv.setAdapter(photographerSavedPhotoAdapter);
        photosProgress = mainView.findViewById(R.id.photos_progress);


    }

    private void initListener() {

        photosRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(photosRv, dx, dy);
                LinearLayoutManager mLayoutManager = (LinearLayoutManager) photosRv
                        .getLayoutManager();

                visibleItemCount = photosRv.getChildCount();
                totalItemCount = mLayoutManager.getItemCount();
                firstVisibleItem = mLayoutManager.findFirstVisibleItemPosition();
                onScroll(firstVisibleItem, visibleItemCount, totalItemCount);

                if (dy > 0) {
                    // Scrolling Up
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
            fragmentPhotoGrapherPhotosPresenter.getPhotographerPhotos(String.valueOf(currentPage + 1));
            loading = true;
        }
    }

    @Override
    public void showPhotos(List<PhotoGrapherPhoto> photosList) {
        this.photoGrapherPhotoList.addAll(photosList);
        photographerSavedPhotoAdapter.notifyDataSetChanged();
    }

    @Override
    public void showMessage(String msg) {
        showToast(msg);
    }

    @Override
    public void showPhotosProgress(boolean state) {
        if (state) {
            photosProgress.setVisibility(View.VISIBLE);
        } else {
            photosProgress.setVisibility(View.GONE);
        }
    }

    public void setOnFragmentScroll(OnFragmentScroll onFragmentScroll) {
        this.onFragmentScroll = onFragmentScroll;
    }

    public interface OnFragmentScroll {
        void onScrollAction(boolean state);
    }
}
