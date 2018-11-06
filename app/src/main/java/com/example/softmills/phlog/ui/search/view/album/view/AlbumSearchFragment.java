package com.example.softmills.phlog.ui.search.view.album.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ProgressBar;

import com.example.softmills.phlog.R;
import com.example.softmills.phlog.base.BaseFragment;
import com.example.softmills.phlog.base.widgets.CustomRecyclerView;
import com.example.softmills.phlog.ui.search.view.SearchActivity;
import com.example.softmills.phlog.ui.search.view.album.model.AlbumSearch;
import com.example.softmills.phlog.ui.search.view.album.model.FilterOption;
import com.example.softmills.phlog.ui.search.view.album.model.SearchFilter;
import com.example.softmills.phlog.ui.search.view.album.presenter.AlbumSearchFragmentImpl;
import com.example.softmills.phlog.ui.search.view.album.presenter.AlbumSearchPresenter;
import com.jakewharton.rxbinding3.widget.TextViewTextChangeEvent;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;

/**
 * Created by abdalla_maged on 10/29/2018.
 */
public class AlbumSearchFragment extends BaseFragment implements AlbumSearchFragmentView, SearchActivity.OnFilterClicked {

    private String TAG = AlbumSearchFragment.class.getSimpleName();
    private View mainView;
    private EditText albumSearch;
    private ExpandableListAdapter expandableListAdapter;
    private CustomRecyclerView albumSearchRv;
    private ExpandableListView filterExpListView;
    private ProgressBar progressBar;
    private AlbumSearchPresenter albumSearchPresenter;
    private List<SearchFilter> searchFilterList = new ArrayList<>();
    private DisplayMetrics metrics = new DisplayMetrics();
    private List<AlbumSearch> albumSearchList = new ArrayList<AlbumSearch>();
    private AlbumSearchAdapter albumSearchAdapter;
    private CompositeDisposable disposable = new CompositeDisposable();

    private OnSearchBrand onSearchBrand;

    public static AlbumSearchFragment getInstance() {
        AlbumSearchFragment albumSearchFragment = new AlbumSearchFragment();
        return albumSearchFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.fragment_album_search, container, false);

        return mainView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (onSearchBrand.getSearchView() != null) {
            initPresenter();
            initViews();
            initListener();

        }

    }


    @Override
    protected void initPresenter() {
        albumSearchPresenter = new AlbumSearchFragmentImpl(getContext(), this);
    }

    @Override
    protected void initViews() {

        albumSearch = onSearchBrand.getSearchView();
        progressBar = mainView.findViewById(R.id.album_search_filter_progress);
         filterExpListView = mainView.findViewById(R.id.filters_expand);
        albumSearchRv = mainView.findViewById(R.id.album_search_rv);
        expandableListAdapter = new ExpandableListAdapter(getActivity(), searchFilterList);
        albumSearchAdapter = new AlbumSearchAdapter(albumSearchList);
        filterExpListView.setAdapter(expandableListAdapter);
        initListener();


    //////// setting ExpandableList indicator to right
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int width = metrics.widthPixels;
        filterExpListView.setIndicatorBoundsRelative(width - GetPixelFromDips(50), width - GetPixelFromDips(10));
        filterExpListView.setIndicatorBoundsRelative(width - GetPixelFromDips(50), width - GetPixelFromDips(10));
    ///////////

        albumSearchRv.setAdapter(albumSearchAdapter);
        if (albumSearch.getText().toString().length() > 0)
            albumSearchPresenter.getAlbumSearch(albumSearch.getText().toString().trim(), 0);
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


    private DisposableObserver<TextViewTextChangeEvent> searchQuery() {
        return new DisposableObserver<TextViewTextChangeEvent>() {
            @Override
            public void onNext(TextViewTextChangeEvent textViewTextChangeEvent) {
                albumSearchList.clear();
                // user cleared search get default data
                if (albumSearch.getText().length() == 0) {
                    albumSearchList.clear();
                    albumSearchPresenter.getAlbumSearch(albumSearch.getText().toString().trim(), 0);
                } else {
                    // user is searching clear default value and get new search List
                    albumSearchList.clear();
                    albumSearchPresenter.getAlbumSearch(albumSearch.getText().toString().trim(), 0);
                }

                Log.e(TAG, "search string: " + albumSearch.getText().toString());

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
    public void viewSearchAlbum(List<AlbumSearch> albumSearchList) {
        filterExpListView.setVisibility(View.GONE);
        albumSearchRv.setVisibility(View.VISIBLE);
        this.albumSearchList.addAll(albumSearchList);
        albumSearchAdapter.notifyDataSetChanged();
    }

    @Override
    public void showToast(String msg) {
        super.showToast(msg);
    }


    private int GetPixelFromDips(float pixels) {
        // Get the screen's density scale
        final float scale = getResources().getDisplayMetrics().density;
        // Convert the dps to pixels, based on density scale
        return (int) (pixels * scale + 0.5f);
    }

    @Override
    public void viewSearchFilters(List<SearchFilter> searchFilterList) {
        filterExpListView.setVisibility(View.VISIBLE);
        albumSearchRv.setVisibility(View.GONE);
        this.searchFilterList.clear();
        this.searchFilterList.addAll(searchFilterList);
        expandableListAdapter.notifyDataSetChanged();


    }

    @Override
    public void showMessage(String msg) {
        showToast(msg);
    }

    @Override
    public void showFilterSearchProgress(boolean state) {
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        disposable.clear();
    }

    public void setBrandSearchView(OnSearchBrand onSearchBrand) {
        this.onSearchBrand = onSearchBrand;
    }

    public interface OnSearchBrand {
        EditText getSearchView();
    }

}
