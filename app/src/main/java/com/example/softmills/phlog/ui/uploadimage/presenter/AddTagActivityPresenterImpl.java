package com.example.softmills.phlog.ui.uploadimage.presenter;

import android.content.Context;

import com.example.softmills.phlog.base.commonmodel.Tag;
import com.example.softmills.phlog.ui.uploadimage.view.AddTagActivityView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by abdalla_maged on 10/28/2018.
 */
public class AddTagActivityPresenterImpl implements AddTagActivityPresenter {
    private Context context;
    private AddTagActivityView addTagActivityView;

    public AddTagActivityPresenterImpl(Context context,AddTagActivityView addTagActivityView){
        this.context=context;
        this.addTagActivityView=addTagActivityView;
    }

    @Override
    public void uploadPhoto(String imagePath, HashMap<String, String> imageType, List<Tag> tagList) {
        String x=imagePath;
        HashMap j=imageType;
        List<Tag> list=tagList;

        int xx=9;

    }
}
