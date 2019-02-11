package com.example.softmills.phlog.ui.social.view;

import com.example.softmills.phlog.base.commonmodel.Campaign;
import com.example.softmills.phlog.base.commonmodel.Photographer;
import com.example.softmills.phlog.ui.social.model.SocialData;

import java.util.List;

public interface SocialFragmentView {
    void viewSocialData(List<SocialData> socialDataList);
    void viewSocialDataProgress(boolean state);
    void showMessage(String msg);
}
