package com.example.softmills.phlog.ui.album.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by abdalla_maged on 11/5/2018.
 */
public class AlbumImg implements Parcelable {

    public String albumImgId;
    public String AlbumIcon;
    @SerializedName("image")
    @Expose
    public String AlbumImg;
    public String albumName;
    public String albumAuthorName;
    public String AlbumImgLikesCount;
    public String AlbumImgCommentsCount;


    public AlbumImg(){

    }

    public void writeToParcel(Parcel out, int flags) {

        out.writeString(AlbumImg);
        out.writeString(albumName);
        out.writeString(albumAuthorName);
        out.writeString(AlbumImgLikesCount);
        out.writeString(AlbumImgCommentsCount);

    }

    public static final Parcelable.Creator<AlbumImg> CREATOR = new Parcelable.Creator<AlbumImg>() {
        public AlbumImg createFromParcel(Parcel in) {
            return new AlbumImg(in);
        }

        public AlbumImg[] newArray(int size) {
            return new AlbumImg[size];
        }
    };

    public int describeContents() {
        return 0;
    }

    public AlbumImg(Parcel in) {
        AlbumImg = in.readString();
        albumName = in.readString();
        albumAuthorName = in.readString();
        AlbumImgLikesCount = in.readString();
        AlbumImgCommentsCount = in.readString();


    }



}
