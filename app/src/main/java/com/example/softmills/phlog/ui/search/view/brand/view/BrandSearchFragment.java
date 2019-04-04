package com.example.softmills.phlog.ui.search.view.brand.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.softmills.phlog.R;
import com.example.softmills.phlog.Utiltes.Constants;
import com.example.softmills.phlog.Utiltes.ErrorUtils;
import com.example.softmills.phlog.base.BaseFragment;
import com.example.softmills.phlog.base.commonmodel.Business;
import com.example.softmills.phlog.base.widgets.CustomRecyclerView;
import com.example.softmills.phlog.base.widgets.PagingController;
import com.example.softmills.phlog.ui.brand.view.BrandInnerActivity;
import com.example.softmills.phlog.ui.search.view.OnSearchTabSelected;
import com.example.softmills.phlog.ui.search.view.brand.model.BrandSearchData;
import com.example.softmills.phlog.ui.search.view.brand.presenter.BrandSearchFragmentPresenter;
import com.example.softmills.phlog.ui.search.view.brand.presenter.BrandSearchFragmentPresenterImpl;
import com.jakewharton.rxbinding3.widget.RxTextView;
import com.jakewharton.rxbinding3.widget.TextViewTextChangeEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * Created by abdalla_maged on 10/31/2018.
 */
public class BrandSearchFragment extends BaseFragment implements BrandSearchFragmentView {

    private final static int campain_inner_request_code = 873;
    public final static String BRAND = "brand";
    private String TAG = BrandSearchFragment.class.getSimpleName();
    private View mainView;
    private EditText brandSearch;
    private TextView searchResultCountView;
    private CustomRecyclerView searchBrandRv;
    private ProgressBar searchBrandProgress;
    private BrandSearchAdapter brandSearchAdapter;
    private List<Business> brandSearchList = new ArrayList<>();
    private BrandSearchFragmentPresenter brandSearchFragmentPresenter;
    private PagingController pagingController;
    private String nextPageUrl = "1";
    private boolean isLoading;

    private CompositeDisposable disposables = new CompositeDisposable();
    private OnSearchTabSelected onSearchTabSelected;

    private ConstraintLayout promptView;
    private ImageView promptImage;
    private TextView promptText;

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

        if (onSearchTabSelected.getSearchView() != null) {
            initPresenter();
            initViews();
            initListener();

        }

        if (brandSearch.getText().toString().length() > 0) {
            promptView.setVisibility(View.GONE);
            brandSearchFragmentPresenter.getSearchBrand(brandSearch.getText().toString().trim(), 0);
        }
    }


    @Override
    protected void initPresenter() {
        brandSearchFragmentPresenter = new BrandSearchFragmentPresenterImpl(getContext(), this);
    }

    @Override
    protected void initViews() {

//        brandSearch = mainView.findViewById(R.id.search_brand);
        brandSearch = onSearchTabSelected.getSearchView();
        searchResultCountView = onSearchTabSelected.getSearchResultCountView();
        searchBrandRv = mainView.findViewById(R.id.search_brand_rv);
        searchBrandProgress = mainView.findViewById(R.id.search_brand_progress_bar);
        brandSearchAdapter = new BrandSearchAdapter(getContext(), brandSearchList);
        searchBrandRv.setAdapter(brandSearchAdapter);

        promptView = mainView.findViewById(R.id.prompt_view);
        promptView.setBackgroundColor(getResources().getColor(R.color.black));
        promptImage = mainView.findViewById(R.id.prompt_image);
        promptImage.setBackgroundResource(R.drawable.ic_brand_search);
        promptText = mainView.findViewById(R.id.prompt_text);
        promptText.setText(R.string.type_something_brand);


        searchResultCountView.setVisibility(View.INVISIBLE);
    }

    private void initListener() {


        disposables.add(

                RxTextView.textChangeEvents(brandSearch)
                        .skipInitialValue()
                        .debounce(Constants.QUERY_SEARCH_TIME_OUT, TimeUnit.MILLISECONDS)
                        .distinctUntilChanged()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(searchQuery()));


        pagingController = new PagingController(searchBrandRv) {


            @Override
            protected void loadMoreItems() {
                brandSearchFragmentPresenter.getSearchBrand(brandSearch.getText().toString().trim(), Integer.parseInt(nextPageUrl));

            }

            @Override
            public boolean isLastPage() {

                if (nextPageUrl == null) {
                    return true;
                } else {
                    return false;
                }

            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }


        };


        brandSearchAdapter.brandAdapterListener = new BrandSearchAdapter.BrandAdapterListener() {
            @Override
            public void onBrandSelected(Business brandSearch) {
                Intent intent = new Intent(getActivity(), BrandInnerActivity.class);
                intent.putExtra(BrandInnerActivity.BRAND_ID, brandSearch.id);
                startActivityForResult(intent, campain_inner_request_code);
            }

            @Override
            public void onFollowBrand(Business business, int position) {
                Observable<Boolean> observable = null;
                if (business.isFollow) {
                    observable = brandSearchFragmentPresenter.unfollowBrand(business);
                } else {
                    observable = brandSearchFragmentPresenter.followBrand(business);
                }
                if (observable != null)
                    disposables.add(observable.subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .doFinally(() -> {
                                brandSearchAdapter.notifyItemChanged(position);
                            })
                            .subscribe(follow -> {
                                business.isFollow = follow;
                            }, throwable -> {
                                ErrorUtils.Companion.setError(getContext(), TAG, throwable);
                            }));
            }
        };
    }

    private DisposableObserver<TextViewTextChangeEvent> searchQuery() {
        return new DisposableObserver<TextViewTextChangeEvent>() {
            @Override
            public void onNext(TextViewTextChangeEvent textViewTextChangeEvent) {
                if (textViewTextChangeEvent.getCount() == 0) {
                    promptView.setVisibility(View.VISIBLE);
                    if (searchResultCountView != null) {
                        searchResultCountView.setVisibility(View.INVISIBLE);
                    }
                    promptText.setText(R.string.type_something_profile);
                    return;
                }
                // user cleared search get default data

                promptView.setVisibility(View.GONE);
                brandSearchList.clear();
                brandSearchFragmentPresenter.getSearchBrand(brandSearch.getText().toString().trim(), 0);
                brandSearchAdapter.notifyDataSetChanged();
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
    public void viewBrandSearchItems(BrandSearchData brandSearchData) {
        this.brandSearchList.addAll(brandSearchData.data);
        brandSearchAdapter.notifyDataSetChanged();
        searchResultCountView.setVisibility(View.VISIBLE);
        searchResultCountView.setTextColor(getResources().getColor(R.color.white));
        searchResultCountView.setText(new StringBuilder().append(brandSearchData.total).append(" ").append(getResources().getString(R.string.result)).toString());
//        hideSoftKeyBoard();


        if (this.brandSearchList.size() == 0) {
            promptView.setVisibility(View.VISIBLE);
            promptText.setText(R.string.could_not_find_brand);
        } else {
            promptView.setVisibility(View.GONE);
        }
    }

    @Override
    public void viewBrandSearchProgress(boolean state) {
        isLoading = state;

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
        disposables.clear();
    }

    public void setBrandSearchView(OnSearchTabSelected onSearchTabSelected) {
        this.onSearchTabSelected = onSearchTabSelected;
    }


    private void hideSoftKeyBoard() {
        brandSearch.clearFocus();
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
        if (imm.isAcceptingText()) { // verify if the soft keyboard is open
            imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
        }
    }

    @Override
    public void setNextPageUrl(String page) {
        this.nextPageUrl = page;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            Business business = data.getParcelableExtra(BRAND);
            if (business != null) {
                for (Business b : brandSearchList) {
                    if (b.id.equals(business.id)) {
                        b.isFollow = business.isFollow;
                        int pos = brandSearchList.indexOf(b);
                        brandSearchAdapter.notifyItemChanged(pos);
                        break;
                    }
                }
            }
        }
    }
}
