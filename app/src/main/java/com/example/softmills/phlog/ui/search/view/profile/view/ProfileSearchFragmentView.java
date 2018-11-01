package com.example.softmills.phlog.ui.search.view.profile.view;

import com.example.softmills.phlog.ui.search.view.profile.model.ProfileSearch;

import java.util.List;

/**
 * Created by abdalla_maged on 11/1/2018.
 */
public interface ProfileSearchFragmentView {

    void viewProfileSearchItems(List<ProfileSearch>profileSearchList);

    void viewProfileSearchProgress(Boolean state);

    void showMessage(String msg);

}
