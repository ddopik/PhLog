package com.example.softmills.phlog.ui.album.view;

import com.example.softmills.phlog.ui.album.model.AlbumImg;

import java.util.List;

/**
 * Created by abdalla_maged on 11/5/2018.
 */
public interface AllAlbumImgActivityView  {
    void viewAlbumImageList(List<AlbumImg> albumImgList);
    void viewAlbumImageListProgress(boolean state);
}
