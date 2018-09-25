package com.example.softmills.phlog.ui.profile.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class PhotoGrapherProfileViewPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> mFragmentList ;
    private List<String> mFragmentTitleList ;

    public PhotoGrapherProfileViewPagerAdapter(FragmentManager manager, List<Fragment> mFragmentList, List<String> mFragmentTitleList) {
        super(manager);
        this.mFragmentList = mFragmentList;
        this.mFragmentTitleList = mFragmentTitleList;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }
}
