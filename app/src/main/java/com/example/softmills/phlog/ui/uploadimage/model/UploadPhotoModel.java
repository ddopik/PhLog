package com.example.softmills.phlog.ui.uploadimage.model;

import com.example.softmills.phlog.base.commonmodel.UploadImageType;

import java.util.Map;

public class UploadPhotoModel {
    public String imagePath;
    public String imageCaption;
    public String location;
    public String draftState;
    public UploadImageType uploadImageType;
    public Map<String, String> tags;
}
