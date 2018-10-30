package com.example.softmills.phlog.ui.search.view.album.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.softmills.phlog.R;
import com.example.softmills.phlog.base.BaseFragment;
import com.example.softmills.phlog.ui.search.view.album.model.SearchFilter;
import com.example.softmills.phlog.ui.search.view.album.presenter.AlbumSearchFragmentImpl;
import com.example.softmills.phlog.ui.search.view.album.presenter.AlbumSearchPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abdalla_maged on 10/29/2018.
 */
public class AlbumSearchFragment extends BaseFragment implements AlbumSearchFragmentView {

    private View mainView;
    private ExpandableListAdapter expandableListAdapter;
    private ExpandableListView filterExpListView;

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

        filterExpListView = mainView.findViewById(R.id.filters_expand);


        expandableListAdapter = new ExpandableListAdapter(getActivity(), searchFilterList);

        filterExpListView.setAdapter(expandableListAdapter);
        initListener();

        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int width = metrics.widthPixels;
        filterExpListView.setIndicatorBoundsRelative(width - GetPixelFromDips(50), width - GetPixelFromDips(10));
        filterExpListView.setIndicatorBoundsRelative(width - GetPixelFromDips(50), width - GetPixelFromDips(10));

        albumSearchPresenter.getSearchFilters();
    }

    private void initListener() {

        filterExpListView.setOnChildClickListener((parent, v, groupPosition, childPosition, id) -> {

            TextView txtListChild = v.findViewById(R.id.filter_item_val);
            showToast("child is" + txtListChild.getText());
            expandableListAdapter.notify_itemChild=groupPosition;
            expandableListAdapter.notify_itemHead=childPosition;
//                txtListChild.setCompoundDrawables(AlbumSearchFragment.this.getResources().getDrawable(R.drawable.ic_radio_button_checked), null, null, null);
            expandableListAdapter.notifyDataSetChanged();
            return true;
        });
//        expandableListAdapter.onChildViewListener = (radioButton, searchFilter, filterOption, groupPosition, childPosition) -> {
//            showToast("child is" + filterOption);
//
//            filterExpListView.getExpandableListAdapter().getChild(groupPosition, childPosition);
//
//
////            radioButton.setOnClickListener(v->{
////
////                if(  ((RadioButton) v).isSelected()){
////                    radioButton.setSelected(false);
////                }
////                else{
////                    radioButton.setSelected(true);
////                }
////
////            });
//
////            ToggleButton toggleButton = filterExpListView.getChildAt(childPosition).findViewById(R.id.filter_item_btn);
////            toggleButton.setBackground(getResources().getDrawable(R.drawable.ic_radio_button_checked));
//        };
//        filterExpListView.setOnChildClickListener((parent, v, groupPosition, childPosition, id) -> {
//            TextView s = parent.getChildAt(childPosition).findViewById(R.id.filter_item_val);
//            ToggleButton toggleButton = parent.getChildAt(childPosition).findViewById(R.id.filter_item_btn);
//            toggleButton.setBackground(getResources().getDrawable(R.drawable.ic_radio_button_checked));
//            String str = s.getText().toString();
//            showToast("child is" + str);
//            return true;
//        });
//        // Listview Group click listener

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
}
