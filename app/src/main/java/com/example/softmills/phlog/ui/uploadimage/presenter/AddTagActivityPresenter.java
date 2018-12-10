package com.example.softmills.phlog.ui.uploadimage.presenter;

import com.example.softmills.phlog.base.commonmodel.Tags;

import java.util.HashMap;
import java.util.List;

/**
 * Created by abdalla_maged on 10/28/2018.
 */
public interface AddTagActivityPresenter {


    void uploadPhoto(String imagePath,String imageCaption,String location,String draftState, HashMap<String, String> imageType, List<Tags> tagsList);
}
