package com.example.softmills.phlog.ui.album.model;

import com.example.softmills.phlog.base.commonmodel.BaseImage;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by abdalla_maged on 11/6/2018.
 */
public class AlbumPreviewResponseData {

    @SerializedName("preview")
    public String preview;
    
    @SerializedName("updated_at")
    public String updatedAt;

    @SerializedName("name")
    public String name;

    @SerializedName("created_at")
    public String createdAt;

    @SerializedName("id")
    public int id;

    @SerializedName("photos_count")
    public int photosCount;
}
