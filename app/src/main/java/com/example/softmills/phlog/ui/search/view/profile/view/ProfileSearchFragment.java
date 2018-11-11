package com.example.softmills.phlog.ui.search.view.profile.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.softmills.phlog.R;
import com.example.softmills.phlog.Utiltes.Constants;
import com.example.softmills.phlog.base.BaseFragment;
import com.example.softmills.phlog.base.widgets.CustomRecyclerView;
import com.example.softmills.phlog.base.widgets.PagingController;
import com.example.softmills.phlog.ui.search.view.profile.model.ProfileSearch;
import com.example.softmills.phlog.ui.search.view.profile.presenter.ProdileSearchPresenter;
import com.example.softmills.phlog.ui.search.view.profile.presenter.ProdileSearchPresenterImpl;
import com.example.softmills.phlog.ui.userprofile.view.UserProfileActivity;
import com.jakewharton.rxbinding3.widget.RxTextView;
import com.jakewharton.rxbinding3.widget.TextViewTextChangeEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * Created by abdalla_maged on 11/1/2018.
 */
public class ProfileSearchFragment extends BaseFragment implements ProfileSearchFragmentView {

    private String TAG=ProfileSearchFragment.class.getSimpleName();
    private View mainView;
    private EditText profileSearch;
    private ProgressBar profileSearchProgress;
    private CustomRecyclerView profileSearchRv;
    private OnSearchProfile onSearchProfile;
    private PagingController pagingController;
    private ProfileSearchAdapter profileSearchAdapter;
    private List<ProfileSearch> profileSearchList = new ArrayList<>();

    private ProdileSearchPresenter prodileSearchPresenter;
    private CompositeDisposable disposable = new CompositeDisposable();

    public static ProfileSearchFragment getInstance() {
        ProfileSearchFragment profileSearchFragment = new ProfileSearchFragment();
        return profileSearchFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.fragment_profile_search, container, false);

        return mainView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        if (onSearchProfile !=null){

            initPresenter();
            initViews();
            initListener();

            if (profileSearch.getText().toString().length() > 0) {
                profileSearchList.clear();
                prodileSearchPresenter.getProfileSearchList(onSearchProfile.getSearchView().getText().toString().trim(),0);
            }

        }

    }


    @Override
    protected void initViews() {

        profileSearchRv = mainView.findViewById(R.id.profile_search_rv);
        profileSearchProgress = mainView.findViewById(R.id.profile_search_progress_bar);
        profileSearch = onSearchProfile.getSearchView();
        profileSearchAdapter = new ProfileSearchAdapter(getContext(), profileSearchList);
        profileSearchRv.setAdapter(profileSearchAdapter);

    }

    @Override
    protected void initPresenter() {
        prodileSearchPresenter = new ProdileSearchPresenterImpl(getContext(), this);
    }


    private void initListener() {



        disposable.add(

                RxTextView.textChangeEvents(profileSearch)
                        .skipInitialValue()
                        .debounce(Constants.QUERY_SEARCH_TIME_OUT, TimeUnit.MILLISECONDS)
                        .distinctUntilChanged()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(searchQuery()));

        pagingController = new PagingController(profileSearchRv) {
            @Override
            public void getPagingControllerCallBack(int page) {
                if (profileSearch.getText().length() > 0) {
                    prodileSearchPresenter.getProfileSearchList(profileSearch.getText().toString().trim(), page - 1);

                }

            }
        };


        profileSearchAdapter.profileAdapterListener = profileSearch -> {
            Intent intent = new Intent(getActivity(), UserProfileActivity.class);
            intent.putExtra(UserProfileActivity.USER_ID,profileSearch.userNameId);
            startActivity(intent);
        };
    }
    private DisposableObserver<TextViewTextChangeEvent> searchQuery() {
        return new DisposableObserver<TextViewTextChangeEvent>() {
            @Override
            public void onNext(TextViewTextChangeEvent textViewTextChangeEvent) {
                // user cleared search get default data
                if (profileSearch.getText().length() == 0) {
                    profileSearchList.clear();
                    prodileSearchPresenter.getProfileSearchList(profileSearch.getText().toString().trim(), 0);
                } else {
                    // user is searching clear default value and get new search List
                    profileSearchList.clear();
                    prodileSearchPresenter.getProfileSearchList(profileSearch.getText().toString().trim(), 0);
                }
                Log.e(TAG, "search string: " + profileSearch.getText().toString());

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
    public void viewProfileSearchItems(List<ProfileSearch> profileSearchList) {
        this.profileSearchList.addAll(profileSearchList);
        profileSearchAdapter.notifyDataSetChanged();
        hideSoftKeyBoard();
    }

    @Override
    public void viewProfileSearchProgress(Boolean state) {
        if (state) {
            profileSearchProgress.setVisibility(View.VISIBLE);
        } else {
            profileSearchProgress.setVisibility(View.GONE);
        }

    }

    @Override
    public void showMessage(String msg) {
        showToast(msg);
    }


    private void hideSoftKeyBoard() {
        profileSearch.clearFocus();
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
        if(imm.isAcceptingText()) { // verify if the soft keyboard is open
            imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
        }
    }
    public void setOnSearchProfile(OnSearchProfile onSearchProfile) {
        this.onSearchProfile = onSearchProfile;
    }

    public interface OnSearchProfile {
        EditText getSearchView();
    }
}
