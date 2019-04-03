package com.example.softmills.phlog.ui.userprofile.view;

import com.example.softmills.phlog.base.commonmodel.BaseImage;
import com.example.softmills.phlog.base.commonmodel.Photographer;

import java.util.List;

public interface UserProfileActivityView {

    void viewUserData(Photographer photographer);
//    void viewUserProfileLevel(String userLevel); //
//    void viewUserProfileRating(int userRating); //
//    void viewUserProfileProfileImg(String userImg); //
//    void viewUserProfileFullName(String fullName); //
//    void viewUserProfileUserName(String userName); //
//    void viewUserProfilePhotosCount(int photosCount);//
//    void viewUserProfileFollowersCount(int followersCount); //
//    void viewUserProfileFollowingCount(int followingCount); //
    void setNextPageUrl(String page);
    void viewUserPhotos(List<BaseImage> userPhotoList);
    void viewUserFollowingState(boolean state);
    void viewUserPhotosProgress(boolean state);
    void showMessage(String msg);
}
