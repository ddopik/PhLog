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

import com.bumptech.glide.request.RequestOptions;
import com.example.softmills.phlog.R;
import com.example.softmills.phlog.Utiltes.GlideApp;
import com.example.softmills.phlog.ui.search.view.brand.model.BrandSearch;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.annotations.NonNull;

/**
 * Created by abdalla_maged on 10/31/2018.
 */
public class BrandSearchAdapter extends RecyclerView.Adapter<BrandSearchAdapter.BrandSearchViewHolder> implements Filterable {


    private String TAG = BrandSearchAdapter.class.getSimpleName();
    public Context context;
    private List<BrandSearch> brandList;
    private List<BrandSearch> brandFiltered;
    public BrandAdapterListener brandAdapterListener;

    public BrandSearchAdapter(Context context, List<BrandSearch> brandList) {
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
            GlideApp.with(context)
                    .load(brandList.get(i).thumbnail).apply(RequestOptions.circleCropTransform())
                    .centerCrop()
                    .into(brandSearchViewHolder.brandIconImg);

            GlideApp.with(context)
                    .load(brandList.get(i).coverImage)
                    .centerCrop()
                    .into(brandSearchViewHolder.brandIconImg);

            brandSearchViewHolder.brandName.setText(brandList.get(i).nameEn);
            brandSearchViewHolder.brandFollowers.setText(brandList.get(i).numberOfFollowers);
            brandSearchViewHolder.searchBrandContainer.setOnClickListener(v -> {
                if (brandAdapterListener != null) {
                    brandAdapterListener.onBrandSelected(brandFiltered.get(i));
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
                    List<BrandSearch> filteredList = new ArrayList<>();
                    for (BrandSearch row : brandList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.nameEn.toLowerCase().contains(charString.toLowerCase()) ) {
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
                brandFiltered = (ArrayList<BrandSearch>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }


    public interface BrandAdapterListener {
        void onBrandSelected(BrandSearch brandSearch);
    }
}