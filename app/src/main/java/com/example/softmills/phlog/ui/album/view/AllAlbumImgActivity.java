package com.example.softmills.phlog.ui.album.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.example.softmills.phlog.R;
import com.example.softmills.phlog.Utiltes.Constants;
import com.example.softmills.phlog.Utiltes.Constants.PhotosListType;
import com.example.softmills.phlog.Utiltes.PrefUtils;
import com.example.softmills.phlog.base.BaseActivity;
import com.example.softmills.phlog.base.commonmodel.BaseImage;
import com.example.softmills.phlog.base.widgets.CustomRecyclerView;
import com.example.softmills.phlog.base.widgets.CustomTextView;
import com.example.softmills.phlog.base.widgets.PagingController;
import com.example.softmills.phlog.ui.album.presenter.AllAlbumImgPresnter;
import com.example.softmills.phlog.ui.album.presenter.AllAlbumImgPresnterImpl;
import com.example.softmills.phlog.ui.album.view.adapter.AllAlbumImgAdapter;
import com.example.softmills.phlog.ui.commentimage.view.ImageCommentActivity;
import com.example.softmills.phlog.ui.userprofile.view.UserProfileActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.example.softmills.phlog.Utiltes.Constants.PhotosListType.CURRENT_PHOTOGRAPHER_PHOTOS_LIST;
import static com.example.softmills.phlog.Utiltes.Constants.PhotosListType.CURRENT_PHOTOGRAPHER_SAVED_LIST;

/**
 * Created by abdalla_maged on 11/5/2018.
 */
public class AllAlbumImgActivity extends BaseActivity implements AllAlbumImgActivityView {


    public static String ALBUM_ID = "album_id";
    public static String ALL_ALBUM_IMAGES = "album_list";
    public static String SELECTED_IMG_ID = "selected_img_id";
    /// set your Proper type of list for later processes
    public static String LIST_TYPE = "list_type";
    public static String LIST_NAME = "list_name";
    public static String CURRENT_PAGE = "current_page";
    private PhotosListType photosListType;
    private ImageButton mainBackBtn;
    private CustomTextView topBarTitle;
    private AllAlbumImgAdapter allAlbumImgAdapter;
    private CustomRecyclerView allAlbumImgRv;
    private List<BaseImage> albumImgList = new ArrayList<>();
    private ProgressBar albumImgProgress;
    private AllAlbumImgPresnter allAlbumImgPresnter;
    private PagingController pagingController;
    /**
     * in case activity loaded with data previously
     * and we would to continue from most resent page
     **/
    private int currentPage = 0;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_album_img);
        initPresenter();
        initView();
    }


    @Override
    public void initView() {
        if (getIntent().getParcelableArrayListExtra(ALL_ALBUM_IMAGES) != null) {
            topBarTitle = findViewById(R.id.toolbar_title);
            mainBackBtn = findViewById(R.id.back_btn);
            mainBackBtn.setVisibility(View.VISIBLE);
            albumImgProgress = findViewById(R.id.album_img_list_progress_bar);
            allAlbumImgRv = findViewById(R.id.album_img_list_rv);

            photosListType = (PhotosListType) getIntent().getSerializableExtra(LIST_TYPE);

            if (photosListType == CURRENT_PHOTOGRAPHER_PHOTOS_LIST || photosListType == CURRENT_PHOTOGRAPHER_SAVED_LIST) {
                if (getIntent().getIntExtra(CURRENT_PAGE, -1) >= 0) {
                    currentPage = getIntent().getIntExtra(CURRENT_PAGE, 0);
                }
            }


            if (getIntent().getStringExtra(LIST_NAME) != null) {
                topBarTitle.setText(getIntent().getStringExtra(LIST_NAME));
            }

            this.albumImgList = getIntent().<BaseImage>getParcelableArrayListExtra(ALL_ALBUM_IMAGES);
            int selectedPosition = getIntent().getIntExtra(SELECTED_IMG_ID, 0);


            allAlbumImgAdapter = new AllAlbumImgAdapter(albumImgList, photosListType);
            allAlbumImgRv.setAdapter(allAlbumImgAdapter);


            for (int i = 0; i < albumImgList.size(); i++) {
                if (albumImgList.get(i).id == selectedPosition) {
                    Objects.requireNonNull(allAlbumImgRv.getLayoutManager()).smoothScrollToPosition(allAlbumImgRv, null, i);
                    break;
                }

            }

            initListener();
        }


    }

    @Override
    public void initPresenter() {
        allAlbumImgPresnter = new AllAlbumImgPresnterImpl(this, this);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    private void initListener() {

        allAlbumImgAdapter.onAlbumImgClicked = new AllAlbumImgAdapter.OnAlbumImgClicked() {
            @Override
            public void onAlbumImgClick(BaseImage albumImg) {
                Intent intent = new Intent(getBaseContext(), ImageCommentActivity.class);
                if (photosListType != null && photosListType.equals(CURRENT_PHOTOGRAPHER_PHOTOS_LIST)) {
                    albumImg.photographer = PrefUtils.getCurrentUser(getBaseContext());
                    albumImg.currentPhotoGrapherPhoto = true;
                } else if (photosListType != null && photosListType.equals(CURRENT_PHOTOGRAPHER_SAVED_LIST)) {
                    albumImg.photographer = PrefUtils.getCurrentUser(getBaseContext());
                }
                intent.putExtra(ImageCommentActivity.IMAGE_DATA, albumImg);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }

            @Override
            public void onAlbumImgDeleteClick(BaseImage albumImg) {
                new AlertDialog.Builder(AllAlbumImgActivity.this)
                        .setTitle(R.string.confirmation)
                        .setMessage(R.string.delete_photo_confirmation)
                        .setPositiveButton(R.string.yes, (dialog, which) -> {
                            allAlbumImgPresnter.deleteImage(albumImg);
                            dialog.dismiss();
                        }).setNegativeButton(R.string.no, (dialog, which) -> dialog.dismiss())
                        .show();
            }

            @Override
            public void onAlbumImgLikeClick(BaseImage albumImg) {
                if (albumImg.isLiked) {
                    allAlbumImgPresnter.unLikePhoto(String.valueOf(albumImg.id));
                } else {
                    allAlbumImgPresnter.likePhoto(String.valueOf(albumImg.id));
                }


            }

            @Override
            public void onAlbumImgCommentClick(BaseImage albumImg) {
                Intent intent = new Intent(getBaseContext(), ImageCommentActivity.class);
                intent.putExtra(ImageCommentActivity.IMAGE_DATA, albumImg);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }

            @Override
            public void onAlbumImgSaveClick(BaseImage albumImg) {

                if (albumImg.isSaved) {
                    allAlbumImgPresnter.unSaveToProfileImage(albumImg);
                } else {
                    allAlbumImgPresnter.saveToProfileImage(albumImg);

                }
            }

            @Override
            public void onAlbumImgFollowClick(BaseImage albumImg) {
                allAlbumImgPresnter.followImagePhotoGrapher(albumImg);
            }

            @Override
            public void onAlbumImgHeaderClick(BaseImage albumImg) {

                if (PrefUtils.getUserId(getBaseContext()).equals(String.valueOf(albumImg.photographer.id))) {
                    Intent intent = new Intent(getBaseContext(), UserProfileActivity.class);
                    intent.putExtra(UserProfileActivity.USER_ID, String.valueOf(albumImg.photographer.id));
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    getBaseContext().startActivity(intent);
                } else if (!photosListType.equals(PhotosListType.USER_PROFILE_PHOTOS_LIST)) { //case user already came from photographer Profile list do not navigate
                    Intent intent = new Intent(getBaseContext(), UserProfileActivity.class);
                    intent.putExtra(UserProfileActivity.USER_ID, String.valueOf(albumImg.photographer.id));
                    intent.putExtra(UserProfileActivity.USER_TYPE, Constants.UserType.USER_TYPE_BUSINESS);
                    getBaseContext().startActivity(intent);
                }


            }
        };


        pagingController = new PagingController(allAlbumImgRv, currentPage) {
            @Override
            public void getPagingControllerCallBack(int page) {
                if (photosListType == CURRENT_PHOTOGRAPHER_PHOTOS_LIST) {
                    allAlbumImgPresnter.getPhotoGrapherPhotosList(page);
                } else if (photosListType == CURRENT_PHOTOGRAPHER_SAVED_LIST) {
                    allAlbumImgPresnter.getPhotoGrapherSavedList(page);
                }


            }
        };

        mainBackBtn.setOnClickListener(v -> {
            onBackPressed();
        });
    }

    @Override
    public void viewAlbumImageList(List<BaseImage> albumImgList) {
//        this.albumImgList.clear();
        this.albumImgList.addAll(albumImgList);
        allAlbumImgAdapter.notifyDataSetChanged();

    }

    @Override
    public void onImageSavedToProfile(BaseImage baseImage, boolean state) {
        for (int i = 0; i < albumImgList.size(); i++) {


            if (albumImgList.get(i).id == baseImage.id) {
                albumImgList.get(i).isSaved = state;
                if (!state) {
                    if (photosListType.equals(CURRENT_PHOTOGRAPHER_SAVED_LIST)) {
                        albumImgList.remove(i);
                    }

                }
                break;
            }
        }

        allAlbumImgAdapter.notifyDataSetChanged();

    }

    @Override
    public void onImagePhotoGrapherFollowed(BaseImage baseImage, boolean state) {
        for (BaseImage mBaseImage : albumImgList) {
            if (mBaseImage.id == baseImage.id) {
                mBaseImage.photographer.isFollow = state;
                break;
            }
        }
        allAlbumImgAdapter.notifyDataSetChanged();
    }

    @Override
    public void onImagePhotoGrapherDeleted(BaseImage baseImage, boolean state) {
        for (int i = 0; i < albumImgList.size(); i++) {
            if (albumImgList.get(i).id == baseImage.id && state) {
                albumImgList.remove(i);
                allAlbumImgAdapter.notifyItemRemoved(i);
                break;
            }
        }
        allAlbumImgAdapter.notifyDataSetChanged();

    }

    @Override
    public void onImagePhotoGrapherLiked(int photoId, boolean state) {
        for (int i = 0; i < albumImgList.size(); i++) {
            if (albumImgList.get(i).id == photoId) {
                albumImgList.get(i).isLiked = state;
                if (state) {
                    albumImgList.get(i).likesCount++;
                } else {
                    albumImgList.get(i).likesCount--;
                }

                break;
            }
        }
        allAlbumImgAdapter.notifyDataSetChanged();
    }

    @Override
    public void viewAlbumImageListProgress(boolean state) {

        if (state) {
            albumImgProgress.setVisibility(View.VISIBLE);
        } else {
            albumImgProgress.setVisibility(View.GONE);
        }

    }


    @Override
    public void showToast(String msg) {
        super.showToast(msg);
    }

    @Override
    public void showMessage(String msg) {
        showToast(msg);
    }
}
