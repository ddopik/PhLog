package com.example.softmills.phlog.ui.search.view.album.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ProgressBar;

import com.example.softmills.phlog.R;
import com.example.softmills.phlog.base.BaseFragment;
import com.example.softmills.phlog.ui.search.view.SearchActivity;
import com.example.softmills.phlog.ui.search.view.album.model.FilterOption;
import com.example.softmills.phlog.ui.search.view.album.model.SearchFilter;
import com.example.softmills.phlog.ui.search.view.album.presenter.AlbumSearchFragmentImpl;
import com.example.softmills.phlog.ui.search.view.album.presenter.AlbumSearchPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abdalla_maged on 10/29/2018.
 */
public class AlbumSearchFragment extends BaseFragment implements AlbumSearchFragmentView, SearchActivity.OnFilterClicked {

    private View mainView;
    private ExpandableListAdapter expandableListAdapter;
    private ProgressBar progressBar;
    private AlbumSearchPresenter albumSearchPresenter;

    private List<SearchFilter> searchFilterList = new ArrayList<>();

    private DisplayMetrics metrics = new DisplayMetrics();

    public static AlbumSearchFragment getInstance() {
        AlbumSearchFragment albumSearchFragment = new AlbumSearchFragment();
        return albumSearchFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.fragment_album_search, container, false);
        initPresenter();
        initViews();

        return mainView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    @Override
    protected void initPresenter() {

        albumSearchPresenter = new AlbumSearchFragmentImpl(getContext(), this);
    }

    @Override
    protected void initViews() {

        progressBar = mainView.findViewById(R.id.album_search_filter_progress);

        ExpandableListView filterExpListView = mainView.findViewById(R.id.filters_expand);


        expandableListAdapter = new ExpandableListAdapter(getActivity(), searchFilterList);

        filterExpListView.setAdapter(expandableListAdapter);
        initListener();


    //////// setting ExpandableList indicator to right
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int width = metrics.widthPixels;
        filterExpListView.setIndicatorBoundsRelative(width - GetPixelFromDips(50), width - GetPixelFromDips(10));
        filterExpListView.setIndicatorBoundsRelative(width - GetPixelFromDips(50), width - GetPixelFromDips(10));
    ///////////

//        albumSearchPresenter.getSearchFilters();
    }

    private void initListener() {


        expandableListAdapter.onChildViewListener = filterOption -> {
            showToast(filterOption.name);
            for (int i = 0; i < searchFilterList.size(); i++) {
                for (int x = 0; x < searchFilterList.get(i).options.size(); x++) {
                    FilterOption currFilterOption = searchFilterList.get(i).options.get(x);
                    if (currFilterOption.name.equals(filterOption.name)) {
                        if (currFilterOption.isSelected) {
                            searchFilterList.get(i).options.get(x).isSelected = false;
                        } else {
                            searchFilterList.get(i).options.get(x).isSelected = true;
                        }
                        expandableListAdapter.notifyDataSetChanged();
                        return;
                    }
                }
            }
        };


    }


    @Override
    public void showToast(String msg) {
        super.showToast(msg);
    }


    public int GetPixelFromDips(float pixels) {
        // Get the screen's density scale
        final float scale = getResources().getDisplayMetrics().density;
        // Convert the dps to pixels, based on density scale
        return (int) (pixels * scale + 0.5f);
    }

    @Override
    public void viewSearchFilters(List<SearchFilter> searchFilterList) {
        this.searchFilterList.clear();
        this.searchFilterList.addAll(searchFilterList);
        expandableListAdapter.notifyDataSetChanged();


//        List<FilterOption> filterOptionsObjList = new ArrayList<FilterOption>();
//        for (int i = 0; i < searchFilterList.size(); i++) {
//            for (int x=0;x<searchFilterList.get(i).options.size();x++){
//                FilterOption filterOption = new FilterOption();
//                filterOption.options = searchFilterList.get(i).options.get(i);
//                filterOptionsObjList.add
//            }
//
//
//
//        }



    }

    @Override
    public void showMessage(String msg) {
        showToast(msg);
    }

    @Override
    public void showAlbumSearchProgress(boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onFilterIconClicked() {
        albumSearchPresenter.getSearchFilters();
    }
}
