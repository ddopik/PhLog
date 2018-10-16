package com.example.softmills.phlog.ui.photographerprofile.view.ph_follow.following.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.widget.Filter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abdalla_maged on 10/14/2018.
 */
public class PhotoGrapherFollowViewPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> mFragmentList;


    public PhotoGrapherFollowViewPagerAdapter(FragmentManager manager, List<Fragment> mFragmentList) {
        super(manager);
        this.mFragmentList = mFragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }



}

