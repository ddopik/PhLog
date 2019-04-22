package com.example.softmills.phlog.ui.commentimage.replay.presenter;

import android.annotation.SuppressLint;
import android.content.Context;


import com.example.softmills.phlog.Utiltes.Constants;
import com.example.softmills.phlog.Utiltes.ErrorUtils;
import com.example.softmills.phlog.Utiltes.Utilities;
import com.example.softmills.phlog.base.commonmodel.Business;
import com.example.softmills.phlog.base.commonmodel.MentionedUser;
import com.example.softmills.phlog.base.commonmodel.Photographer;
import com.example.softmills.phlog.network.BaseNetworkApi;
import com.example.softmills.phlog.ui.commentimage.replay.view.ReplayCommentActivityView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ReplayCommentPresenterImpl implements ReplayCommentPresenter {


    private String TAG = ReplayCommentPresenterImpl.class.getSimpleName();
    private Context context;
    private ReplayCommentActivityView replayCommentActivityView;

    public ReplayCommentPresenterImpl(Context context, ReplayCommentActivityView replayCommentActivityView) {
        this.context = context;
        this.replayCommentActivityView = replayCommentActivityView;
    }

    @SuppressLint("CheckResult")
    @Override
    public void getReplies(int parentCommentId, int imageID,int page) {
        replayCommentActivityView.viewRepliesProgress(true);
        BaseNetworkApi.getCommentReplies(parentCommentId, imageID, String.valueOf(page))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(repliesResponse -> {
                    replayCommentActivityView.viewRepliesProgress(false);
                    replayCommentActivityView.viewReplies(repliesResponse.data);
                    if (repliesResponse.data.comments.nextPageUrl != null) {
                        replayCommentActivityView.setNextPageUrl(Utilities.getNextPageNumber(context, repliesResponse.data.comments.nextPageUrl));

                    } else {
                        replayCommentActivityView.setNextPageUrl(null);
                    }
                }, throwable -> {
                    replayCommentActivityView.viewRepliesProgress(false);
                    ErrorUtils.Companion.setError(context, TAG, throwable);

                });

    }

    @SuppressLint("CheckResult")
    @Override
    public void submitReplayComment(String imageId, String parentReplayId, String comment) {
        replayCommentActivityView.viewRepliesProgress(true);
        BaseNetworkApi.submitImageCommentReplay(imageId,  parentReplayId, comment)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(submitImageCommentResponse -> {
                    replayCommentActivityView.onCommentReplied(submitImageCommentResponse.data);
                    replayCommentActivityView.viewRepliesProgress(false);
                }, throwable -> {
                    ErrorUtils.Companion.setError(context, TAG, throwable);
                    replayCommentActivityView.viewRepliesProgress(false);
                });

    }

    @SuppressLint("CheckResult")
    @Override
    public void getMentionedUser(String key) {
        BaseNetworkApi.getSocialAutoComplete(key)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(socialAutoCompleteResponse -> {

                    List<MentionedUser> mentionedUserList = new ArrayList<>();
                    if (socialAutoCompleteResponse.data.photographers != null) {


                        for (Photographer photographer : socialAutoCompleteResponse.data.photographers) {
                            MentionedUser mentionedUser = new MentionedUser();

                            mentionedUser.mentionedUserId = photographer.id;
                            mentionedUser.mentionedUserName = photographer.fullName;
                            mentionedUser.mentionedImage = photographer.imageProfile;
                            mentionedUser.mentionedType = Constants.UserType.USER_TYPE_PHOTOGRAPHER;
                            mentionedUserList.add(mentionedUser);
                        }
                    }
                    if (socialAutoCompleteResponse.data.businesses != null) {
                        for (Business business : socialAutoCompleteResponse.data.businesses) {
                            MentionedUser mentionedUser = new MentionedUser();
                            mentionedUser.mentionedUserId = business.id;
                            mentionedUser.mentionedUserName = business.firstName + " " + business.lastName;
                            mentionedUser.mentionedImage = business.thumbnail;
                            mentionedUser.mentionedType = Constants.UserType.USER_TYPE_BUSINESS;
                            mentionedUserList.add(mentionedUser);
                        }
                    }
                    replayCommentActivityView.viewMentionedUsers(mentionedUserList);
                }, throwable -> {
                    ErrorUtils.Companion.setError(context, TAG, throwable);
                });
    }

}
