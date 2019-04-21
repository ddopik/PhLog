package com.example.softmills.phlog.ui.album.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.request.RequestOptions;
import com.example.softmills.phlog.R;
import com.example.softmills.phlog.Utiltes.Constants;
import com.example.softmills.phlog.Utiltes.GlideApp;
import com.example.softmills.phlog.Utiltes.PrefUtils;
import com.example.softmills.phlog.Utiltes.Utilities;
import com.example.softmills.phlog.base.commonmodel.BaseImage;
import com.example.softmills.phlog.base.commonmodel.Business;
import com.example.softmills.phlog.base.commonmodel.Comment;
import com.example.softmills.phlog.base.commonmodel.MentionedUser;
import com.example.softmills.phlog.base.commonmodel.Mentions;
import com.example.softmills.phlog.base.commonmodel.Photographer;
import com.example.softmills.phlog.base.commonmodel.Tag;
import com.example.softmills.phlog.base.widgets.CustomAutoCompleteTextView;
import com.example.softmills.phlog.base.widgets.CustomTextView;
import com.example.softmills.phlog.ui.album.presenter.CommentAdapterPresenter;
import com.example.softmills.phlog.ui.album.presenter.CommentAdapterPresenterImpl;
import com.example.softmills.phlog.ui.album.view.CommentsAdapterView;
import com.example.softmills.phlog.ui.commentimage.view.MentionsAutoCompleteAdapter;
import com.example.softmills.phlog.ui.userprofile.view.UserProfileActivity;
import com.jakewharton.rxbinding3.widget.RxTextView;
import com.jakewharton.rxbinding3.widget.TextViewTextChangeEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import static com.example.softmills.phlog.Utiltes.Constants.CommentListType.MAIN_COMMENT;
import static com.example.softmills.phlog.Utiltes.Constants.CommentListType.REPLAY_ON_COMMENT;
import static com.example.softmills.phlog.Utiltes.Constants.CommentListType.VIEW_REPLIES;

/**
 * Created by abdalla_maged On Nov,2018
 */
public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.CommentViewHolder>     {

    private String TAG = CommentsAdapter.class.getSimpleName();
    private Constants.CommentListType commentListType;
    private Context context;
    private List<Comment> commentList;
    private Mentions mentions;
    private LayoutInflater layoutInflater;
    private List<MentionedUser> mentionedUserList = new ArrayList<>();
     private CommentAdapterPresenter commentAdapterPresenter;
     private BaseImage previewImage;

    private boolean shouldShowChooseWinnerButton;
    public CommentAdapterAction commentAdapterAction;
    private int HEAD = 0;
    private int COMMENT = 1;
     private int REPLY_COMMENT = 2;

    private final String USER_MENTION_IDENTIFIER = "%";
    private DisposableObserver<TextViewTextChangeEvent> searchQuery;


    public CommentsAdapter(BaseImage previewImage, List<Comment> commentList, Mentions mentions, Constants.CommentListType commentListType) {
        this.commentList = commentList;
        this.previewImage = previewImage;
        this.mentions = mentions;
        this.commentListType = commentListType;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        this.context = viewGroup.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        if (i == HEAD) {
            return new CommentViewHolder(layoutInflater.inflate(R.layout.view_holder_comment_start_item, viewGroup, false), HEAD);
        }  else {
            return new CommentViewHolder(layoutInflater.inflate(R.layout.view_holder_image_comment, viewGroup, false), COMMENT);

        }
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder commentViewHolder, int i) {
//////////////////////////////////////HEAD/////////////////////////////////////////
        if (getItemViewType(i) == HEAD) {


            commentViewHolder.authorName.setText(previewImage.photographer.fullName);
            commentViewHolder.authorUserName.setText(previewImage.photographer.userName);


            GlideApp.with(context)
                    .load(previewImage.photographer.imageProfile)
                    .error(R.drawable.default_error_img)
                    .placeholder(R.drawable.default_place_holder)
                    .apply(RequestOptions.circleCropTransform())
                    .override(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
                    .into(commentViewHolder.commentAuthorIcon);


            int rate = Math.round(previewImage.rate);
            commentViewHolder.photoRating.setRating(rate);


            GlideApp.with(context)
                    .load(previewImage.url)
                    .error(R.drawable.default_error_img)
                    .placeholder(R.drawable.default_place_holder)
                    .override(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
                    .into(commentViewHolder.commentImg);

            String tagS = "";
            if (previewImage.tags != null)
                for (Tag tag : previewImage.tags) {
                    tagS = tagS + " #" + tag.name;
                }
            commentViewHolder.commentPreviewImgTags.setText(tagS);
            if (previewImage.likesCount != null)
                commentViewHolder.imgLikeNum.setText(new StringBuilder().append(previewImage.likesCount).append(" ").append(context.getResources().getString(R.string.like)).toString());
            if (previewImage.commentsCount != null)
                commentViewHolder.imgCommentNum.setText(new StringBuilder().append(previewImage.commentsCount).append(" ").append(context.getResources().getString(R.string.comment)).toString());
            if (commentAdapterAction != null) {
                commentViewHolder.imageCommentBtn.setOnClickListener(v -> {
                    commentAdapterAction.onImageCommentClicked();
                });


                if (previewImage.isLiked != null && !previewImage.isLiked) {
                    commentViewHolder.imageLikeBtn.setImageResource(R.drawable.ic_like_off_white);
                } else {
                    commentViewHolder.imageLikeBtn.setImageResource(R.drawable.ic_favorite_3);
                }

                commentViewHolder.imageLikeBtn.setOnClickListener(v -> {

                    commentAdapterAction.onImageLike(previewImage);
                });

                commentViewHolder.commentAuthorIcon.setOnClickListener(v -> {
                    commentAdapterAction.onCommentAuthorIconClicked(previewImage);
                });
            }

            if (previewImage.photographer != null) {
                if (previewImage.photographer.id.equals(Integer.valueOf(PrefUtils.getUserId(context)))) {

//                    if (previewImage.currentPhotoGrapherPhoto != null && previewImage.currentPhotoGrapherPhoto) {
//
//                    }
                    commentViewHolder.deleteBtn.setVisibility(View.VISIBLE);
                    commentViewHolder.photoRating.setVisibility(View.VISIBLE);
                    commentViewHolder.photoRating.setIsIndicator(true);
                }
            }
            commentViewHolder.deleteBtn.setOnClickListener(v -> {
                commentAdapterAction.onDeleteClicked();
            });

//////////////////////////////////////COMMENT/////////////////////////////////////////
        } else if (getItemViewType(i) == COMMENT || getItemViewType(i) == REPLY_COMMENT) {
            if (commentList.get(i).business != null) {

                if (commentList.get(i).business.thumbnail != null)
                    GlideApp.with(context)
                            .load(commentList.get(i).business.thumbnail)
                            .error(R.drawable.default_error_img)
                            .placeholder(R.drawable.default_place_holder)
                            .override(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
                            .apply(RequestOptions.circleCropTransform())
                            .into(commentViewHolder.commentAuthorImg);
                commentViewHolder.commentAuthorName.setText(new StringBuilder().append(commentList.get(i).business.firstName).append(" ").append(commentList.get(i).business.lastName).toString());


            } else if (commentList.get(i).photographer != null) {
                if (commentList.get(i).photographer.imageProfile != null)
                    GlideApp.with(context)
                            .load(commentList.get(i).photographer.imageProfile)
                            .error(R.drawable.default_error_img)
                            .placeholder(R.drawable.default_place_holder)
                            .override(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
                            .apply(RequestOptions.circleCropTransform())
                            .into(commentViewHolder.commentAuthorImg);
                commentViewHolder.commentAuthorName.setText(commentList.get(i).photographer.fullName);

            }

            if (commentList.get(i).comment != null) {
                handleCommentBody(commentViewHolder.commentVal, commentList.get(i).comment);
                if (getItemViewType(i) == REPLY_COMMENT) {
                    commentViewHolder.commentValSubContainer.setBackgroundColor(context.getResources().getColor(R.color.black242B31));
                    commentViewHolder.commentValSubContainer.setPadding(12, 12, 12, 12);
                }
            }


            if (commentList.get(i).repliesCount != null && commentList.get(i).repliesCount > 0) {
                commentViewHolder.imageCommentReplayBtn.setText(new StringBuilder().append(context.getResources().getString(R.string.view_more)).append(" ").append(commentList.get(i).repliesCount).append(" ").append(context.getResources().getString(R.string.replay)).toString());
            } else {
                commentViewHolder.imageCommentReplayBtn.setText(context.getResources().getString(R.string.replay));
            }
            if (commentAdapterAction != null && getItemViewType(i) != REPLY_COMMENT) {
                commentViewHolder.imageCommentReplayBtn.setOnClickListener(v -> {
                            if (commentViewHolder.imageCommentReplayBtn.getText().equals(context.getResources().getString(R.string.replay))) {
                                commentAdapterAction.onReplayClicked(commentList.get(i), mentions, REPLAY_ON_COMMENT);
                            } else {
                                commentAdapterAction.onReplayClicked(commentList.get(i), mentions, VIEW_REPLIES);

                            }

                        }
                );

            } else {
                commentViewHolder.imageCommentReplayBtn.setVisibility(View.GONE);
            }


         }
    }


    private DisposableObserver<TextViewTextChangeEvent> getSearchTagQuery(AutoCompleteTextView autoCompleteTextView) {
        return new DisposableObserver<TextViewTextChangeEvent>() {
            @Override
            public void onNext(TextViewTextChangeEvent textViewTextChangeEvent) {
                // user cleared search get default
                Log.e(TAG, "search string: " + autoCompleteTextView.getText().toString().trim());
                int cursorPosition = autoCompleteTextView.getSelectionStart();

                ///getting String value before cursor
                if (cursorPosition > 0) {
                    int searKeyPosition = autoCompleteTextView.getText().toString().lastIndexOf("@", cursorPosition);
                    if (searKeyPosition >= 0) {
                        commentAdapterPresenter.getMentionedUser(autoCompleteTextView.getText().toString().substring(searKeyPosition + 1, autoCompleteTextView.getSelectionStart()));
                    }

                }

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
    }

    private void handleCommentBody(TextView commentView, String commentFinalValue) {

        List<String> authorsId = Utilities.getMentionsList(commentFinalValue);
        List<String> mentionsPhotoGrapherIdIdList = new ArrayList<>();
        List<String> mentionBusinessIdList = new ArrayList<>();
        List<MentionRange> mentionsPoint = new ArrayList<MentionRange>();
        List<ClickableSpan> clickableSpanList = new ArrayList<>();
        //

        if (authorsId.size() > 0) {

            for (String authorId : authorsId) {
                String[] singleId = authorId.split("\\_");

                if (singleId[0].equals("0")) {
                    mentionsPhotoGrapherIdIdList.add(singleId[1]);
                } else if (singleId[0].equals("1")) {
                    mentionBusinessIdList.add(singleId[1]);
                }
            }


            /// Append unique identifier to mentioned user to get highLighted later
            /// And Replacing All Occurrence of photoGrapherId with actualValue
//            for (String photoGrapherId : mentionsPhotoGrapherIdIdList) {
            for (int i = 0; i < mentionsPhotoGrapherIdIdList.size(); i++) {
                Photographer photographer = getMentionedPhotoGrapher(mentionsPhotoGrapherIdIdList.get(i));
                if (mentionsPhotoGrapherIdIdList.get(i) != null) {
                    if (photographer != null) {
                        commentFinalValue = commentFinalValue.replace("@0_" + mentionsPhotoGrapherIdIdList.get(i), USER_MENTION_IDENTIFIER + photographer.fullName);
                        commentView.setText(commentFinalValue);
                    }
                }
            }

            /// Append unique identifier to mentioned user to get highLighted later
            /// And Replacing All Occurrence of businessId with actualValue
//            for (String businessId : mentionBusinessIdList) {
            for (int i = 0; i < mentionBusinessIdList.size(); i++) {
                if (getMentionedBusiness(mentionBusinessIdList.get(i)) != null) {
                    Business business = getMentionedBusiness(mentionBusinessIdList.get(i));
                    if (business != null) {
                        commentFinalValue = commentFinalValue.replace("@1_" + mentionBusinessIdList.get(i), USER_MENTION_IDENTIFIER + business.firstName + " " + business.lastName);
                        commentView.setText(commentFinalValue);
                    }
                }
            }


            for (int i = 0; i < mentionsPhotoGrapherIdIdList.size(); i++) {
                final int xx = i;
                if (getMentionedPhotoGrapher(mentionsPhotoGrapherIdIdList.get(i)) != null) {
                    Photographer photographer = getMentionedPhotoGrapher(mentionsPhotoGrapherIdIdList.get(i));
                    ///////PhotoGrapher CallBack
                    ClickableSpan noUnderLineClickSpan = new ClickableSpan() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(context, UserProfileActivity.class);
                            intent.putExtra(UserProfileActivity.USER_ID, mentionsPhotoGrapherIdIdList.get(xx));
                            intent.putExtra(UserProfileActivity.USER_TYPE, Constants.UserType.USER_TYPE_PHOTOGRAPHER);
                            context.startActivity(intent);
                        }

                        @Override
                        public void updateDrawState(TextPaint ds) {
                            super.updateDrawState(ds);
                            ds.setUnderlineText(false);
                            ds.setColor(Color.BLUE); // specific color for this link
                        }
                    };
                    String replacement = USER_MENTION_IDENTIFIER + photographer.fullName;
                    int replacementStart = commentFinalValue.indexOf(replacement);
                    //this flag is required as start index get increased in case multipleUser
                    boolean firstRound = true;
                    // this loop is required in case multiple occurrence for the same user
                    while (replacementStart >= 0) {

                        MentionRange mentionRange = new MentionRange();
                        if (firstRound) {
                            mentionRange.startPoint = replacementStart;
                            firstRound = false;

                        } else {
                            mentionRange.startPoint = replacementStart - 1;

                        }
                        mentionRange.endPoint = replacementStart + replacement.length();
                        replacementStart = commentFinalValue.indexOf(replacement, replacementStart + 1);
                        mentionsPoint.add(mentionRange);
                    }
                    clickableSpanList.add(noUnderLineClickSpan);
                    //Removing string mentions Identifier
                    commentFinalValue = commentFinalValue.replace(replacement, replacement.substring(1, replacement.length()));
                }
            }


            //////////////////////////////////////////////////////////

            for (int i = 0; i < mentionBusinessIdList.size(); i++) {

                final int xx = i;

                if (getMentionedBusiness(mentionBusinessIdList.get(i)) != null) {
                    Business business = getMentionedBusiness(mentionBusinessIdList.get(i));
                    //////business CallBack
                    ClickableSpan noUnderLineClickSpan2 = new ClickableSpan() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(context, UserProfileActivity.class);
                            intent.putExtra(UserProfileActivity.USER_ID, mentionBusinessIdList.get(xx));
                            intent.putExtra(UserProfileActivity.USER_TYPE, Constants.UserType.USER_TYPE_BUSINESS);
                            context.startActivity(intent);
                        }

                        @Override
                        public void updateDrawState(TextPaint ds) {
                            super.updateDrawState(ds);
                            ds.setUnderlineText(false);
                            ds.setColor(Color.MAGENTA); // specific color for this link
                        }
                    };


                    String replacement = USER_MENTION_IDENTIFIER + business.firstName + " " + business.lastName;
                    int replacementStart = commentFinalValue.indexOf(replacement);
                    //this flag is required as start index get increased in case multipleUser
                    boolean firstRound = true;
                    // this loop is required in case multiple occurrence for the same user
                    while (replacementStart >= 0) {

                        MentionRange mentionRange = new MentionRange();
                        if (firstRound) {
                            mentionRange.startPoint = replacementStart;
                            firstRound = false;

                        } else {
                            mentionRange.startPoint = replacementStart - 1;

                        }
                        mentionRange.endPoint = replacementStart + replacement.length();
                        replacementStart = commentFinalValue.indexOf(replacement, replacementStart + 1);

                        mentionsPoint.add(mentionRange);
                    }
                    clickableSpanList.add(noUnderLineClickSpan2);
                    //Removing string mentions Identifier
                    commentFinalValue = commentFinalValue.replace(replacement, replacement.substring(1, replacement.length()));
                }
            }

            makeLinks(commentView, mentionsPoint, clickableSpanList, commentFinalValue);
        } else {
            commentView.setText(commentFinalValue);
        }


    }


    /**
     * @param viewHolder        --->view holder contain comment value
     * @param mentionsList      --->replacement user_value flaged with (?*_)
     * @param clickableSpanList --->link CallBack
     * @param commentFinalValue --->all text value insideViewHolder
     */
    private void makeLinks(TextView viewHolder, List<MentionRange> mentionsList, List<ClickableSpan> clickableSpanList, String commentFinalValue) {
        SpannableString spannableString = new SpannableString(commentFinalValue);
        for (int i = 0; i < mentionsList.size(); i++) {


            int xs = commentFinalValue.length();

            int spannableStartPoint = mentionsList.get(i).startPoint;
            int spannableEndPoint = mentionsList.get(i).endPoint;

            ///////////////////////
            if (spannableStartPoint <= 0) {
                spannableStartPoint = 0;
            }

            while (spannableEndPoint > commentFinalValue.length()) {
                spannableEndPoint--;
            }


            spannableString.setSpan(clickableSpanList.get(i), spannableStartPoint, spannableEndPoint, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        viewHolder.setLinksClickable(true);
        viewHolder.setClickable(true);
        viewHolder.setMovementMethod(LinkMovementMethod.getInstance());

        viewHolder.setText(spannableString);

    }


    private Photographer getMentionedPhotoGrapher(String userId) {

        if (mentions.photographers != null)
            for (Photographer photographer : mentions.photographers) {
                if (photographer.id == Integer.parseInt(userId)) {
                    return photographer;
                }
            }

        return null;
    }

    private Business getMentionedBusiness(String userId) {

        if (mentions.business != null)
            for (Business business : mentions.business) {
                if (business.id == Integer.parseInt(userId)) {
                    return business;
                }
            }
        return null;
    }

    /**
     * HEAD and ADD_COMMENT section are disabled when using this adapter with "replies_list"
     * for design purposes
     */
    @Override
    public int getItemViewType(int position) {
        if (position == 0 && commentListType.equals(MAIN_COMMENT)) {
            return HEAD; //-->For Image Header Preview
        } else if (position == 0 && commentListType.equals(VIEW_REPLIES)) {
            return REPLY_COMMENT; //-->For Image Header Preview
        }  else {
            return COMMENT; //--->  Comment Cell
        }
    }


    @Override
    public int getItemCount() {
        return commentList.size();
    }

    public void setShouldShowChooseWinnerButton(boolean shouldShowChooseWinnerButton) {
        this.shouldShowChooseWinnerButton = shouldShowChooseWinnerButton;
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder {
        //header cell

        CustomTextView imgLikeNum, imgCommentNum, commentPreviewImgTags, authorName, authorUserName;
        ImageView commentImg, commentAuthorIcon;
        ImageButton imageLikeBtn, imageCommentBtn;
        RatingBar photoRating;
        Button deleteBtn;

        ///Comment_value Cell
        CardView parentCommentView;
        CustomTextView commentVal, commentAuthorName, imageCommentReplayBtn;
        ImageView commentAuthorImg;
        LinearLayout commentValSubContainer;
        //SendCommentCell
        CustomAutoCompleteTextView sendCommentImgVal;
        ImageButton sendCommentBtn;


        CommentViewHolder(View view, int type) {
            super(view);
            if (type == HEAD) {


                commentAuthorIcon = view.findViewById(R.id.comment_author_icon);
                authorName = view.findViewById(R.id.author_name);
                authorUserName = view.findViewById(R.id.author_user_name);

                commentImg = view.findViewById(R.id.comment_preview_img);
                commentPreviewImgTags = view.findViewById(R.id.comment_preview_img_tag);
                imageLikeBtn = view.findViewById(R.id.comment_preview_img_like_btn);
                imageCommentBtn = view.findViewById(R.id.comment_preview_img_comment_btn);
                imgLikeNum = view.findViewById(R.id.comment_preview_img_like_num);
                imgCommentNum = view.findViewById(R.id.comment_preview_img_comment_num);
                photoRating = view.findViewById(R.id.photo_rate);
                deleteBtn = view.findViewById(R.id.album_img_delete_btn);

            } else if (type == COMMENT) {
                parentCommentView = view.findViewById(R.id.comment_parent_view);
                commentVal = view.findViewById(R.id.comment_val);
                commentAuthorImg = view.findViewById(R.id.commentAuthorImg);
                commentAuthorName = view.findViewById(R.id.comment_author);
                imageCommentReplayBtn = view.findViewById(R.id.image_comment_replay_btn);
                commentValSubContainer = view.findViewById(R.id.comment_val_sub_container);
            }

        }

    }


    public interface CommentAdapterAction {
        void onImageLike(BaseImage baseImage);

        void onImageCommentClicked();



        void onCommentAuthorIconClicked(BaseImage baseImage);


        void onReplayClicked(Comment comment, Mentions mentions, Constants.CommentListType commentListType);

        default void onDeleteClicked() {
        }

    }



    private class MentionRange {
        int startPoint;
        int endPoint;
    }
}
