package com.example.softmills.phlog.ui.photographerprofile.view.ph_following.following.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.softmills.phlog.R;
import com.example.softmills.phlog.Utiltes.PagingController;
import com.example.softmills.phlog.base.BaseFragment;
import com.example.softmills.phlog.ui.photographerprofile.view.ph_following.album.PhotoGrapherAlbumFragment;
import com.example.softmills.phlog.ui.photographerprofile.view.ph_following.brand.PhotoGrapherBrandFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Abdalla_maged on 9/30/2018.
 */
public class PhotoGrapherFollowFragment extends BaseFragment {

    private View mainView;
    private TabLayout followTableLayout;
    private ViewPager followViewPager;
    private PhotoGrapherFollowViewPagerAdapter photoGrapherFollowViewPagerAdapter;
    private PagingController pagingController;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mainView = inflater.inflate(R.layout.fragment_follow, container, false);
        return mainView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initPresenter();
        initViews();
    }


    @Override
    protected void initPresenter() {

    }

    @Override
    protected void initViews() {

        followTableLayout = mainView.findViewById(R.id.photographer_follow_tabs);
        followViewPager = mainView.findViewById(R.id.photographer_follow_pager);
        photoGrapherFollowViewPagerAdapter = new PhotoGrapherFollowViewPagerAdapter(getContext(), getChildFragmentManager(), getFragmentList(), getFragmentIcon());
        followViewPager.setAdapter(photoGrapherFollowViewPagerAdapter);
        followTableLayout.setupWithViewPager(followViewPager);


    }

    @Override
    public void showToast(String msg) {
        super.showToast(msg);
    }

    private List<Fragment> getFragmentList() {
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(PhotoGrapherFollowingFragment.getInstance());
        fragmentList.add(PhotoGrapherAlbumFragment.getInstance());
        fragmentList.add(PhotoGrapherBrandFragment.getInstance());
        return fragmentList;
    }

    private List<Integer> getFragmentIcon() {
        List<Integer> fragmentIcons = new ArrayList<Integer>();
        fragmentIcons.add(R.drawable.ic_star_on_24dp);
        fragmentIcons.add(R.drawable.ic_star_on_24dp);
        fragmentIcons.add(R.drawable.ic_star_on_24dp);
        return fragmentIcons;
    }

}
