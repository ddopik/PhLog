package com.example.softmills.phlog.ui.campaigns.inner.ui;

import com.example.softmills.phlog.base.commonmodel.BaseImage;

import java.util.List;

/**
 * Created by abdalla_maged on 10/8/2018.
 */
public interface CampaignInnerPhotosFragmentView {

    void getInnerCampaignPhotos(List<BaseImage> campaignInnerPhotoList);
    void viewCampaignInnerPhotosProgress(boolean state);
}
