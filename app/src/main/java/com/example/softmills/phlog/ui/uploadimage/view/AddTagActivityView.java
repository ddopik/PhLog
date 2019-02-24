package com.example.softmills.phlog.ui.uploadimage.view;

import com.example.softmills.phlog.base.BaseView;
import com.example.softmills.phlog.base.commonmodel.Tag;

import java.util.List;

/**
 * Created by abdalla_maged on 10/28/2018.
 */
public interface AddTagActivityView  {

    void viewUploadProgress(boolean state);

    void viewMessage(String msg);

    void updateTagsList(List<Tag> tagList);

}
