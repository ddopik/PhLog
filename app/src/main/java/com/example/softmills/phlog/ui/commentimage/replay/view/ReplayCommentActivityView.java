package com.example.softmills.phlog.ui.commentimage.replay.view;


import com.example.softmills.phlog.base.commonmodel.MentionedUser;
import com.example.softmills.phlog.ui.commentimage.model.ImageCommentsData;
import com.example.softmills.phlog.ui.commentimage.model.SubmitImageCommentData;

import java.util.List;

public interface ReplayCommentActivityView {
    void viewReplies(ImageCommentsData imageCommentsData);

    void viewRepliesProgress(boolean state);

    void viewMessage(String msg);
    void setNextPageUrl(String page);

    void onCommentReplied(SubmitImageCommentData submitImageCommentData);

    void viewMentionedUsers(List<MentionedUser> mentionedUserList);

}
