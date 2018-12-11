package com.example.softmills.phlog.ui.album.view;

import com.example.softmills.phlog.base.commonmodel.BaseImage;

import java.util.List;

/**
 * Created by abdalla_maged on 11/5/2018.
 */
public interface AllAlbumImgActivityView  {
    void viewAlbumImageList(List<BaseImage> albumImgList);
    void viewAlbumImageListProgress(boolean state);
    void showMessage(String msg);
}
