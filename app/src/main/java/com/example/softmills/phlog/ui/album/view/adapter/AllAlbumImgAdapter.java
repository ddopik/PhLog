package com.example.softmills.phlog.ui.album.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.softmills.phlog.R;
import com.example.softmills.phlog.Utiltes.Constants.PhotosListType;
import com.example.softmills.phlog.Utiltes.GlideApp;
import com.example.softmills.phlog.Utiltes.PrefUtils;
import com.example.softmills.phlog.base.commonmodel.BaseImage;
import com.example.softmills.phlog.base.commonmodel.Photographer;
import com.example.softmills.phlog.base.commonmodel.Tag;
import com.o_bdreldin.loadingbutton.LoadingButton;

import java.util.List;

import static com.example.softmills.phlog.Utiltes.Constants.PhotosListType.CURRENT_PHOTOGRAPHER_PHOTOS_LIST;
import static com.example.softmills.phlog.Utiltes.Constants.PhotosListType.CURRENT_PHOTOGRAPHER_SAVED_LIST;
import static com.example.softmills.phlog.Utiltes.Constants.PhotosListType.SOCIAL_LIST;

/**
 * Created by abdalla_maged on 11/5/2018.
 */
public class AllAlbumImgAdapter extends RecyclerView.Adapter<AllAlbumImgAdapter.AlbumImgViewHolder> {

    private List<BaseImage> albumImgList;
    private Context context;
    private PhotosListType photosListType;
    public OnAlbumImgClicked onAlbumImgClicked;


    public AllAlbumImgAdapter(List<BaseImage> albumImgList, PhotosListType photosListType) {
        this.albumImgList = albumImgList;
        this.photosListType = photosListType;

    }


    @NonNull
    @Override
    public AlbumImgViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.view_holder_album_img, viewGroup, false);
        this.context = viewGroup.getContext();
        return new AlbumImgViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumImgViewHolder albumImgViewHolder, int i) {


        Photographer imagePhotographer;

        if (photosListType.equals(CURRENT_PHOTOGRAPHER_PHOTOS_LIST) ) {
            imagePhotographer = PrefUtils.getCurrentUser(context);
        } else {
            imagePhotographer = albumImgList.get(i).photographer;
        }


        albumImgViewHolder.followPhotoGrapherBtn.setLoading(false);
        if (imagePhotographer.id == Integer.parseInt(PrefUtils.getUserId(context))) {
            albumImgViewHolder.followPhotoGrapherBtn.setVisibility(View.GONE);
        } else {
            imagePhotographer = albumImgList.get(i).photographer;
            if (!imagePhotographer.isFollow) {
                albumImgViewHolder.followPhotoGrapherBtn.setText(context.getResources().getString(R.string.follow));
            } else {
                albumImgViewHolder.followPhotoGrapherBtn.setText(context.getString(R.string.un_follow));

            }
        }


        GlideApp.with(context)
                .load(albumImgList.get(i).thumbnailUrl)
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .placeholder(R.drawable.default_user_pic)
                .error(R.drawable.phlog_logo)
                .into(albumImgViewHolder.albumIcon);


        GlideApp.with(context)
                .load(albumImgList.get(i).url)
                .centerCrop()
                .error(R.drawable.default_error_img)
                .placeholder(R.drawable.default_user_pic)
                .into(albumImgViewHolder.albumImg);

        String tagS = "";
        if (albumImgList.get(i).tags != null) {
            for (Tag tag : albumImgList.get(i).tags) {
                tagS = tagS + " #" + tag.name;
            }
        }


        if (albumImgList.get(i).isSaved) {
            albumImgViewHolder.albumImgSaveBtn.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_saved));
        } else {
            albumImgViewHolder.albumImgSaveBtn.setImageDrawable(context.getResources().getDrawable(R.drawable.un_saved));
        }
        albumImgViewHolder.imageCommentTagVal.setText(tagS);


        if (imagePhotographer.isFollow) {
            albumImgViewHolder.followPhotoGrapherBtn.setText(context.getResources().getString(R.string.un_follow));
        } else {
            albumImgViewHolder.followPhotoGrapherBtn.setText(context.getResources().getString(R.string.follow));
        }

        if (albumImgList.get(i).likesCount != null)
            albumImgViewHolder.albumImgLikeVal.setText(new StringBuilder().append(albumImgList.get(i).likesCount).append(" Likes").toString());
        if (albumImgList.get(i).commentsCount != null)
            albumImgViewHolder.albumImgCommentVal.setText(new StringBuilder().append(albumImgList.get(i).commentsCount).append(" Comments").toString());
        if (!albumImgList.get(i).isLiked) {
            albumImgViewHolder.albumImgLike.setImageResource(R.drawable.ic_like_off_gray);
        } else {
            albumImgViewHolder.albumImgLike.setImageResource(R.drawable.ic_like_on);
        }

        setImagePhotoGrapherInfo(albumImgViewHolder, imagePhotographer);


        if (onAlbumImgClicked != null) {
            albumImgViewHolder.albumImg.setOnClickListener(v -> onAlbumImgClicked.onAlbumImgClick(albumImgList.get(i)));
            albumImgViewHolder.albumImgLike.setOnClickListener(v -> onAlbumImgClicked.onAlbumImgLikeClick(albumImgList.get(i)));
            albumImgViewHolder.albumImgComment.setOnClickListener(v -> onAlbumImgClicked.onAlbumImgCommentClick(albumImgList.get(i)));
            albumImgViewHolder.albumImgLikeVal.setOnClickListener(v -> onAlbumImgClicked.onAlbumImgLikeClick(albumImgList.get(i)));
            albumImgViewHolder.albumImgCommentVal.setOnClickListener(v -> onAlbumImgClicked.onAlbumImgCommentClick(albumImgList.get(i)));
            albumImgViewHolder.albumImgHeader.setOnClickListener(v -> onAlbumImgClicked.onAlbumImgHeaderClick(albumImgList.get(i)));
            albumImgViewHolder.followPhotoGrapherBtn.setOnClickListener(v -> {
                albumImgViewHolder.followPhotoGrapherBtn.setLoading(true);
                onAlbumImgClicked.onAlbumImgFollowClick(albumImgList.get(i));
            });
            albumImgViewHolder.albumImgSaveBtn.setOnClickListener(v -> {
                onAlbumImgClicked.onAlbumImgSaveClick(albumImgList.get(i));
            });
            albumImgViewHolder.albumImgDeleteBtn.setOnClickListener(v -> {
                onAlbumImgClicked.onAlbumImgDeleteClick(albumImgList.get(i));
            });

        }


        albumImgViewHolder.albumImgSaveBtn.setVisibility(View.INVISIBLE);
        albumImgViewHolder.albumImgDeleteBtn.setVisibility(View.INVISIBLE);
        albumImgViewHolder.followPhotoGrapherBtn.setVisibility(View.INVISIBLE);
        switch (photosListType) {
            case SOCIAL_LIST: {
                albumImgViewHolder.albumImgSaveBtn.setVisibility(View.VISIBLE);
                albumImgViewHolder.albumImgDeleteBtn.setVisibility(View.INVISIBLE);
                if (imagePhotographer.id == Integer.parseInt(PrefUtils.getUserId(context))) {
                    albumImgViewHolder.followPhotoGrapherBtn.setVisibility(View.INVISIBLE);
                } else if (albumImgList.get(i).photographer.isFollow) {
                    albumImgViewHolder.followPhotoGrapherBtn.setText(context.getResources().getString(R.string.un_follow));
                } else {
                    albumImgViewHolder.followPhotoGrapherBtn.setText(context.getResources().getString(R.string.follow));
                }
                break;
            }
            case CURRENT_PHOTOGRAPHER_SAVED_LIST: {
                albumImgViewHolder.albumImgSaveBtn.setVisibility(View.VISIBLE);
                albumImgViewHolder.albumImgDeleteBtn.setVisibility(View.INVISIBLE);
                albumImgViewHolder.followPhotoGrapherBtn.setVisibility(View.INVISIBLE);
                break;
            }
            case CURRENT_PHOTOGRAPHER_PHOTOS_LIST: {
                albumImgViewHolder.albumImgSaveBtn.setVisibility(View.INVISIBLE);
                albumImgViewHolder.albumImgDeleteBtn.setVisibility(View.VISIBLE);
                albumImgViewHolder.followPhotoGrapherBtn.setVisibility(View.INVISIBLE);
                break;
            }
            case USER_PROFILE_PHOTOS_LIST: {
                albumImgViewHolder.albumImgSaveBtn.setVisibility(View.VISIBLE);
                albumImgViewHolder.albumImgDeleteBtn.setVisibility(View.INVISIBLE);
                albumImgViewHolder.followPhotoGrapherBtn.setVisibility(View.VISIBLE);
                break;
            }
            case ALBUM_PREVIEW_LIST: {

                albumImgViewHolder.albumImgSaveBtn.setVisibility(View.VISIBLE);
                albumImgViewHolder.albumImgDeleteBtn.setVisibility(View.INVISIBLE);
                albumImgViewHolder.followPhotoGrapherBtn.setVisibility(View.VISIBLE);
                break;
            }
//            currently not available
//            case CAMPAIGN_IMAGES_LIST: {
//                albumImgViewHolder.albumImgSaveBtn.setVisibility(View.VISIBLE);
//                albumImgViewHolder.albumImgDeleteBtn.setVisibility(View.INVISIBLE);
//                albumImgViewHolder.followPhotoGrapherBtn.setVisibility(View.VISIBLE);
//                break;
//            }
        }


    }

    private void setImagePhotoGrapherInfo(AlbumImgViewHolder albumImgViewHolder, Photographer photoGrapherInfo) {
        albumImgViewHolder.authorName.setText(photoGrapherInfo.fullName);
        albumImgViewHolder.authorUserName.setText(new StringBuilder().append("@").append(photoGrapherInfo.userName).toString());
        GlideApp.with(context)
                .load(photoGrapherInfo.imageProfile)
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .placeholder(R.drawable.default_user_pic)
                .error(R.drawable.phlog_logo)
                .into(albumImgViewHolder.albumIcon);

    }

    @Override
    public int getItemCount() {
        return albumImgList.size();
    }

    class AlbumImgViewHolder extends RecyclerView.ViewHolder {

        LinearLayout albumImgHeader;
        ImageView albumIcon, albumImg;
        TextView authorName, authorUserName, imageCommentTagVal, albumImgLikeVal, albumImgCommentVal;
        ImageButton albumImgLike, albumImgComment, albumImgSaveBtn;
        Button albumImgDeleteBtn;
        LoadingButton followPhotoGrapherBtn;

        AlbumImgViewHolder(View view) {
            super(view);
            albumImgHeader = view.findViewById(R.id.album_img_header);

            albumIcon = view.findViewById(R.id.album_icon);
            albumImg = view.findViewById(R.id.album_img);
            authorName = view.findViewById(R.id.author_name);
            imageCommentTagVal = view.findViewById(R.id.image_comment_tag_val);
            authorUserName = view.findViewById(R.id.author_user_name);
            albumImgLikeVal = view.findViewById(R.id.album_img_like_count);
            albumImgCommentVal = view.findViewById(R.id.album_img_comment_count);
            albumImgLike = view.findViewById(R.id.album_img_like_btn);
            albumImgComment = view.findViewById(R.id.album_img_comment);
            albumImgSaveBtn = view.findViewById(R.id.album_img_save_btn);
            albumImgDeleteBtn = view.findViewById(R.id.album_img_delete_btn);
            followPhotoGrapherBtn = view.findViewById(R.id.follow_photographer);
        }
    }

    public interface OnAlbumImgClicked {
        void onAlbumImgClick(BaseImage albumImg);

        void onAlbumImgDeleteClick(BaseImage albumImg);

        void onAlbumImgLikeClick(BaseImage albumImg);

        void onAlbumImgCommentClick(BaseImage albumImg);

        void onAlbumImgSaveClick(BaseImage albumImg);

        void onAlbumImgFollowClick(BaseImage albumImg);

        void onAlbumImgHeaderClick(BaseImage albumImg);


    }
}
