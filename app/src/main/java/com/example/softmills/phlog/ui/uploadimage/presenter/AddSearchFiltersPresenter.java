package com.example.softmills.phlog.ui.uploadimage.presenter;

import android.content.Context;

import com.example.softmills.phlog.ui.search.view.album.model.Filter;
import com.example.softmills.phlog.ui.uploadimage.view.AddSearchFiltersActivity;
import com.example.softmills.phlog.ui.uploadimage.view.AddSearchFiltersView;

import java.util.List;

import io.reactivex.functions.Consumer;

public interface AddSearchFiltersPresenter {

    void setView(AddSearchFiltersView view);

    void getSearchFilters(Context context, Consumer<List<Filter>> listConsumer);
}
