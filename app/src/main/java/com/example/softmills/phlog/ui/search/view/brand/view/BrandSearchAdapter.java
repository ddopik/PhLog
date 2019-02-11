package com.example.softmills.phlog.ui.search.view.brand.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
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

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by abdalla_maged on 10/31/2018.
 */
public class BrandSearchAdapter extends RecyclerView.Adapter<BrandSearchAdapter.BrandSearchViewHolder> implements Filterable {


    private String TAG = BrandSearchAdapter.class.getSimpleName();
    public Context context;
    private List<Business> brandList;
    private List<Business> brandFiltered;
    public BrandAdapterListener brandAdapterListener;

    public BrandSearchAdapter(Context context, List<Business> brandList) {
        this.context = context;
        this.brandList = brandList;
        this.brandFiltered = brandList;
    }

    @NonNull
    @Override
    public BrandSearchViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        return new BrandSearchViewHolder(layoutInflater.inflate(R.layout.view_holder_brand, viewGroup, false));

    }

    @Override
    public void onBindViewHolder(@NonNull BrandSearchViewHolder brandSearchViewHolder, int i) {


        try {

            if(brandList.get(i).isFollow){
                brandSearchViewHolder.brandFollowBtn.setText(context.getResources().getString(R.string.following));
            }else {
                brandSearchViewHolder.brandFollowBtn.setText(context.getResources().getString(R.string.follow));
            }
            GlideApp.with(context)
                    .load(brandList.get(i).thumbnail)
                    .placeholder(R.drawable.default_place_holder)
                    .error(R.drawable.default_place_holder)
                    .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                    .into(brandSearchViewHolder.brandIconImg);


            GlideApp .with(context)
                    .load(brandList.get(i).imageCover)
                    .placeholder(R.drawable.default_place_holder)
                    .error(R.drawable.default_place_holder)
                    .centerCrop()
                    .into(brandSearchViewHolder.brandImg);

            brandSearchViewHolder.brandName.setText(brandList.get(i).fullName);
            brandSearchViewHolder.brandFollowers.setText(new StringBuilder().append(brandList.get(i).followersCount).append(" ").append(context.getResources().getString(R.string.following)).toString());
            brandSearchViewHolder.searchBrandContainer.setOnClickListener(v -> {
                if (brandAdapterListener != null) {
                    brandAdapterListener.onBrandSelected(brandFiltered.get(i));
                }
            });

            brandSearchViewHolder.brandFollowBtn.setOnClickListener(v -> {
                if (brandList.get(i).isFollow) {
                    BaseNetworkApi.unFollowBrand(String.valueOf(brandList.get(i).id))
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(followBrandResponse -> {
                                brandList.get(i).isFollow = false;
                                brandSearchViewHolder.brandFollowBtn.setText(context.getResources().getString(R.string.follow));
                            }, throwable -> {
                                ErrorUtils.Companion.setError(context, TAG, throwable);
                            });
                } else {
                    BaseNetworkApi.followBrand(String.valueOf(brandList.get(i).id))
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(followBrandResponse -> {
                                brandList.get(i).isFollow = true;
                                brandSearchViewHolder.brandFollowBtn.setText(context.getResources().getString(R.string.following));
                            }, throwable -> {
                                ErrorUtils.Companion.setError(context, TAG, throwable);
                            });
                }
            });

        } catch (Exception e) {
            Log.e(TAG, "onBindViewHolder () Error--->" + e.getMessage());

        }

    }


    @Override
    public int getItemCount() {
        return brandFiltered.size();
    }

    //
    public class BrandSearchViewHolder extends RecyclerView.ViewHolder {

        LinearLayout searchBrandContainer;
        ImageView brandIconImg,brandImg;
        TextView brandName,brandFollowers;
        Button brandFollowBtn;

        public BrandSearchViewHolder(View view) {
            super(view);
            searchBrandContainer=view.findViewById(R.id.search_brand_container);
            brandIconImg=view.findViewById(R.id.brand_search_icon_img);
            brandImg=view.findViewById(R.id.brand_search_img);
            brandName=view.findViewById(R.id.brand_search_name);
            brandFollowers=view.findViewById(R.id.brand_search_following);
            brandFollowBtn=view.findViewById(R.id.follow_brand);
        }
    }


    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    brandFiltered = brandList;
                } else {
                    List<Business> filteredList = new ArrayList<>();
                    for (Business row : brandList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.fullName.toLowerCase().contains(charString.toLowerCase()) ) {
                            filteredList.add(row);
                        }
                    }
                    brandFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = brandFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                brandFiltered = (ArrayList<Business>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }


    public interface BrandAdapterListener {
        void onBrandSelected(Business brandSearch);
    }
}