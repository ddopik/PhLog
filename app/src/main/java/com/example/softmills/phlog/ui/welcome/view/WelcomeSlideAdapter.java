package com.example.softmills.phlog.ui.welcome.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.softmills.phlog.R;
import com.example.softmills.phlog.Utiltes.GlideApp;

import java.util.List;

public class WelcomeSlideAdapter extends PagerAdapter {

    private List<String> urlList;
    private LayoutInflater inflater;
    private Context context;


    public WelcomeSlideAdapter(Context context,List<String> urlList) {
        this.context = context;
        this.urlList =urlList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return urlList.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup view, int position) {
        View imageLayout = inflater.inflate(R.layout.slidingimages_layout, view, false);

        assert imageLayout != null; // Exception is thrown if error occurs
        final ImageView imageView = imageLayout
                .findViewById(R.id.slide_img);


//        imageView.setImageResource(urlList.get(position));


        GlideApp.with(context)
                .load(urlList.get(position))
                .centerCrop()
//                .override(600, 200) //setImageDimension
                .placeholder(R.drawable.splash_screen_background)
                .into(imageView);
        view.addView(imageLayout, 0);
        return imageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }



}
