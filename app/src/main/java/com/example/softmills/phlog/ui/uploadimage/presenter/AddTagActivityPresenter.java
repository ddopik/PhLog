package com.example.softmills.phlog.ui.uploadimage.presenter;

import com.example.softmills.phlog.base.commonmodel.Tag;
import com.example.softmills.phlog.base.commonmodel.UploadImageType;
import com.example.softmills.phlog.ui.uploadimage.model.UploadPhotoModel;

import java.util.List;

/**
 * Created by abdalla_maged on 10/28/2018.
 */
public interface AddTagActivityPresenter {


    void uploadPhoto(String imagePath, String imageCaption, String location, String draftState, UploadImageType imageType, List<Tag> tagList);
    void getAutoCompleteTags(String key);

    UploadPhotoModel getUploadModel(String imagePreviewPath, String imageCaption, String imageLocation, String draftState, UploadImageType imageType, List<Tag> tagList);
}
