package com.example.softmills.phlog.ui.commentimage.model;


import com.example.softmills.phlog.base.commonmodel.Comment;
import com.example.softmills.phlog.base.commonmodel.Mentions;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by abdalla_maged On Dec,2018
 */
public class SubmitImageCommentData {
    @SerializedName("comment")
    @Expose
    public Comment comment;
    @SerializedName("mentions")
    @Expose
    public Mentions mentions;
}
