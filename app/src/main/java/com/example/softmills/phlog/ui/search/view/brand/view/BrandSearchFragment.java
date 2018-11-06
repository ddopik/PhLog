package com.example.softmills.phlog.ui.search.view.brand.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.softmills.phlog.R;
import com.example.softmills.phlog.base.widgets.PagingController;
import com.example.softmills.phlog.base.BaseFragment;
import com.example.softmills.phlog.base.widgets.CustomRecyclerView;
import com.example.softmills.phlog.ui.search.view.brand.model.BrandSearch;
import com.example.softmills.phlog.ui.search.view.brand.presenter.BrandSearchFragmentPresenter;
import com.example.softmills.phlog.ui.search.view.brand.presenter.BrandSearchFragmentPresenterImpl;
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
 * Created by abdalla_maged on 10/31/2018.
 */
public class BrandSearchFragment extends BaseFragment implements BrandSearchFragmentView {

    private String TAG = BrandSearchFragment.class.getSimpleName();
    private View mainView;
    private EditText brandSearch;
    private CustomRecyclerView searchBrandRv;
    private ProgressBar searchBrandProgress;
    private BrandSearchAdapter brandSearchAdapter;
    private List<BrandSearch> brandSearchList = new ArrayList<>();
    private BrandSearchFragmentPresenter brandSearchFragmentPresenter;
    private PagingController pagingController;
    private CompositeDisposable disposable = new CompositeDisposable();

    private OnSearchBrand onSearchBrand;

    public static BrandSearchFragment getInstance() {
        BrandSearchFragment brandSearchFragment = new BrandSearchFragment();
        return brandSearchFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.fragment_brand_search, container, false);
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

        if(brandSearch.getText().toString().length() >0)
            brandSearchFragmentPresenter.getSearchBrand(brandSearch.getText().toString().trim(), 0);
    }



    @Override
    protected void initPresenter() {
        brandSearchFragmentPresenter = new BrandSearchFragmentPresenterImpl(getContext(), this);
    }

    @Override
    protected void initViews() {

//        brandSearch = mainView.findViewById(R.id.search_brand);
        brandSearch = onSearchBrand.getSearchView();
        searchBrandRv = mainView.findViewById(R.id.search_brand_rv);
        searchBrandProgress = mainView.findViewById(R.id.search_brand_progress_bar);

        brandSearchAdapter = new BrandSearchAdapter(getContext(), brandSearchList);
        searchBrandRv.setAdapter(brandSearchAdapter);


    }

    private void initListener() {


        disposable.add(

                RxTextView.textChangeEvents(brandSearch)
                        .skipInitialValue()
                        .debounce(300, TimeUnit.MILLISECONDS)
                        .distinctUntilChanged()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(searchQuery()));

        pagingController = new PagingController(searchBrandRv) {
            @Override
            public void getPagingControllerCallBack(int page) {
                if (brandSearch.getText().length() == 0) {
                    brandSearchFragmentPresenter.getSearchBrand(brandSearch.getText().toString().trim(), page - 1);
                } else {
                    brandSearchFragmentPresenter.getSearchBrand(brandSearch.getText().toString().trim(), page - 1);
                }

            }
        };
    }

    private DisposableObserver<TextViewTextChangeEvent> searchQuery() {
        return new DisposableObserver<TextViewTextChangeEvent>() {
            @Override
            public void onNext(TextViewTextChangeEvent textViewTextChangeEvent) {
                brandSearchList.clear();
                // user cleared search get default data
                if (brandSearch.getText().length() == 0) {
                    brandSearchList.clear();
                    brandSearchFragmentPresenter.getSearchBrand(brandSearch.getText().toString().trim(), 0);
                } else {
                    // user is searching clear default value and get new search List
                    brandSearchList.clear();
                    brandSearchFragmentPresenter.getSearchBrand(brandSearch.getText().toString().trim(), 0);
                }

                Log.e(TAG, "search string: " + brandSearch.getText().toString());

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
    public void viewBrandSearchItems(List<BrandSearch> brandSearchList) {
        this.brandSearchList.addAll(brandSearchList);
        brandSearchAdapter.notifyDataSetChanged();
    }

    @Override
    public void viewBrandSearchProgress(boolean state) {
        if (state) {
            searchBrandProgress.setVisibility(View.VISIBLE);
        } else {
            searchBrandProgress.setVisibility(View.GONE);
        }

    }

    @Override
    public void showMessage(String msg) {
        showToast(msg);
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
