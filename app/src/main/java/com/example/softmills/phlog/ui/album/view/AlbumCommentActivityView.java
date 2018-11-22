package com.example.softmills.phlog.ui.album.view;

import com.example.softmills.phlog.ui.album.model.AlbumImgCommentData;

/**
 * Created by abdalla_maged on 11/6/2018.
 */
public interface AlbumCommentActivityView {
    void viewAddCommentProgress(Boolean state);
    void viewPhotoComment(AlbumImgCommentData albumImgCommentData);
}
