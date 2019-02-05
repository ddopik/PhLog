package com.example.softmills.phlog.ui.earning.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.softmills.phlog.R;
import com.example.softmills.phlog.base.BaseFragment;
import com.example.softmills.phlog.ui.earning.model.Earning;
import com.example.softmills.phlog.ui.earning.presenter.EarningInnerPresenter;
import com.example.softmills.phlog.ui.earning.presenter.EarningInnerPresenterImpl;

/**
 * Created by abdalla_maged On Nov,2018
 */
public class EarningInnerFragment extends BaseFragment implements EarningInnerView {

    public String earningId;
    private View mainView;

    private TextView id, createdAt, by, exclusive, buyerName, buyerWebsite, buyerPhone, price;
    private ImageView image;

    private EarningInnerPresenter presenter;


    public static EarningInnerFragment getInstance(String earningId) {
        EarningInnerFragment fragment = new EarningInnerFragment();
        fragment.earningId = earningId;
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.fragment_earning_inner_2, container, false);
        return mainView;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
        initPresenter();
        presenter.getEarning(getContext(), earningId);
    }

    @Override
    protected void initPresenter() {
        presenter = new EarningInnerPresenterImpl();
        presenter.setView(this);
    }

    @Override
    protected void initViews() {
        id = mainView.findViewById(R.id.earning_id);
        createdAt = mainView.findViewById(R.id.earning_created_at);
        by = mainView.findViewById(R.id.created_by);
        exclusive = mainView.findViewById(R.id.earning_type);
        buyerName = mainView.findViewById(R.id.buyer_name);
        buyerWebsite = mainView.findViewById(R.id.buyer_mail);
        buyerPhone = mainView.findViewById(R.id.buyer_phone);
        price = mainView.findViewById(R.id.earning_price);
        image = mainView.findViewById(R.id.earning_image);
    }

    @Override
    public void setEarning(Earning data) {
        id.setText(getString(R.string.id_val, data.getId()));
        Glide.with(this)
                .load(data.getPhoto().url)
                .into(image);
        createdAt.setText(getString(R.string.created_at_val, data.getPhoto().createdAt));
//        by.setText(data.ph); // TODO: we should be saving our profile locally
        buyerName.setText(data.getBusiness().fullName);
        buyerWebsite.setText(data.getBusiness().email);
        buyerPhone.setText(data.getBusiness().phone);
        price.setText(String.format("%1$s $", data.getPrice()));
        if (data.getExclusive() == 1)
            exclusive.setVisibility(View.VISIBLE);
        else exclusive.setVisibility(View.INVISIBLE);
    }
}
