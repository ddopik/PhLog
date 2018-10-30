package com.example.softmills.phlog.ui.search.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.softmills.phlog.R;
import com.example.softmills.phlog.base.BaseActivity;
import com.example.softmills.phlog.ui.search.view.album.view.AlbumSearchFragment;

/**
 * Created by abdalla_maged on 10/29/2018.
 */
public class SearchActivity extends BaseActivity {

    private TextView searchView;
    private TextView brandTab, profileTab, albumTab;
    private FrameLayout searchContainer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initPresenter();
        initView();
        initListener();
    }


    @Override
    public void initView() {

        searchView = findViewById(R.id.search_view);
        brandTab = findViewById(R.id.tab_brand);
        profileTab = findViewById(R.id.tab_profile);
        albumTab = findViewById(R.id.tab_album);
        searchContainer = findViewById(R.id.search_container);

    }

    @Override
    public void initPresenter() {

    }

    private void initListener(){
        brandTab.setOnClickListener((view) -> {
            setTapSelected(R.id.tab_brand);
        });
        profileTab.setOnClickListener((view) -> {
            setTapSelected(R.id.tab_profile);
        });

        albumTab.setOnClickListener((view) -> {
            setTapSelected(R.id.tab_album);
            addFragment(R.id.search_container, AlbumSearchFragment.getInstance(), AlbumSearchFragment.class.getSimpleName(), false);
        });
    }


    @Override
    public void showToast(String msg) {
        super.showToast(msg);
    }

    private void setTapSelected(int tabId) {
        brandTab.setTextColor(getResources().getColor(R.color.text_input_color));
        profileTab.setTextColor(getResources().getColor(R.color.text_input_color));
        albumTab.setTextColor(getResources().getColor(R.color.text_input_color));

        brandTab.setBackground(getResources().getDrawable(R.drawable.rounded_frame_orange));
        profileTab.setBackground(getResources().getDrawable(R.drawable.rounded_frame_orange));
        albumTab.setBackground(getResources().getDrawable(R.drawable.rounded_frame_orange));


        switch (tabId) {
            case R.id.tab_brand:
                brandTab.setTextColor(getResources().getColor(R.color.white));
                brandTab.setBackground(getResources().getDrawable(R.drawable.rounded_frame_orange_fill));
                break;

            case R.id.tab_profile:
                profileTab.setTextColor(getResources().getColor(R.color.white));
                profileTab.setBackground(getResources().getDrawable(R.drawable.rounded_frame_orange_fill));
                break;

            case R.id.tab_album:
                albumTab.setTextColor(getResources().getColor(R.color.white));
                albumTab.setBackground(getResources().getDrawable(R.drawable.rounded_frame_orange_fill));
                break;

        }

    }

}