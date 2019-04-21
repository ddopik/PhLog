package com.example.softmills.phlog.ui.commentimage.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.example.softmills.phlog.R;
import com.example.softmills.phlog.Utiltes.Constants;
import com.example.softmills.phlog.Utiltes.ErrorUtils;
import com.example.softmills.phlog.Utiltes.PrefUtils;
import com.example.softmills.phlog.Utiltes.Utilities;
import com.example.softmills.phlog.base.BaseActivity;
import com.example.softmills.phlog.base.commonmodel.BaseImage;
import com.example.softmills.phlog.base.commonmodel.Business;
import com.example.softmills.phlog.base.commonmodel.Comment;
import com.example.softmills.phlog.base.commonmodel.MentionedUser;
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
import com.jakewharton.rxbinding3.widget.RxTextView;
import com.jakewharton.rxbinding3.widget.TextViewTextChangeEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import static com.example.softmills.phlog.Utiltes.Constants.CommentListType.MAIN_COMMENT;

/**
 * Created by abdalla_maged on 11/6/2018.
 */
public class ImageCommentActivity extends BaseActivity implements ImageCommentActivityView {

    public final static int REPLY_REQUEST_CODE = 876;
    public String TAG = ImageCommentActivity.class.getSimpleName();
    public static String IMAGE_DATA = "image_data";
    public static String NOTIFICATION_IMAGE_ID = "image_id";
    public static final int ImageComment_REQUEST_CODE = 1396;
    private CustomTextView toolBarTitle;
    private ImageButton backBtn;
    private BaseImage previewImage;
    private ProgressBar addCommentProgress;
    private CustomRecyclerView commentsRv;
    private List<Comment> commentList = new ArrayList<>();
    private Mentions mentions = new Mentions();
    private CommentsAdapter commentsAdapter;
    private PagingController pagingController;
    private String nextPageUrl = "1";
    private boolean isLoading;
    //SendCommentCell
    private CustomAutoCompleteTextView sendCommentImgVal;
    private ImageButton sendCommentBtn;
    private MentionsAutoCompleteAdapter mentionsAutoCompleteAdapter;
    private List<MentionedUser> mentionedUserList = new ArrayList<>();
    private DisposableObserver<TextViewTextChangeEvent> searchQuery;
    private CompositeDisposable disposable = new CompositeDisposable();

    private ImageCommentActivityPresenter imageCommentActivityPresenter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_image_commnet);

        if (getIntent().getParcelableExtra(IMAGE_DATA) != null) {
            previewImage = getIntent().getExtras().getParcelable(IMAGE_DATA);
            initPresenter();
            initView();
            initListener();
            imageCommentActivityPresenter.getImageComments(String.valueOf(previewImage.id), nextPageUrl);
        } else if (getIntent().getIntExtra(NOTIFICATION_IMAGE_ID, -1) >= 0) {
            initPresenter();
            initView();
            imageCommentActivityPresenter.getImageDetails(getIntent().getIntExtra(NOTIFICATION_IMAGE_ID, -1));
        }


    }


    @Override
    public void initView() {
        toolBarTitle = findViewById(R.id.toolbar_title);
        backBtn = findViewById(R.id.back_btn);
        addCommentProgress = findViewById(R.id.add_comment_progress);
        commentsRv = findViewById(R.id.comment_rv);
        sendCommentImgVal = findViewById(R.id.img_send_comment_val);
        sendCommentBtn = findViewById(R.id.send_comment_btn);
        if (previewImage != null) {
//            toolBarTitle.setText(previewImage.albumName);
            //force adapter to start to render Add commentView
            Comment userComment = new Comment();
            commentList.add(userComment); /// acts As default for image Header
            commentsAdapter = new CommentsAdapter(previewImage, commentList, mentions, MAIN_COMMENT);
            commentsRv.setAdapter(commentsAdapter);
        }


    }

    @Override
    public void initPresenter() {
        imageCommentActivityPresenter = new ImageCommentActivityImpl(this, this);
    }


    private void initListener() {


        pagingController = new PagingController(commentsRv) {


            @Override
            protected void loadMoreItems() {
                imageCommentActivityPresenter.getImageComments(String.valueOf(previewImage.id), nextPageUrl);

            }

            @Override
            public boolean isLastPage() {

                if (nextPageUrl == null) {
                    return true;
                } else {
                    return false;
                }

            }

            @Override
            public boolean isLoading() {
                return isLoading;
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
                Observable<Boolean> observable = null;
                if (baseImage.isLiked) {
                    observable = imageCommentActivityPresenter.unLikePhoto(baseImage);
                } else {
                    observable = imageCommentActivityPresenter.likePhoto(baseImage);
                }
                Disposable d = observable.subscribeOn(Schedulers.io())
                        .doFinally(() -> {
                            commentsAdapter.notifyItemChanged(0);
                            viewImageProgress(false);
                            Intent data = new Intent();
                            data.putExtra(IMAGE_DATA, baseImage);
                            setResult(RESULT_OK, data);
                        })
                        .subscribe(success -> {
                            baseImage.isLiked = !baseImage.isLiked;
                            if (baseImage.isLiked) baseImage.likesCount++;
                            else baseImage.likesCount--;
                        }, throwable -> {

                        });
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
            public void onReplayClicked(Comment comment, Mentions mentions, Constants.CommentListType commentListType) {

                Intent intent = new Intent(getBaseContext(), ReplayCommentActivity.class);

                intent.putExtra(ReplayCommentActivity.COMMENT_IMAGE, previewImage);
                intent.putExtra(ReplayCommentActivity.COMMENT_LIST_TYPE, commentListType);
                intent.putExtra(ReplayCommentActivity.REPLY_HEADER_COMMENT, comment);
                intent.putExtra(ReplayCommentActivity.MENTIONS, mentions);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivityForResult(intent, REPLY_REQUEST_CODE);
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
        this.commentList.addAll(this.commentList.size() , imageCommentsData.comments.commentList);

        if (imageCommentsData.mentions.business != null)
            this.mentions.business.addAll(imageCommentsData.mentions.business);
        if (imageCommentsData.mentions.photographers != null)
            this.mentions.photographers.addAll(imageCommentsData.mentions.photographers);

        commentsAdapter.notifyDataSetChanged();


    }


    @Override
    public void onImageLiked(BaseImage baseImage) {
        previewImage = baseImage;
        commentsAdapter.notifyItemChanged(0);
    }

    @SuppressLint("CheckResult")
    @Override
    public void onImageCommented(SubmitImageCommentData commentData) {
        previewImage.commentsCount++;
        Intent data = new Intent();
        data.putExtra(IMAGE_DATA, previewImage);
        setResult(RESULT_OK, data);
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
        commentsAdapter.notifyItemChanged(0);
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
        baseImage.isImageDeleted = true;
        Intent data = new Intent();
        data.putExtra(IMAGE_DATA, baseImage);
        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public void viewImageProgress(Boolean state) {
        isLoading = state;

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

    @Override
    public void setNextPageUrl(String page) {
        this.nextPageUrl = page;
    }

    @Override
    public void viewImageDetails(BaseImage baseImage) {
        previewImage = baseImage;
        initView();
        initListener();
        imageCommentActivityPresenter.getImageComments(String.valueOf(previewImage.id), nextPageUrl);

    }

    @Override
    public void onImageFailed() {

        new AlertDialog.Builder(this)
                .setTitle(R.string.campaign_not_available)
                .setPositiveButton(R.string.go_back, (dialog, which) -> {
                    finish();
                    dialog.dismiss();
                }).show();

    }

    @SuppressLint("CheckResult")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REPLY_REQUEST_CODE)
            if (resultCode == RESULT_OK) {
                Comment comment = data.getParcelableExtra(ReplayCommentActivity.REPLY_HEADER_COMMENT);
                BaseImage image = data.getParcelableExtra(ReplayCommentActivity.COMMENT_IMAGE);
                if (comment != null && image != null) {
                    previewImage.commentsCount = image.commentsCount;
                    Intent intent = new Intent();
                    intent.putExtra(IMAGE_DATA, previewImage);
                    setResult(RESULT_OK, intent);
                    commentsAdapter.notifyItemChanged(0);
                    Observable.just(comment)
                            .observeOn(Schedulers.computation())
                            .map(c -> {
                                for (Comment _c : commentList) {
                                    if (c.id.equals(_c.id)) {
                                        return commentList.indexOf(_c);
                                    }
                                }
                                return -1;
                            })
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeOn(Schedulers.io())
                            .subscribe(position -> {
                                if (position != -1) {
                                    commentsAdapter.notifyItemChanged(position);
                                }
                            });
                }
            }
    }

    private void set() {
        mentionsAutoCompleteAdapter = new MentionsAutoCompleteAdapter(getBaseContext(), R.layout.view_holder_mentioned_user, mentionedUserList);
        mentionsAutoCompleteAdapter.setNotifyOnChange(true);
        sendCommentImgVal.setAdapter(mentionsAutoCompleteAdapter);
        sendCommentImgVal.setMovementMethod(new ScrollingMovementMethod());
        sendCommentImgVal.setThreshold(0);


        sendCommentImgVal.setOnItemClickListener((parent, view, position, id) -> {
            sendCommentImgVal.handleMentionedCommentBody(position, mentionedUserList);

        });


        if (searchQuery == null) {
            searchQuery = getSearchTagQuery(sendCommentImgVal);
            sendCommentImgVal.addTextChangedListener(new TextWatcher() {
                int cursorPosition = sendCommentImgVal.getSelectionStart();

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    int mentionIdentifierCharPosition = sendCommentImgVal.getText().toString().indexOf("@", cursorPosition - 2);
                    if ((mentionIdentifierCharPosition + 1) >= sendCommentImgVal.getText().toString().length() || mentionIdentifierCharPosition == -1) {
                        mentionedUserList.clear();
                        mentionsAutoCompleteAdapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            disposable.add(

                    RxTextView.textChangeEvents(sendCommentImgVal)
                            .skipInitialValue()
                            .debounce(900, TimeUnit.MILLISECONDS)
                            .distinctUntilChanged()
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeWith(searchQuery)
            );
        }


        sendCommentBtn.setOnClickListener(v -> {
            String comment = sendCommentImgVal.prepareCommentToSend();
            if (comment.length() > 0) {
                imageCommentActivityPresenter.submitComment(String.valueOf(previewImage.id), comment);

            } else {
                showToast(getResources().getString(R.string.comment_cant_not_be_null));
            }

            sendCommentImgVal.getText().clear();

        });


        mentionsAutoCompleteAdapter.onUserClicked = socialUser -> {
        };
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
                        imageCommentActivityPresenter.getMentionedUser(autoCompleteTextView.getText().toString().substring(searKeyPosition + 1, autoCompleteTextView.getSelectionStart()));
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

    @Override
    public void viewMentionedUsers(List<MentionedUser> mentionedUserList) {
        this.mentionedUserList.clear();
        this.mentionedUserList.addAll(mentionedUserList);
        mentionsAutoCompleteAdapter.notifyDataSetChanged();
    }

}