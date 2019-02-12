package com.example.softmills.phlog.ui.brand.view;

import com.example.softmills.phlog.base.commonmodel.Business;

/**
 * Created by abdalla_maged on 11/12/2018.
 */
public interface BrandInnerActivityView {

    void viewInnerBrandData(Business brandInnerData);
    void viewInnerBrandProgressBar(Boolean state);
    void viwBrandFollowedState(Boolean state);
    void showMessage(String msg);
}
