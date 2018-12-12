package com.example.softmills.phlog.ui.album.view;

import com.example.softmills.phlog.base.commonmodel.Comment;

import java.util.List;

/**
 * Created by abdalla_maged on 11/6/2018.
 */
public interface ImageCommentActivityView {
    void viewAddCommentProgress(Boolean state);
    void viewPhotoComment(List<Comment> commentList);
    void viewMessage(String msg);
}
