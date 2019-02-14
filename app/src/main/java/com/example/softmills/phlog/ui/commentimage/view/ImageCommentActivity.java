package com.example.softmills.phlog.ui.commentimage.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import com.example.softmills.phlog.R;
import com.example.softmills.phlog.base.BaseActivity;
import com.example.softmills.phlog.base.commonmodel.BaseImage;
import com.example.softmills.phlog.base.commonmodel.Business;
import com.example.softmills.phlog.base.commonmodel.Comment;
import com.example.softmills.phlog.base.commonmodel.Mentions;
import com.example.softmills.phlog.base.commonmodel.Photographer;
import com.example.softmills.phlog.base.eventbus.SendCommentEvent;
import com.example.softmills.phlog.base.widgets.CustomRecyclerView;
import com.example.softmills.phlog.base.widgets.CustomTextView;
import com.example.softmills.phlog.base.widgets.PagingController;
import com.example.softmills.phlog.ui.album.view.adapter.CommentsAdapter;
import com.example.softmills.phlog.ui.commentimage.model.ImageCommentsData;
import com.example.softmills.phlog.ui.commentimage.model.SubmitImageCommentData;
import com.example.softmills.phlog.ui.commentimage.presenter.ImageCommentActivityImpl;
import com.example.softmills.phlog.ui.commentimage.presenter.ImageCommentActivityPresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abdalla_maged on 11/6/2018.
 */
public class ImageCommentActivity extends BaseActivity implements ImageCommentActivityView {

    public static String IMAGE_DATA = "image_data";
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
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }


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

        commentsAdapter = new CommentsAdapter(previewImage, commentList, mentions);
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




        };


        backBtn.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.putExtra(IMAGE_DATA, previewImage);
            setResult(RESULT_OK, intent);
            finish();

        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(SendCommentEvent sendCommentEvent) {
//
//commentsAdapter.setItemsCanFocus
//        listView.setItemsCanFocus(true);
    }


    @Override
    public void viewPhotoComment(ImageCommentsData imageCommentsData) {


        if (imageCommentsData.comments.commentList.size() == 0) {
            imageCommentsData.comments.commentList.clear();
            // (1) is A default value to view AddComment layout in case there is now Comments
            this.commentList.addAll(1, imageCommentsData.comments.commentList);
        }else {
            if(commentList.get(1).comment ==null){
                commentList.remove(1);
            }
            this.commentList.addAll( imageCommentsData.comments.commentList);

        }


        if (imageCommentsData.mentions.business != null)
            this.mentions.business.addAll(imageCommentsData.mentions.business);
        if (imageCommentsData.mentions.photographers != null)
            this.mentions.photographers.addAll(imageCommentsData.mentions.photographers);

        commentsAdapter.notifyDataSetChanged();

    }

    private void updateMentionedUserList(SubmitImageCommentData submitImageCommentData) {


        if (submitImageCommentData.mentions.business != null) {

            for (Business newBusiness : submitImageCommentData.mentions.business) {
                for (int i = 0; i < mentions.business.size(); i++) {
                    if (mentions.business.get(i).id.equals(newBusiness.id)) {
                        continue;
                    }
                    if (i == (mentions.business.size() - 1) && !mentions.business.get(i).id.equals(newBusiness.id))
                        mentions.business.add(newBusiness);
                }
            }


        }

        if (submitImageCommentData.mentions.photographers != null) {

            for (Photographer newPhotographer : submitImageCommentData.mentions.photographers) {
                for (int i = 0; i < mentions.photographers.size(); i++) {
                    if (mentions.photographers.get(i).id.equals(newPhotographer.id)) {
                        continue;
                    }
                    if (i == (mentions.photographers.size() - 1) && !mentions.photographers.get(i).id.equals(newPhotographer.id))
                        mentions.photographers.add(newPhotographer);
                }
            }


        }


        commentsAdapter.notifyDataSetChanged();
    }


    @Override
    public void onImageLiked(BaseImage baseImage) {
        previewImage = baseImage;
        commentsAdapter.notifyDataSetChanged();


    }

    @Override
    public void onImageCommented(SubmitImageCommentData commentData) {
        // (1) is A default value to view AddComment layout in case there is now Comments
        this.commentList.add(1, commentData.comment);
        updateMentionedUserList(commentData);
        commentsAdapter.notifyDataSetChanged();

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


    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra(IMAGE_DATA, previewImage);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
}