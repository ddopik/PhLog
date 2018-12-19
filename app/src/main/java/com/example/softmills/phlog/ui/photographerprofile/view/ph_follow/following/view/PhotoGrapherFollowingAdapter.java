package com.example.softmills.phlog.ui.photographerprofile.view.ph_follow.following.view;

import android.content.Context;
import android.support.annotation.NonNull;
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
        return new PhotoGrapherFollowingInViewHolder(layoutInflater.inflate(R.layout.view_holder_following, viewGroup, false));

    }

    @Override
    public void onBindViewHolder(@NonNull PhotoGrapherFollowingInViewHolder photoGrapherFollowingInViewHolder, int i) {


        try {
            GlideApp.with(context).load(photoGrapherFollowingInList
                    .get(i).imageProfile)
                    .placeholder(R.drawable.default_place_holder)
                    .error(R.drawable.default_error_img)
                    .apply(RequestOptions.circleCropTransform())
                    .into(photoGrapherFollowingInViewHolder.followersImg);
            photoGrapherFollowingInViewHolder.followersFullName.setText(photoGrapherFollowingInList.get(i).fullName);
            photoGrapherFollowingInViewHolder.followersUserName.setText(photoGrapherFollowingInList.get(i).userName);
            photoGrapherFollowingInViewHolder.followersCount.setText(new StringBuilder().append(photoGrapherFollowingInList.get(i).followingCount).append(" ").append(context.getResources().getString(R.string.followers)).toString());
            photoGrapherFollowingInViewHolder.followingCount.setText(new StringBuilder().append(photoGrapherFollowingInList.get(i).followingCount).append(" ").append(context.getResources().getString(R.string.following)).toString());
            photoGrapherFollowingInViewHolder.photosCount.setText(new StringBuilder().append(photoGrapherFollowingInList.get(i).photosCount).append(" ").append(context.getResources().getString(R.string.photos)).toString());
            photoGrapherFollowingInViewHolder.followingContainer.setOnClickListener(new View.OnClickListener() {
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

        private LinearLayout followingContainer;
        private ImageView followersImg;
        private TextView followersFullName, followersUserName, followersCount, followingCount, photosCount;

        public PhotoGrapherFollowingInViewHolder(View view) {
            super(view);
            followingContainer = view.findViewById(R.id.following_container);
            followersImg = view.findViewById(R.id.followers_img);
            followersFullName = view.findViewById(R.id.followers_full_name);
            followersUserName = view.findViewById(R.id.followers_user_name);
            followingCount = view.findViewById(R.id.following_count);
            followersCount = view.findViewById(R.id.followers_count);
            photosCount = view.findViewById(R.id.photos_count);
        }
    }


    public interface FollowingAdapterListener {
        void onFollowingsSelected(Photographer photoGrapherFollowingObj);
    }
}
