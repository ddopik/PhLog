package com.example.softmills.phlog.ui.photographerprofile.view.ph_follow.following.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;


import com.example.softmills.phlog.R;
import com.example.softmills.phlog.Utiltes.Constants;
import com.example.softmills.phlog.base.widgets.PagingController;
import com.example.softmills.phlog.base.BaseFragment;
import com.example.softmills.phlog.base.widgets.CustomRecyclerView;
import com.example.softmills.phlog.ui.photographerprofile.view.ph_follow.following.model.PhotoGrapherFollowingObj;
import com.example.softmills.phlog.ui.photographerprofile.view.ph_follow.following.presenter.PhotoGrapherFollowingInPresenter;
import com.example.softmills.phlog.ui.photographerprofile.view.ph_follow.following.presenter.PhotoGrapherFollowingInPresenterImpl;
import com.example.softmills.phlog.ui.userprofile.view.UserProfileActivity;
import com.jakewharton.rxbinding3.widget.RxTextView;
import com.jakewharton.rxbinding3.widget.TextViewTextChangeEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by abdalla_maged on 10/11/2018.
 */
public class PhotoGrapherFollowingFragment extends BaseFragment implements PhotoGrapherFollowingFragmentView {


    private List<PhotoGrapherFollowingObj> photoGrapherFollowingList = new ArrayList<PhotoGrapherFollowingObj>();
    private PhotoGrapherFollowingAdapter photoGrapherFollowingAdapter;
    private View mainView;
    private CustomRecyclerView followingRV;
    private PhotoGrapherFollowingInPresenter photoGrapherFollowingInPresenter;
    private PagingController pagingController;
    private EditText searchEditText;
    private ProgressBar followingProgressBar;
    private CompositeDisposable disposable = new CompositeDisposable();

    public static PhotoGrapherFollowingFragment getInstance() {
        return new PhotoGrapherFollowingFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return mainView = inflater.inflate(R.layout.fragment_photographer_following, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initPresenter();
        initViews();
        initListener();
        //default following list
        photoGrapherFollowingInPresenter.getPhotoGrapherFollowing(0);
    }

    @Override
    public void showToast(String msg) {
        super.showToast(msg);
    }

    @Override
    protected void initPresenter() {
        photoGrapherFollowingInPresenter = new PhotoGrapherFollowingInPresenterImpl(getContext(), this);
    }


    @Override
    protected void initViews() {

        followingProgressBar = mainView.findViewById(R.id.photographer_following_progress_bar);
        followingRV = mainView.findViewById(R.id.photographer_following_rv);
        photoGrapherFollowingAdapter = new PhotoGrapherFollowingAdapter(getContext(), photoGrapherFollowingList);
        followingRV.setAdapter(photoGrapherFollowingAdapter);
        searchEditText = mainView.findViewById(R.id.search_following);
    }

    private void initListener() {

        photoGrapherFollowingAdapter.followingAdapterListener= photoGrapherFollowingObj -> {
            Intent intent=new Intent(getActivity(), UserProfileActivity.class);
            intent.putExtra(UserProfileActivity.USER_ID,photoGrapherFollowingObj.userNameId);
            getContext().startActivity(intent);
        };


        pagingController = new PagingController(followingRV) {
            @Override
            public void getPagingControllerCallBack(int page) {
                if(searchEditText.getText().length() ==0){
                    //default following list paging
                    photoGrapherFollowingInPresenter.getPhotoGrapherFollowing(page);
                }else {
                    photoGrapherFollowingInPresenter.getPhotoGrapherFollowingSearch(page,searchEditText.getText().toString());
                }

            }
        };




        disposable.add(

                RxTextView.textChangeEvents(searchEditText)
                        .skipInitialValue()
                        .debounce(Constants.QUERY_SEARCH_TIME_OUT, TimeUnit.MILLISECONDS)
                        .distinctUntilChanged()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(searchQuery()));



    }
    private DisposableObserver<TextViewTextChangeEvent> searchQuery() {
        return new DisposableObserver<TextViewTextChangeEvent>() {
            @Override
            public void onNext(TextViewTextChangeEvent textViewTextChangeEvent) {
                // user cleared search get default data
                if (searchEditText.getText().length() == 0) {
                    photoGrapherFollowingList.clear();
                    photoGrapherFollowingInPresenter.getPhotoGrapherFollowing(0);
                }else {
                    // user is searching clear default value and get new search List
                    photoGrapherFollowingList.clear();
                    photoGrapherFollowingInPresenter.getPhotoGrapherFollowingSearch(0, searchEditText.getText().toString());
                }



            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
    }
    @Override
    public void viewPhotographerFollowingIn(List<PhotoGrapherFollowingObj> photoGrapherFollowingObjList) {
        this.photoGrapherFollowingList.addAll(photoGrapherFollowingObjList);
        photoGrapherFollowingAdapter.notifyDataSetChanged();
    }

    @Override
    public void viewPhotographerFollowingSearch(List<PhotoGrapherFollowingObj> photoGrapherFollowingObjList) {
        this.photoGrapherFollowingList.addAll(photoGrapherFollowingObjList);
        photoGrapherFollowingAdapter.notifyDataSetChanged();
    }

    @Override
    public void viewPhotographerFollowingInProgress(boolean state) {
        if (state) {
            followingProgressBar.setVisibility(View.VISIBLE);
        } else {
            followingProgressBar.setVisibility(View.GONE);
        }

    }

    @Override
    public void viewMessage(String msg) {
        showToast(msg);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        disposable.clear();
    }
}
