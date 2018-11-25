package com.example.softmills.phlog.ui.earning.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.softmills.phlog.R;
import com.example.softmills.phlog.Utiltes.GlideApp;
import com.example.softmills.phlog.ui.earning.model.Earning;

import java.util.List;

/**
 * Created by abdalla_maged On Nov,2018
 */
public class EarningListAdapter extends RecyclerView.Adapter<EarningListAdapter.EarningListViewHolder> {
    private Context context;
    private List<Earning> earningList;
    public OnEarningClick onEarningClick;

    public EarningListAdapter(List<Earning> earningList) {
        this.earningList = earningList;
    }

    @NonNull
    @Override
    public EarningListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        return new EarningListViewHolder(layoutInflater.inflate(R.layout.view_holder_earning, viewGroup, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull EarningListViewHolder earningListViewHolder, int i) {

        Earning earning = earningList.get(i);
        GlideApp.with(context).load(earning.earningImg)
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(14)))
                .into(earningListViewHolder.earningImg);
        earningListViewHolder.earningPrice.setText("$" + earning.earningPrice);
        earningListViewHolder.earningBuyer.setText(earning.earningBuyer);
        earningListViewHolder.earningSize.setText(earning.earningSize);
        if (onEarningClick != null) {
            earningListViewHolder.earningContainer.setOnClickListener(view -> {
                onEarningClick.OnEarningClickListener(earning);
            });
        }


    }

    @Override
    public int getItemCount() {
        return earningList.size();
    }

    class EarningListViewHolder extends RecyclerView.ViewHolder {

        LinearLayout earningContainer;
        TextView earningPrice, earningBuyer, earningSize;
        ImageView earningImg;

        EarningListViewHolder(View view) {
            super(view);
            earningContainer = view.findViewById(R.id.earning_container);
            earningImg = view.findViewById(R.id.earning_img);
            earningPrice = view.findViewById(R.id.earning_price);
            earningBuyer = view.findViewById(R.id.earning_buyer);
            earningSize = view.findViewById(R.id.earning_size);

        }
    }

    interface OnEarningClick {
        void OnEarningClickListener(Earning earning);
    }
}
