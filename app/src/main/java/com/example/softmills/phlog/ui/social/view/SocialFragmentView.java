package com.example.softmills.phlog.ui.social.view;

import com.example.softmills.phlog.ui.social.model.SocialData;

import java.util.List;

public interface SocialFragmentView {
    void viewSocialData(List<SocialData> socialDataList);

    void viewSocialDataProgress(boolean state);

    void setNextPageUrl(String page);

    void showMessage(String msg);

    void loadMore(boolean loadMore);

}
