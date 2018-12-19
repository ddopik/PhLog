package com.example.softmills.phlog.ui.social.presenter;

public interface SocialFragmentPresenter {

    void getSocialData();
    void followUser(String userId);
    void followSocialCampaign(String id);
    void followSocialBrand(String id);
    void unFollowSocialBrand(String id);
 }
