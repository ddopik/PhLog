package com.example.softmills.phlog.ui.commentimage.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import com.example.softmills.phlog.R;
import com.example.softmills.phlog.Utiltes.Constants;
 import com.example.softmills.phlog.Utiltes.ErrorUtils;
import com.example.softmills.phlog.Utiltes.PrefUtils;
import com.example.softmills.phlog.Utiltes.Utilities;
import com.example.softmills.phlog.base.BaseActivity;
import com.example.softmills.phlog.base.commonmodel.BaseImage;
import com.example.softmills.phlog.base.commonmodel.Business;
import com.example.softmills.phlog.base.commonmodel.Comment;
import com.example.softmills.phlog.base.commonmodel.Mentions;
import com.example.softmills.phlog.base.commonmodel.Photographer;
import com.example.softmills.phlog.base.widgets.CustomAutoCompleteTextView;
import com.example.softmills.phlog.base.widgets.CustomRecyclerView;
import com.example.softmills.phlog.base.widgets.CustomTextView;
import com.example.softmills.phlog.base.widgets.PagingController;
import com.example.softmills.phlog.ui.album.view.adapter.CommentsAdapter;
import com.example.softmills.phlog.ui.commentimage.model.ImageCommentsData;
import com.example.softmills.phlog.ui.commentimage.model.SubmitImageCommentData;
import com.example.softmills.phlog.ui.commentimage.presenter.ImageCommentActivityImpl;
import com.example.softmills.phlog.ui.commentimage.presenter.ImageCommentActivityPresenter;
import com.example.softmills.phlog.ui.commentimage.replay.view.ReplayCommentActivity;
import com.example.softmills.phlog.ui.userprofile.view.UserProfileActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE;
import static com.example.softmills.phlog.Utiltes.Constants.CommentListType.MAIN_COMMENT;

/**
 * Created by abdalla_maged on 11/6/2018.
 */
public class ImageCommentActivity extends BaseActivity implements ImageCommentActivityView {

    public String TAG = ImageCommentActivity.class.getSimpleName();
    public static String IMAGE_DATA = "image_data";
    public static String IMAGE_TYPE = "image_type";
    public static final int ImageComment_REQUEST_CODE = 1396;
    private CustomTextView toolBarTitle;
    private ImageButton backBtn;
    private BaseImage previewImage;
    private FrameLayout addCommentProgress;
    private CustomRecyclerView commentsRv;
    private List<Comment> commentList = new ArrayList<>();
    private Mentions mentions = new Mentions();
    private CommentsAdapter commentsAdapter;
    private PagingController pagingController;
    private ImageCommentActivityPresenter imageCommentActivityPresenter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getIntent().getParcelableExtra(IMAGE_DATA) != null) {
            setContentView(R.layout.activity_image_commnet);
            previewImage = getIntent().getExtras().getParcelable(IMAGE_DATA);



            initPresenter();
            initView();
            initListener();
        }


    }


    @Override
    public void initView() {
        toolBarTitle = findViewById(R.id.toolbar_title);
        backBtn = findViewById(R.id.back_btn);

        addCommentProgress = findViewById(R.id.add_comment_progress);

        commentsRv = findViewById(R.id.comment_rv);
        toolBarTitle.setText(previewImage.albumName);
        //force adapter to start to render Add commentView
        Comment userComment = new Comment();
        commentList.add(userComment); /// acts As default for image Header
        commentList.add(userComment);/// acts As default for image Add comment

        commentsAdapter = new CommentsAdapter(previewImage, commentList, mentions, MAIN_COMMENT);
        commentsRv.setAdapter(commentsAdapter);
        imageCommentActivityPresenter.getImageComments(String.valueOf(previewImage.id), "0");

    }

    @Override
    public void initPresenter() {
        imageCommentActivityPresenter = new ImageCommentActivityImpl(this, this);
    }


    private void initListener() {

        pagingController = new PagingController(commentsRv) {
            @Override
            public void getPagingControllerCallBack(int page) {
                imageCommentActivityPresenter.getImageComments(String.valueOf(previewImage.id), String.valueOf(page));
            }
        };


        commentsAdapter.commentAdapterAction = new CommentsAdapter.CommentAdapterAction() {

            @Override
            public void onCommentAuthorIconClicked(BaseImage baseImage) {
                if (PrefUtils.getUserId(getBaseContext()).equals(String.valueOf(baseImage.photographer.id))) {
                    Intent intent = new Intent(getBaseContext(), UserProfileActivity.class);
                    intent.putExtra(UserProfileActivity.USER_ID, String.valueOf(baseImage.photographer.id));
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    getBaseContext().startActivity(intent);
                }
            }

            @Override
            public void onImageLike(BaseImage baseImage) {
                if (baseImage.isLiked) {
                    imageCommentActivityPresenter.unLikePhoto(baseImage);
                } else {
                    imageCommentActivityPresenter.likePhoto(baseImage);
                }
            }

            @Override
            public void onSubmitComment(String comment) {

                if (comment.length() > 0) {
                    imageCommentActivityPresenter.submitComment(String.valueOf(previewImage.id), comment);

                } else {
                    showToast(getResources().getString(R.string.comment_cant_not_be_null));
                }
            }

            @Override
            public void onImageCommentClicked() {


//                if (commentList.size() <= 3) {
//                    commentsRgit v.scrollToPosition(commentList.size() - 1);
//                    CustomAutoCompleteTextView customAutoCompleteTextView = (CustomAutoCompleteTextView) commentsRv.getChildAt(commentList.size() - 1).findViewById(R.id.img_send_comment_val);
//                    customAutoCompleteTextView.requestFocus();
//                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                    imm.showSoftInput(customAutoCompleteTextView, InputMethodManager.SHOW_IMPLICIT);
////
//                } else {
                   /**
                    *  Pegging causing this block to crash
                    * */
//                    RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
//                        @Override
//                        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                            switch (newState) {
//                                case SCROLL_STATE_IDLE:
//                                    //we reached the target position
//                                    CustomAutoCompleteTextView customAutoCompleteTextView = commentsRv.getChildAt(commentsRv.getChildCount()-1).findViewById(R.id.img_send_comment_val);
//                                    customAutoCompleteTextView.requestFocus();
////
//                                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                                    imm.showSoftInput(customAutoCompleteTextView, InputMethodManager.SHOW_IMPLICIT);
//                                    recyclerView.removeOnScrollListener(this);
//                                    break;
//                            }
//                        }
//                    };
//
//                    commentsRv.addOnScrollListener(onScrollListener);
//                    commentsRv.getLayoutManager().smoothScrollToPosition(commentsRv, null, commentList.size() - 1);


//                }
            }


            @Override
            public void onReplayClicked(Comment comment, Constants.CommentListType commentListType) {

                Intent intent = new Intent(getBaseContext(), ReplayCommentActivity.class);

                intent.putExtra(ReplayCommentActivity.COMMENT_IMAGE, previewImage);
                intent.putExtra(ReplayCommentActivity.COMMENT_LIST_TYPE, commentListType);
                intent.putExtra(ReplayCommentActivity.REPLY_HEADER_COMMENT, comment);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }

            @Override
            public void onDeleteClicked() {
                new AlertDialog.Builder(ImageCommentActivity.this)
                        .setTitle(R.string.confirmation)
                        .setMessage(R.string.delete_photo_confirmation)
                        .setPositiveButton(R.string.yes, (dialog, which) -> {
                            imageCommentActivityPresenter.deleteImage(previewImage);
                            dialog.dismiss();
                        }).setNegativeButton(R.string.no, (dialog, which) -> dialog.dismiss())
                        .show();
            }
        };


        backBtn.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.putExtra(IMAGE_DATA, previewImage);
            setResult(RESULT_OK, intent);
            finish();

        });
    }


    @Override
    public void viewPhotoComment(ImageCommentsData imageCommentsData) {

        Collections.reverse(imageCommentsData.comments.commentList);
        this.commentList.addAll(this.commentList.size() - 1, imageCommentsData.comments.commentList);

        if (imageCommentsData.mentions.business != null)
            this.mentions.business.addAll(imageCommentsData.mentions.business);
        if (imageCommentsData.mentions.photographers != null)
            this.mentions.photographers.addAll(imageCommentsData.mentions.photographers);

        commentsAdapter.notifyDataSetChanged();


    }


    @Override
    public void onImageLiked(BaseImage baseImage) {
        previewImage = baseImage;
        commentsAdapter.notifyDataSetChanged();


    }

    @SuppressLint("CheckResult")
    @Override
    public void onImageCommented(SubmitImageCommentData commentData) {
        // (1) is A default value to view AddComment layout in case there is now Comments
        this.commentList.add(commentList.size() - 1, commentData.comment);

        // prevent mentioned user from duplication
        reSortMentionList(commentData.mentions).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mentions -> {
                    this.mentions.business.addAll(mentions.business);
                    this.mentions.photographers.addAll(mentions.photographers);
                    commentsAdapter.notifyDataSetChanged();
                }, throwable -> {
                    ErrorUtils.Companion.setError(this, TAG, throwable);
                });
        Utilities.hideKeyboard(this);

    }

    @Override
    public void viewOnImagedAddedToCart(boolean state) {

        if (state) {
            previewImage.isCart = true;
        }
        commentsAdapter.notifyDataSetChanged();
    }

    @Override
    public void viewOnImageRate(BaseImage baseImage) {
        if (baseImage != null) {
            previewImage.rate = baseImage.rate;
            previewImage.isRated = baseImage.isRated;
            commentsAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void onImageDeleted(BaseImage baseImage, boolean state) {
        finish();
    }

    @Override
    public void viewImageProgress(Boolean state) {
        if (state) {
            addCommentProgress.setVisibility(View.VISIBLE);
        } else {
            addCommentProgress.setVisibility(View.GONE);
        }
    }


    @Override
    public void viewMessage(String msg) {
        showToast(msg);
    }


    private Observable<Mentions> reSortMentionList(Mentions mentionsNew) {

        Mentions mentions = new Mentions();
        this.mentions.photographers.addAll(mentionsNew.photographers);
        this.mentions.business.addAll(mentionsNew.business);
        return Observable.fromCallable(() -> {


            for (Photographer newPhotoGrapher : mentionsNew.photographers) {

                for (int i = 0; i < this.mentions.photographers.size(); i++) {
                    if (mentions.photographers.get(i).id.equals(newPhotoGrapher.id)) {
                        break;
                    }
                    if (i == this.mentions.photographers.size() - 1) {
                        this.mentions.photographers.add(newPhotoGrapher);
                    }
                }


            }

            for (Business newBusiness : mentionsNew.business) {

                for (int i = 0; i < this.mentions.business.size(); i++) {
                    if (mentions.business.get(i).id.equals(newBusiness.id)) {
                        break;
                    }
                    if (i == this.mentions.business.size() - 1) {
                        this.mentions.business.add(newBusiness);
                    }
                }


            }
            return mentions;
        });

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra(IMAGE_DATA, previewImage);
        setResult(RESULT_OK, intent);
        finish();
    }


}