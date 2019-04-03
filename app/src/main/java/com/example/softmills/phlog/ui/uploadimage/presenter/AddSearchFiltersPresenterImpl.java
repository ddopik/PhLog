package com.example.softmills.phlog.ui.uploadimage.presenter;

import android.content.Context;

import com.example.softmills.phlog.Utiltes.ErrorUtils;
import com.example.softmills.phlog.network.BaseNetworkApi;
import com.example.softmills.phlog.ui.search.view.album.model.Filter;
import com.example.softmills.phlog.ui.uploadimage.view.AddSearchFiltersActivity;
import com.example.softmills.phlog.ui.uploadimage.view.AddSearchFiltersView;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class AddSearchFiltersPresenterImpl implements AddSearchFiltersPresenter {

    public final static String TAG = AddSearchFiltersPresenterImpl.class.getSimpleName();
    private AddSearchFiltersView view;
    private CompositeDisposable disposables = new CompositeDisposable();

    @Override
    public void setView(AddSearchFiltersView view) {
        this.view = view;
    }

    @Override
    public void getSearchFilters(Context context, Consumer<List<Filter>> listConsumer) {

        view.showProgress(true);
        Disposable disposable = BaseNetworkApi.getFilters()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> view.showProgress(false))
                .subscribe(searchAlbumResponse -> {
                    listConsumer.accept(searchAlbumResponse.data);
                }, throwable -> {
                    ErrorUtils.Companion.setError(context, TAG, throwable);
                });
        disposables.add(disposable);
    }
}
