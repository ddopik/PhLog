package com.example.softmills.phlog.ui.allphotos.presenter;

/**
 * Created by abdalla_maged On Dec,2018
 */
public interface AllPhotographerPhotosPresenter {
    void getPhotographerPhotos(int Page);
    void uploadCampaignExistingPhoto(String campaignId, String imageId);
}
