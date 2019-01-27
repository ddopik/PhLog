package com.example.softmills.phlog.ui.social.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.softmills.phlog.R;
import com.example.softmills.phlog.base.BaseFragment;
import com.example.softmills.phlog.base.commonmodel.BaseImage;
import com.example.softmills.phlog.base.widgets.CustomRecyclerView;
import com.example.softmills.phlog.ui.album.view.AllAlbumImgActivity;
import com.example.softmills.phlog.ui.brand.view.BrandInnerActivity;
import com.example.softmills.phlog.ui.campaigns.inner.ui.CampaignInnerActivity;
import com.example.softmills.phlog.ui.search.view.SearchActivity;
import com.example.softmills.phlog.ui.social.model.Entite;
import com.example.softmills.phlog.ui.social.model.SocialData;
import com.example.softmills.phlog.ui.social.presenter.SocailFragmentPresenterImpl;
import com.example.softmills.phlog.ui.social.presenter.SocialFragmentPresenter;
import com.example.softmills.phlog.ui.social.view.adapter.SocialAdapter;
import com.example.softmills.phlog.ui.userprofile.view.UserProfileActivity;

import java.util.ArrayList;
import java.util.List;

import static com.example.softmills.phlog.ui.album.view.AllAlbumImgActivity.ALL_ALBUM_IMAGES;
import static com.example.softmills.phlog.ui.album.view.AllAlbumImgActivity.SELECTED_IMG_ID;
import static com.example.softmills.phlog.ui.userprofile.view.UserProfileActivity.USER_ID;

public class SocialFragment extends BaseFragment implements SocialFragmentView {

    private View mainView;
    private EditText homeSearch;
    private ProgressBar socialProgress;
    private CustomRecyclerView socailRv;
    private SocialFragmentPresenter socialFragmentPresenter;
    private SocialAdapter socialAdapter;
    private List<SocialData> socialDataList = new ArrayList<>();

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
        socailRv = mainView.findViewById(R.id.social_rv);
        socialProgress = mainView.findViewById(R.id.social_progress);


        this.socialAdapter = new SocialAdapter(socialDataList);
        socailRv.setAdapter(socialAdapter);


    }

    private void initListener(){
        homeSearch.setOnClickListener((v)->{
            Intent intent=new Intent(getActivity(), SearchActivity.class);
            startActivity(intent);
        });

        socialAdapter.onSocialItemListener = new SocialAdapter.OnSocialItemListener() {
            @Override
            public void onSocialProfileClick(Entite entite) {
                Intent intent = new Intent(getActivity(), UserProfileActivity.class);
                intent.putExtra(USER_ID, String.valueOf(entite.id));
                startActivity(intent);

            }

            @Override
            public void OnFollowSocialProfileClick(Entite entite) {
                socialFragmentPresenter.followUser(String.valueOf(entite.id));
            }

            @Override
            public void onSocialCampaignClicked(Entite entite) {
                Intent intent = new Intent(getActivity(), CampaignInnerActivity.class);
                intent.putExtra(CampaignInnerActivity.CAMPAIGN_ID,String.valueOf(entite.id));
                startActivity(intent);
            }

            @Override
            public void onSocialFollowCampaignClicked(Entite entite) {
                socialFragmentPresenter.joinSocialCampaign(String.valueOf(entite.id));
            }

            @Override
            public void onSocialSlideImageClicked(Entite entite) {
                Intent intent = new Intent(getActivity(), AllAlbumImgActivity.class);
                List<BaseImage> albumImgList = new ArrayList<>();
                for (int i = 0; i < entite.imgs.size(); i++) {
                    BaseImage albumImg = new BaseImage();
                    albumImg.url = entite.imgs.get(i);
                    albumImgList.add(albumImg);
                }
                intent.putParcelableArrayListExtra(ALL_ALBUM_IMAGES, (ArrayList<? extends Parcelable>) albumImgList);
                intent.putExtra(SELECTED_IMG_ID, entite.id);
                startActivity(intent);
            }

            @Override
            public void onSocialBrandClicked(Entite entite) {
                Intent intent = new Intent(getActivity(), BrandInnerActivity.class);
                intent.putExtra(BrandInnerActivity.BRAND_ID, String.valueOf(entite.id));
                startActivity(intent);
            }

            @Override
            public void onSocialBrandFollowClicked(Entite entite) {
                socialFragmentPresenter.followSocialBrand(String.valueOf(entite.id));
            }
        };


    }

    @Override
    public void viewSocialData(List<SocialData> socialDataList) {
        this.socialDataList.addAll(socialDataList);
        socialAdapter.notifyDataSetChanged();

    }

    @Override
    public void viewSocialDataProgress(boolean state) {
        if (state) {
            socialProgress.setVisibility(View.VISIBLE);
        } else {
            socialProgress.setVisibility(View.GONE);
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
