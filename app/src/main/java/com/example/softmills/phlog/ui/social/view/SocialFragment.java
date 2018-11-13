package com.example.softmills.phlog.ui.social.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.example.softmills.phlog.R;
import com.example.softmills.phlog.base.BaseFragment;
import com.example.softmills.phlog.ui.search.view.SearchActivity;
import com.example.softmills.phlog.ui.social.model.Entite;
import com.example.softmills.phlog.ui.social.model.SocialData;
import com.example.softmills.phlog.ui.social.presenter.SocailFragmentPresenterImpl;
import com.example.softmills.phlog.ui.social.presenter.SocialFragmentPresenter;
import com.example.softmills.phlog.ui.social.view.controller.ProfileController;

import java.util.List;

import static com.example.softmills.phlog.Utiltes.Constants.ENTITY_ALBUM;
import static com.example.softmills.phlog.Utiltes.Constants.ENTITY_BRAND;
import static com.example.softmills.phlog.Utiltes.Constants.ENTITY_CAMPAIGN;
import static com.example.softmills.phlog.Utiltes.Constants.ENTITY_IMAGE;
import static com.example.softmills.phlog.Utiltes.Constants.ENTITY_PROFILE;

public class SocialFragment extends BaseFragment implements SocialFragmentView {

    private View mainView;
    private EditText homeSearch;
    private ProgressBar socialProgress;
    private LinearLayout adapterList;
    private SocialFragmentPresenter socialFragmentPresenter;
    private ProfileController profileController;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mainView =inflater.inflate(R.layout.fragment_home,container,false);
        return mainView;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initPresenter();
        initViews();
        initListener();
        socialFragmentPresenter.getSocialData();




    }


    @Override
    protected void initPresenter() {
        socialFragmentPresenter = new SocailFragmentPresenterImpl(getContext(), this);
    }

    @Override
    protected void initViews() {
        homeSearch=mainView.findViewById(R.id.home_search);
        adapterList = mainView.findViewById(R.id.adapter_list);
        socialProgress = mainView.findViewById(R.id.social_progress);

//        getLayoutInflater().inflate(R.layout.social_view_album, adapterList, true);


        profileController = new ProfileController(adapterList,getContext());


    }

    private void initListener(){
        homeSearch.setOnClickListener((v)->{
            Intent intent=new Intent(getActivity(), SearchActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public void viewSocialData(List<SocialData> socialDataList) {
        prepareCategoryList(socialDataList);
    }

    @Override
    public void viewSocialDataProgress(boolean state) {
        if (state) {
            socialProgress.setVisibility(View.VISIBLE);
        } else {
            socialProgress.setVisibility(View.GONE);
        }
    }


    private void prepareCategoryList(List<SocialData> socialDataList) {

        for (int i = 0; i < socialDataList.size(); i++) {

            List<Entite> entityList = socialDataList.get(i).entites;
            switch (socialDataList.get(i).entityId) {

                case ENTITY_PROFILE: {
                    profileController.getSocialProfileView(entityList);

                }
                case ENTITY_CAMPAIGN: {

                }
                case ENTITY_ALBUM: {

                }
                case ENTITY_IMAGE: {

                }
                case ENTITY_BRAND: {

                }

            }


        }
    }

    @Override
    public void showMessage(String msg) {
        showToast(msg);
    }

    @Override
    public void showToast(String msg) {
        super.showToast(msg);
    }

}
