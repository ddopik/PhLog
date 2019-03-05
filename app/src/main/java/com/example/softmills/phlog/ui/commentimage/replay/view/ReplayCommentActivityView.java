package com.example.softmills.phlog.ui.commentimage.replay.view;


import com.example.softmills.phlog.ui.commentimage.model.ImageCommentsData;
import com.example.softmills.phlog.ui.commentimage.model.SubmitImageCommentData;

public interface ReplayCommentActivityView {
    void viewReplies(ImageCommentsData imageCommentsData);

    void viewRepliesProgress(boolean state);

    void viewMessage(String msg);


    void onCommentReplied(SubmitImageCommentData submitImageCommentData);


}
