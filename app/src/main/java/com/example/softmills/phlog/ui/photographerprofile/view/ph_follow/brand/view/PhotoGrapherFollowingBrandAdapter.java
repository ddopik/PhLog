package com.example.softmills.phlog.ui.photographerprofile.view.ph_follow.brand.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.softmills.phlog.R;
import com.example.softmills.phlog.Utiltes.ErrorUtils;
import com.example.softmills.phlog.Utiltes.GlideApp;
import com.example.softmills.phlog.base.commonmodel.Business;
import com.example.softmills.phlog.network.BaseNetworkApi;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by abdalla_maged on 11/11/2018.
 */
public class PhotoGrapherFollowingBrandAdapter extends RecyclerView.Adapter<PhotoGrapherFollowingBrandAdapter.BrandViewHolder> {

    private String TAG = PhotoGrapherFollowingBrandAdapter.class.getSimpleName();
    public Context context;
    private List<Business> brandList;
    //    private List<BrandSearch> brandFiltered;
    public BrandAdapterListener brandAdapterListener;

    public PhotoGrapherFollowingBrandAdapter(List<Business> brandList) {

        this.brandList = brandList;
//        this.brandFiltered = brandList;
    }

    @NonNull
    @Override
    public BrandViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        context = viewGroup.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        return new BrandViewHolder(layoutInflater.inflate(R.layout.view_holder_brand, viewGroup, false));

    }

    @Override
    public void onBindViewHolder(@NonNull BrandViewHolder brandViewHolder, int i) {

        if (brandList.get(i).isFollow) {
            brandViewHolder.brandFollowBtn.setText(context.getResources().getString(R.string.following));
        } else {
            brandViewHolder.brandFollowBtn.setText(context.getResources().getString(R.string.follow));
        }

        GlideApp.with(context)
                .load(brandList.get(i).thumbnail)
                .placeholder(R.drawable.default_place_holder)
                .error(R.drawable.default_error_img)
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .into(brandViewHolder.brandIconImg);


        GlideApp.with(context)
                .load(brandList.get(i).imageCover)
                .placeholder(R.drawable.default_place_holder)
                .error(R.drawable.default_error_img)
                .centerCrop()
                .into(brandViewHolder.brandImg);

        brandViewHolder.brandName.setText(brandList.get(i).fullName);
        brandViewHolder.brandFollowers.setText(new StringBuilder().append(brandList.get(i).followersCount).append(" ").append(context.getResources().getString(R.string.following)).toString());
        brandViewHolder.searchBrandContainer.setOnClickListener(v -> {
            if (brandAdapterListener != null) {
                brandAdapterListener.onBrandSelected(brandList.get(i));
            }
        });


        brandViewHolder.brandFollowBtn.setOnClickListener(v -> {
            if (brandList.get(i).isFollow) {
                BaseNetworkApi.unFollowBrand(String.valueOf(brandList.get(i).id))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(followBrandResponse -> {
                            brandList.get(i).isFollow = false;
                            brandViewHolder.brandFollowBtn.setText(context.getResources().getString(R.string.follow));
                        }, throwable -> {
                            ErrorUtils.Companion.setError(context, TAG, throwable);
                        });
            } else {
                BaseNetworkApi.followBrand(String.valueOf(brandList.get(i).id))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(followBrandResponse -> {
                            brandList.get(i).isFollow = true;
                            brandViewHolder.brandFollowBtn.setText(context.getResources().getString(R.string.following));
                        }, throwable -> {
                            ErrorUtils.Companion.setError(context, TAG, throwable);
                        });
            }
        });
    }


    @Override
    public int getItemCount() {
        return brandList.size();
    }

    //
    public class BrandViewHolder extends RecyclerView.ViewHolder {

        LinearLayout searchBrandContainer;
        ImageView brandIconImg, brandImg;
        TextView brandName, brandFollowers;
        Button brandFollowBtn;

        public BrandViewHolder(View view) {
            super(view);
            searchBrandContainer = view.findViewById(R.id.search_brand_container);
            brandIconImg = view.findViewById(R.id.brand_search_icon_img);
            brandImg = view.findViewById(R.id.brand_search_img);
            brandName = view.findViewById(R.id.brand_search_name);
            brandFollowers = view.findViewById(R.id.brand_search_following);
            brandFollowBtn = view.findViewById(R.id.follow_brand);
        }
    }


//    @Override
//    public Filter getFilter() {
//        return new Filter() {
//            @Override
//            protected FilterResults performFiltering(CharSequence charSequence) {
//                String charString = charSequence.toString();
//                if (charString.isEmpty()) {
//                    brandFiltered = brandList;
//                } else {
//                    List<BrandSearch> filteredList = new ArrayList<>();
//                    for (BrandSearch row : brandList) {
//
//                        // name match condition. this might differ depending on your requirement
//                        // here we are looking for name or phone number match
//                        if (row.nameEn.toLowerCase().contains(charString.toLowerCase()) ) {
//                            filteredList.add(row);
//                        }
//                    }
//                    brandFiltered = filteredList;
//                }
//
//                FilterResults filterResults = new FilterResults();
//                filterResults.values = brandFiltered;
//                return filterResults;
//            }
//
//            @Override
//            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
//                brandFiltered = (ArrayList<BrandSearch>) filterResults.values;
//                notifyDataSetChanged();
//            }
//        };
//    }


    public interface BrandAdapterListener {
        void onBrandSelected(Business brand);

    }
}
