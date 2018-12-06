package com.example.softmills.phlog.ui.userprofile.view;

import com.example.softmills.phlog.ui.userprofile.model.UserPhoto;

import java.util.List;

public interface UserProfileActivityView {

    void viewUserProfileLevel(String userLevel); //
    void viewUserProfileRating(int userRating); //
    void viewUserProfileProfileImg(String userImg); //
    void viewUserProfileFullName(String fullName); //
    void viewUserProfileUserName(String userName); //
    void viewUserProfilePhotosCount(int photosCount);//
    void viewUserProfileFollowersCount(int followersCount); //
    void viewUserProfileFollowingCount(int followingCount); //
    void viewUserPhotos(List<UserPhoto> userPhotoList);
    void viewUserPhotosProgress(boolean state);
    void showMessage(String msg);
}
