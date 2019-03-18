package com.example.softmills.phlog.ui.search.view;

import android.widget.EditText;
import android.widget.TextView;

import com.example.softmills.phlog.base.widgets.CustomTextView;

/**
 * Created by abdalla_maged On Dec,2018
 */

/**
 *  //this interface gets the query if exists through other taps
 * **/
public interface OnSearchTabSelected {
    EditText getSearchView();
    CustomTextView getSearchResultCount();
}
