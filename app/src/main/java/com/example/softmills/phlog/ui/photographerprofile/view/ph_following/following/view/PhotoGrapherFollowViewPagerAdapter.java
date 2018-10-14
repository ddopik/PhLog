package com.example.softmills.phlog.ui.photographerprofile.view.ph_following.following.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;

import java.util.List;

/**
 * Created by abdalla_maged on 10/14/2018.
 */
public class PhotoGrapherFollowViewPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> mFragmentList;
    private List<Integer> mFragmentTitleList;
    private Context context;

    public PhotoGrapherFollowViewPagerAdapter(Context context,FragmentManager manager, List<Fragment> mFragmentList, List<Integer> mFragmentTitleList) {
        super(manager);
        this.mFragmentList = mFragmentList;
        this.mFragmentTitleList = mFragmentTitleList;
        this.context=context;
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
        // Generate title based on item position
        // return tabTitles[position];
        Drawable image = context.getResources().getDrawable(mFragmentTitleList.get(position));
        image.setBounds(0, 0, image.getIntrinsicWidth(), image.getIntrinsicHeight());
        SpannableString sb = new SpannableString(" ");
        ImageSpan imageSpan = new ImageSpan(image, ImageSpan.ALIGN_BOTTOM);
        sb.setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return sb;
    }
}

