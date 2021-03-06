package com.example.softmills.phlog.ui.social.view.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.view.View;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.softmills.phlog.R;
import com.example.softmills.phlog.Utiltes.Constants;
import com.example.softmills.phlog.Utiltes.ErrorUtils;
import com.example.softmills.phlog.Utiltes.GlideApp;
import com.example.softmills.phlog.Utiltes.PrefUtils;
import com.example.softmills.phlog.base.commonmodel.Photographer;
import com.example.softmills.phlog.network.BaseNetworkApi;
import com.example.softmills.phlog.ui.MainActivity;
import com.example.softmills.phlog.ui.social.model.SocialData;
import com.example.softmills.phlog.ui.userprofile.view.UserProfileActivity;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.example.softmills.phlog.ui.userprofile.view.UserProfileActivity.USER_ID;

public class SocialAdapterProfileViewController {

    private String TAG = SocialAdapterProfileViewController.class.getSimpleName();
    private SocialAdapter socialAdapter;
    private Context context;
    private List<SocialData> socialDataList;

    public SocialAdapterProfileViewController(Context context, SocialAdapter socialAdapter, List<SocialData> socialDataList) {
        this.context = context;
        this.socialAdapter = socialAdapter;
        this.socialDataList = socialDataList;
    }


    @SuppressLint("CheckResult")
    public void setProfileType3(SocialData socialData, SocialAdapter.SocialViewHolder socialViewHolder, SocialAdapter.OnSocialItemListener onSocialItemListener) {
        socialViewHolder.socialProfileAlbumType3PhotosContainer.setBackground(context.getResources().getDrawable(R.drawable.default_user_profile));
        socialViewHolder.storyTitle.setText(socialData.title);
        socialViewHolder.socialProfileType3.setVisibility(View.VISIBLE);
        Photographer photographer = socialData.profiles.get(0);
        socialViewHolder.socialProfileType3FullName.setText(photographer.fullName);
        socialViewHolder.socialProfileType3UserName.setText(new StringBuilder().append("@").append(photographer.userName).toString());
//        getUserPhotos(photographer.id, socialData);

        GlideApp.with(context)
                .load(photographer.imageProfile)
                .placeholder(R.drawable.default_user_pic)
                .error(R.drawable.default_user_pic)
                .apply(RequestOptions.circleCropTransform())
                .into(socialViewHolder.socialProfileType3Icon);


        if (photographer.photos.size() >= 4) {
            socialViewHolder.socialProfileAlbumType3PhotosContainer.setBackgroundResource(0);
            GlideApp.with(context)
                    .load(photographer.photos.get(0) )
                    .centerCrop()
                    .placeholder(R.drawable.default_photographer_profile)
                    .error(R.drawable.default_photographer_profile)
                    .into(socialViewHolder.socialProfileType3Img_1);

            GlideApp.with(context)
                    .load(photographer.photos.get(1))
                    .placeholder(R.drawable.default_photographer_profile)
                    .centerCrop()
                    .error(R.drawable.default_photographer_profile)
                    .into(socialViewHolder.socialProfileType3Img_2);


            GlideApp.with(context)
                    .load(photographer.photos.get(2))
                    .centerCrop()
                    .placeholder(R.drawable.default_photographer_profile)
                    .error(R.drawable.default_photographer_profile)
                    .into(socialViewHolder.socialProfileType3Img_3);


            GlideApp.with(context)
                    .load(photographer.photos.get(3))
                    .error(R.drawable.default_photographer_profile)
                    .apply(new RequestOptions().centerCrop())
                    .into(socialViewHolder.socialProfileType3Img_4);


        } else if (photographer.imageCover != null) {


            GlideApp.with(context)
                    .load(photographer.imageCover)
                    .placeholder(R.drawable.default_place_holder)
                    .error(R.drawable.default_error_img)
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            // log exception
                            socialViewHolder.socialProfileAlbumType3PhotosContainer.setBackground(context.getResources().getDrawable(R.drawable.default_error_img));

                            return false; // important to return false so the error placeholder can be placed
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            socialViewHolder.socialProfileAlbumType3PhotosContainer.setBackground(resource);
                            return false;
                        }
                    });


        }


        socialViewHolder.followSocialProfileType3Btn.setLoading(false);
        if (photographer.isFollow)

        {
            socialViewHolder.followSocialProfileType3Btn.setText(context.getString(R.string.un_follow));
        } else

        {
            socialViewHolder.followSocialProfileType3Btn.setText(context.getResources().getString(R.string.follow));
        }

        socialViewHolder.followSocialProfileType3Btn.setOnClickListener(v ->

        {
            socialViewHolder.followSocialProfileType3Btn.setLoading(true);
            if (photographer.isFollow) {
                unFollowUser(photographer.id, socialData);
            } else {
                followUser(photographer.id, socialData);
            }
        });


        View.OnClickListener onProfileClickListener = v -> {
            if (photographer.id == Integer.parseInt(PrefUtils.getUserId(context))) {

                ((MainActivity) context).navigationManger.navigate(Constants.NavigationHelper.PROFILE);

            } else {


                Intent intent = new Intent(context, UserProfileActivity.class);
                intent.putExtra(USER_ID, String.valueOf(photographer.id));
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                context.startActivity(intent);
            }
        };

        socialViewHolder.socialProfileAlbumType3PhotosContainer.setOnClickListener(onProfileClickListener);
        socialViewHolder.socialProfileType3ItemHeader.setOnClickListener(onProfileClickListener);

    }


    @SuppressLint("CheckResult")

    private void followUser(int userId, SocialData socialData) {
        BaseNetworkApi.followUser(String.valueOf(userId))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    int index = socialDataList.indexOf(socialData);
                    socialAdapter.notifyItemChanged(index);
                })
                .subscribe(followUserResponse -> {
                    socialData.profiles.get(0).isFollow = true;
//                    onSocialItemListener.onSocialPhotoGrapherFollowed(Integer.parseInt(userId), true);
                }, throwable -> {
                    ErrorUtils.Companion.setError(context, TAG, throwable);
                });
    }

    @SuppressLint("CheckResult")
    private void unFollowUser(int userID, SocialData socialData) {
        BaseNetworkApi.unFollowUser(String.valueOf(userID))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    int index = socialDataList.indexOf(socialData);
                    socialAdapter.notifyItemChanged(index);
                })
                .subscribe(followUserResponse -> {
                    socialData.profiles.get(0).isFollow = false;
//                    onSocialItemListener.onSocialPhotoGrapherFollowed(Integer.parseInt(userID), false);
                }, throwable -> {
                    ErrorUtils.Companion.setError(context, TAG, throwable);
                });

    }

//    @SuppressLint("CheckResult")
//    private void getUserPhotos(int userId, SocialData socialData) {
//
//
//        BaseNetworkApi.getUserProfilePhotos(String.valueOf(userId), 0)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(userPhotosResponse -> {
//                            getUserIndex(userId)
//                                    .subscribeOn(Schedulers.io())
//                                    .observeOn(AndroidSchedulers.mainThread())
//                                    .subscribe(index -> {
//                                        socialData.profiles.get(0).photoGrapherPhotos = userPhotosResponse.data.data;
//                                        if (index > 0) {
//                                            socialDataList.set(index, socialData);
//                                            socialAdapter.notifyDataSetChanged();
//                                        }
//
//                                    }, throwable -> {
//                                        ErrorUtils.Companion.setError(context, TAG, throwable);
//                                    });
//
//                        }
//                        , throwable -> {
//                            ErrorUtils.Companion.setError(context, TAG, throwable);
//                        });
//    }


    private Observable<Integer> getUserIndex(int userId) {
        return Observable.fromCallable(() -> {

            for (int i = 0; i < socialDataList.size(); i++) {
                if (socialDataList.get(i).profiles != null && socialDataList.get(i).profiles.size() > 0)
                    for (Photographer photographer : socialDataList.get(i).profiles) {
                        if (photographer.id.equals(userId)) {
                            return i;
                        }
                    }
            }
            return -1;
        });

    }

}



