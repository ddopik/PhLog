package com.example.softmills.phlog.ui.photographerprofile.view.ph_follow.following.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.request.RequestOptions;
import com.example.softmills.phlog.R;
import com.example.softmills.phlog.Utiltes.GlideApp;
import com.example.softmills.phlog.base.commonmodel.Photographer;

import java.util.List;

/**
 * Created by abdalla_maged on 10/11/2018.
 */
public class PhotoGrapherFollowingAdapter extends RecyclerView.Adapter<PhotoGrapherFollowingAdapter.PhotoGrapherFollowingInViewHolder> {


    private String TAG = PhotoGrapherFollowingAdapter.class.getSimpleName();
    public Context context;
    private List<Photographer> photoGrapherFollowingInList;
    public FollowingAdapterListener followingAdapterListener;

    public PhotoGrapherFollowingAdapter(Context context, List<Photographer> photoGrapherFollowingInList) {
        this.context = context;
        this.photoGrapherFollowingInList = photoGrapherFollowingInList;
    }

    @NonNull
    @Override
    public PhotoGrapherFollowingInViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        return new PhotoGrapherFollowingInViewHolder(layoutInflater.inflate(R.layout.view_holder_profile_search_2, viewGroup, false));

    }

    @Override
    public void onBindViewHolder(@NonNull PhotoGrapherFollowingInViewHolder viewHolder, int i) {


        try {
            GlideApp.with(context).load(photoGrapherFollowingInList
                    .get(i).imageProfile)
                    .placeholder(R.drawable.default_place_holder)
                    .error(R.drawable.default_error_img)
                    .apply(RequestOptions.circleCropTransform())
                    .into(viewHolder.profileImg);
            viewHolder.profileFullName.setText(photoGrapherFollowingInList.get(i).fullName);
            viewHolder.profileUserName.setText(photoGrapherFollowingInList.get(i).userName);
            viewHolder.profileFollowersCount.setText(new StringBuilder().append(photoGrapherFollowingInList.get(i).followersCount).append(" ").append(context.getResources().getString(R.string.followers)).toString());
            viewHolder.profileCountFollowingCount.setText(new StringBuilder().append(photoGrapherFollowingInList.get(i).followingCount).append(" ").append(context.getResources().getString(R.string.following)).toString());
            viewHolder.photosCount.setText(new StringBuilder().append(photoGrapherFollowingInList.get(i).photosCount).append(" ").append(context.getResources().getString(R.string.photos)).toString());
            viewHolder.profileContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (followingAdapterListener != null) {
                        followingAdapterListener.onFollowingsSelected(photoGrapherFollowingInList.get(i));
                    }
                }
            });
        } catch (Exception e) {
            Log.e(TAG, "onBindViewHolder () Error--->" + e.getMessage());

        }

    }


    @Override
    public int getItemCount() {
        return photoGrapherFollowingInList.size();
    }

    //
    public class PhotoGrapherFollowingInViewHolder extends RecyclerView.ViewHolder {

        private ConstraintLayout profileContainer;
        private ImageView profileImg;
        private TextView profileFullName, profileUserName, profileFollowersCount, profileCountFollowingCount, photosCount;

        public PhotoGrapherFollowingInViewHolder(View view) {
            super(view);
            profileContainer = view.findViewById(R.id.profile_container);
            profileImg = view.findViewById(R.id.profile_img);
            profileFullName = view.findViewById(R.id.profile_full_name);
            profileUserName = view.findViewById(R.id.profile_user_name);
            profileCountFollowingCount = view.findViewById(R.id.profile_following_count);
            profileFollowersCount = view.findViewById(R.id.profile_followers_count);
            photosCount = view.findViewById(R.id.profile_photos_count);
        }
    }


    public interface FollowingAdapterListener {
        void onFollowingsSelected(Photographer photoGrapherFollowingObj);
    }
}
