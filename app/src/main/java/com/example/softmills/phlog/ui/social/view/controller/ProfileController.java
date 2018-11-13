package com.example.softmills.phlog.ui.social.view.controller;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.example.softmills.phlog.R;
import com.example.softmills.phlog.base.widgets.CustomRecyclerView;
import com.example.softmills.phlog.ui.social.model.Entite;
import com.example.softmills.phlog.ui.social.view.adapter.SocialProfileImagesAdapter;

import java.util.List;

/**
 * Created by abdalla_maged on 11/13/2018.
 */
public class ProfileController {

    private LinearLayout adapterLis;
    private SocialProfileImagesAdapter socialProfileImagesAdapter;
    private Context context;

    public ProfileController(LinearLayout adapterLis, Context context) {
        this.adapterLis = adapterLis;
        this.context=context;
    }

    public void getSocialProfileView(List<Entite> entityList) {
        //todo displayType must be passed
        this.socialProfileImagesAdapter = new SocialProfileImagesAdapter(entityList,entityList.get(0).displayType  );
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View mainView = layoutInflater.inflate(R.layout.social_view_profile, adapterLis, true);
        CustomRecyclerView socialProfileRv = mainView.findViewById(R.id.social_profile_rv);
        socialProfileRv.setAdapter(socialProfileImagesAdapter);
    }
}
