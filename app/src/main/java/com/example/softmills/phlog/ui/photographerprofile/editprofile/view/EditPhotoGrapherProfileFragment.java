package com.example.softmills.phlog.ui.photographerprofile.editprofile.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.softmills.phlog.R;
import com.example.softmills.phlog.Utiltes.Constants;
import com.example.softmills.phlog.Utiltes.GlideApp;
import com.example.softmills.phlog.base.BaseFragment;
import com.example.softmills.phlog.base.commonmodel.Photographer;
import com.example.softmills.phlog.ui.MainActivity;
import com.example.softmills.phlog.ui.photographerprofile.editprofile.presenter.EditPhotoGrapherProfileFragmentImpl;
import com.example.softmills.phlog.ui.photographerprofile.editprofile.presenter.EditPhotoGrapherProfileFragmentPresenter;

/**
 * Created by abdalla_maged On Nov,2018
 */
public class EditPhotoGrapherProfileFragment extends BaseFragment implements EditPhotoGrapherProfileFragmentView {
    private View mainView;
    private EditPhotoGrapherProfileFragmentPresenter editPhotoGrapherProfileFragmentPresenter;
    private EditText profileName, profileUserName, profileEmail, ProfilePassWord;
    private ImageView profileCoverImage;
    private ImageButton EditProfileBack;
    private Button editProfileSave;


    public static EditPhotoGrapherProfileFragment getInstance() {
        EditPhotoGrapherProfileFragment editPhotoGrapherProfileFragment = new EditPhotoGrapherProfileFragment();
        return editPhotoGrapherProfileFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.fragment_edit_photo_grapher_profile, container, false);
        return mainView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initPresenter();
        initViews();
        initListeners();
        editPhotoGrapherProfileFragmentPresenter.getProfileEditData();
    }

    @Override
    protected void initPresenter() {
        editPhotoGrapherProfileFragmentPresenter = new EditPhotoGrapherProfileFragmentImpl(this, getContext());
    }

    @Override
    protected void initViews() {
        profileName = mainView.findViewById(R.id.profile_edit_name);
        profileUserName = mainView.findViewById(R.id.profile_edit_user_name);
        profileEmail = mainView.findViewById(R.id.edit_profile_email);
        ProfilePassWord = mainView.findViewById(R.id.edit_profile_password);
        profileCoverImage=mainView.findViewById(R.id.user_profile_cover_img);
        editProfileSave =mainView.findViewById(R.id.save_profile_data_btn);
        EditProfileBack=mainView.findViewById(R.id.edit_profile_back_btn);
    }

    private void initListeners() {
        editProfileSave.setOnClickListener(v->{

        });
        EditProfileBack.setOnClickListener(v-> {
            MainActivity.navigationManger.navigate(Constants.NavigationHelper.PROFILE);
        });

    }

    @Override
    public void showPhotoGrapherProfileData(Photographer photographer) {
        profileName.setText(photographer.fullName);
        profileUserName.setText(photographer.userName);
        profileEmail.setText(photographer.email);
        ProfilePassWord.setText(photographer.password);
//        GlideApp.with(getContext())
//                .load(photographer.coverImage)
//                .error(R.drawable.default_error_img)
//                .placeholder(R.drawable.default_place_holder)
//                .into(profileCoverImage);


    }

    @Override
    public void viewEditProfileProgress(Boolean State) {

    }


}
