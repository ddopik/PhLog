package com.example.softmills.phlog.ui.social.view.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.bumptech.glide.request.RequestOptions;
import com.example.softmills.phlog.R;
import com.example.softmills.phlog.Utiltes.ErrorUtils;
import com.example.softmills.phlog.Utiltes.GlideApp;
import com.example.softmills.phlog.Utiltes.PrefUtils;
import com.example.softmills.phlog.base.commonmodel.Photographer;
import com.example.softmills.phlog.network.BaseNetworkApi;
import com.example.softmills.phlog.ui.userprofile.view.UserProfileActivity;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.example.softmills.phlog.ui.userprofile.view.UserProfileActivity.USER_ID;

public class SocialAdapterProfileViewController {

    private String TAG = SocialAdapterProfileViewController.class.getSimpleName();
    private Context context;

    public SocialAdapterProfileViewController(Context context){
        this.context=context;
    }


    @SuppressLint("CheckResult")
    public void setProfileType3(Photographer photographer, SocialAdapter.SocialViewHolder socialViewHolder, OnSocialAdapterProfileViewListener onSocialAdapterProfileViewListener) {

        socialViewHolder.socialProfileType3.setVisibility(View.VISIBLE);
        GlideApp.with(context)
                .load(photographer.imageProfile)
                .placeholder(R.drawable.default_user_pic)
                .error(R.drawable.default_user_pic)
                .apply(RequestOptions.circleCropTransform())
                .into(socialViewHolder.socialProfileIcon);

        BaseNetworkApi.getUserProfilePhotos(PrefUtils.getUserToken(context), String.valueOf(photographer.id), 0)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(userPhotosResponse -> {

                            if (userPhotosResponse.data.data.size() > 0) {
                                GlideApp.with(context)
                                        .load(userPhotosResponse.data.data.get(0).url)
                                        .centerCrop()
                                        .placeholder(R.drawable.default_photographer_profile)
                                        .error(R.drawable.default_photographer_profile)
                                        .into(socialViewHolder.socialProfileImg_1);
                            } else {
                                socialViewHolder.socialProfileImg_1.setVisibility(View.INVISIBLE);
                            }

                            if (userPhotosResponse.data.data.size() > 1) {
                                GlideApp.with(context)
                                        .load(userPhotosResponse.data.data.get(1).url)
                                        .placeholder(R.drawable.default_photographer_profile)
                                        .centerCrop()
                                        .error(R.drawable.default_photographer_profile)
                                        .into(socialViewHolder.socialProfileImg_2);

                            } else {
                                socialViewHolder.socialProfileImg_1.setVisibility(View.INVISIBLE);
                            }

                            if (userPhotosResponse.data.data.size() > 2) {
                                GlideApp.with(context)
                                        .load(userPhotosResponse.data.data.get(2).url)
                                        .centerCrop()
                                        .placeholder(R.drawable.default_photographer_profile)
                                        .error(R.drawable.default_photographer_profile)
                                        .into(socialViewHolder.socialProfileImg_3);
                            } else {
                                socialViewHolder.socialProfileImg_1.setVisibility(View.INVISIBLE);
                            }

                            if (userPhotosResponse.data.data.size() > 3) {
                                GlideApp.with(context)
                                        .load(userPhotosResponse.data.data.get(3).url)
                                        .error(R.drawable.default_photographer_profile)
                                        .apply(new RequestOptions().centerCrop())
                                        .into(socialViewHolder.socialProfileImg_4);
                            } else {
                                socialViewHolder.socialProfileImg_1.setVisibility(View.INVISIBLE);
                            }


                        }
                        , throwable -> {
                            ErrorUtils.Companion.setError(context, TAG, throwable);
                        });


        socialViewHolder.socialProfileFullName.setText(photographer.fullName);
        socialViewHolder.socialProfileUserName.setText(photographer.userName);




        socialViewHolder.socialProfileContainer.setOnClickListener(v -> {
            Intent intent = new Intent(context, UserProfileActivity.class);
            intent.putExtra(USER_ID, String.valueOf(photographer.id));
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            context.startActivity(intent);
            });



        if (photographer.isFollow){
            socialViewHolder.followSocialProfile.setText(context.getResources().getString(R.string.following));
        }else {
            socialViewHolder.followSocialProfile.setText(context.getResources().getString(R.string.follow));
        }

        socialViewHolder.followSocialProfile.setOnClickListener(v -> {
            if (photographer.isFollow) {
                unFollowUser(String.valueOf(photographer.id), onSocialAdapterProfileViewListener);
            } else {
                followUser(String.valueOf(photographer.id), onSocialAdapterProfileViewListener);
            }
            });

    }

    @SuppressLint("CheckResult")

    public void followUser(String userId, OnSocialAdapterProfileViewListener onSocialAdapterProfileViewListener) {
        String x=PrefUtils.getUserToken(context) ;
        BaseNetworkApi.followUser(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(followUserResponse -> {
                    onSocialAdapterProfileViewListener.onSocialProfileFollowClick(true);
                }, throwable -> {
                    ErrorUtils.Companion.setError(context, TAG, throwable);
                });
    }

    @SuppressLint("CheckResult")
    public void unFollowUser(String userID, OnSocialAdapterProfileViewListener onSocialAdapterProfileViewListener) {
        BaseNetworkApi.unFollowUser(userID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(followUserResponse -> {
                    onSocialAdapterProfileViewListener.onSocialProfileFollowClick(false);
                }, throwable -> {
                    ErrorUtils.Companion.setError(context, TAG, throwable);
                });

    }

    public interface OnSocialAdapterProfileViewListener {
        void onSocialProfileFollowClick(boolean state);
    }
}
