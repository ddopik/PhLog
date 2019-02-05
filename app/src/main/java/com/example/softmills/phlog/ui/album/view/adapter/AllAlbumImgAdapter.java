package com.example.softmills.phlog.ui.album.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.softmills.phlog.R;
import com.example.softmills.phlog.Utiltes.GlideApp;
import com.example.softmills.phlog.base.commonmodel.BaseImage;
import com.example.softmills.phlog.base.commonmodel.Tag;

import java.util.List;

/**
 * Created by abdalla_maged on 11/5/2018.
 */
public class AllAlbumImgAdapter extends RecyclerView.Adapter<AllAlbumImgAdapter.AlbumImgViewHolder> {

    private List<BaseImage> albumImgList;
    private Context context;
    public OnAlbumImgClicked onAlbumImgClicked;

    public AllAlbumImgAdapter(List<BaseImage> albumImgList) {
        this.albumImgList = albumImgList;
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
        if (albumImgList.get(i).tags !=null) {
            for (Tag tag : albumImgList.get(i).tags) {
                tagS = tagS + " #" + tag.name;
            }
        }
        albumImgViewHolder.imageCommentTagVal.setText(tagS);
        if(albumImgList.get(i).thumbnailUrl !=null)
        albumImgViewHolder.albumName.setText(albumImgList.get(i).thumbnailUrl);
        if(albumImgList.get(i).photographer !=null)
        albumImgViewHolder.albumAuthor.setText(albumImgList.get(i).photographer.fullName);
        if(albumImgList.get(i).likesCount !=null)
        albumImgViewHolder.albumImgLikeVal.setText(new StringBuilder().append("number of likes here").append(" Likes").toString());
        if(albumImgList.get(i).commentsCount !=null)
        albumImgViewHolder.albumImgCommentVal.setText(new StringBuilder().append("comment_count_here").append("Comments").toString());

        if (onAlbumImgClicked != null) {
            albumImgViewHolder.albumImg.setOnClickListener(v -> onAlbumImgClicked.onAlbumImgClick(albumImgList.get(i)));
            albumImgViewHolder.albumImgLike.setOnClickListener(v -> onAlbumImgClicked.onAlbumImgLikeClick(albumImgList.get(i)));
            albumImgViewHolder.albumImgComment.setOnClickListener(v -> onAlbumImgClicked.onAlbumImgCommentClick(albumImgList.get(i)));
            albumImgViewHolder.albumImgDownload.setOnClickListener(v -> onAlbumImgClicked.onAlbumImgDownloadClick(albumImgList.get(i)));
            albumImgViewHolder.albumImgLikeVal.setOnClickListener(v -> onAlbumImgClicked.onAlbumImgLikeClick(albumImgList.get(i)));
            albumImgViewHolder.albumImgCommentVal.setOnClickListener(v -> onAlbumImgClicked.onAlbumImgCommentClick(albumImgList.get(i)));
        }

    }

    @Override
    public int getItemCount() {
        return albumImgList.size();
    }

    public class AlbumImgViewHolder extends RecyclerView.ViewHolder {

        ImageView albumIcon, albumImg;
        TextView albumName, albumAuthor, imageCommentTagVal, albumImgLikeVal, albumImgCommentVal;
        ImageButton albumImgLike, albumImgComment, albumImgDownload;

        AlbumImgViewHolder(View view) {
            super(view);
            albumIcon = view.findViewById(R.id.album_icon);
            albumImg = view.findViewById(R.id.album_img);
            albumName = view.findViewById(R.id.album_name);
            imageCommentTagVal = view.findViewById(R.id.image_comment_tag_val);
            albumAuthor = view.findViewById(R.id.album_author);
            albumImgLikeVal = view.findViewById(R.id.album_img_like_count);
            albumImgCommentVal = view.findViewById(R.id.album_img_comment_count);
            albumImgLike = view.findViewById(R.id.album_img_like_btn);
            albumImgComment = view.findViewById(R.id.album_img_comment);
            albumImgDownload = view.findViewById(R.id.album_img_download);
        }
    }

    public interface OnAlbumImgClicked {
        void onAlbumImgClick(BaseImage albumImg);

        void onAlbumImgLikeClick(BaseImage albumImg);

        void onAlbumImgCommentClick(BaseImage albumImg);

        void onAlbumImgDownloadClick(BaseImage albumImg);


    }
}
