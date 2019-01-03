package com.example.softmills.phlog.ui.album.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AlbumPreviewImagesResponse {

    @SerializedName("data")
    @Expose
    public AlbumPreviewImagesData data;
}
