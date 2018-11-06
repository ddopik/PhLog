package com.example.softmills.phlog.ui.album.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by abdalla_maged on 11/5/2018.
 */
public class AlbumImg {

    public String AlbumIcon;
    @SerializedName("image")
    @Expose
    public String AlbumImg;
    public String albumName;
    public String albumAuthorName;
    public String AlbumImgLikesCount;
    public String AlbumImgCommentsCount;

}
