package com.example.softmills.phlog.ui.earning.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.softmills.phlog.R;
import com.example.softmills.phlog.Utiltes.GlideApp;
import com.example.softmills.phlog.base.BaseFragment;
import com.example.softmills.phlog.base.widgets.CustomTextView;
import com.example.softmills.phlog.ui.earning.model.Earning;
import com.example.softmills.phlog.ui.earning.presenter.EarningInnerPresenter;
import com.example.softmills.phlog.ui.earning.presenter.EarningInnerPresenterImpl;

/**
 * Created by abdalla_maged On Nov,2018
 */
public class EarningInnerFragment extends BaseFragment implements EarningInnerView {

    public String earningId;
    private View mainView;

    private CustomTextView id, createdAt, by, exclusive, buyerName, buyerWebsite, buyerPhone, price;
    private ImageView buyerImage, earningItemImg;

    private EarningInnerPresenter presenter;


    public static EarningInnerFragment getInstance(String earningId) {
        EarningInnerFragment fragment = new EarningInnerFragment();
        fragment.earningId = earningId;
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.fragment_earning_inner, container, false);
        return mainView;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initPresenter();
        initViews();
        presenter.getEarningInner(getContext(), earningId);
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
        buyerImage = mainView.findViewById(R.id.earning_buyer_image);
        earningItemImg = mainView.findViewById(R.id.earning_item_img);
    }

    @Override
    public void viewEarningDetails(Earning earning) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions.transforms(new CenterCrop(), new RoundedCorners(16));

        id.setText(getString(R.string.id_val, earning.id));


        GlideApp.with(this)
                .load(earning.photo.url)
                .placeholder(R.drawable.default_place_holder)
                .error(R.drawable.default_error_img)
                .apply(requestOptions)
                .into(earningItemImg);


        GlideApp.with(this)
                .load(earning.business.thumbnail)
                .placeholder(R.drawable.default_place_holder)
                .error(R.drawable.default_error_img)


                .into(buyerImage);
        createdAt.setText(getString(R.string.created_at_val, earning.createdAt));
//        by.setText(data.ph); // TODO: we should be saving our profile locally
        buyerName.setText(earning.business.fullName);
        buyerWebsite.setText(earning.business.email);
        buyerPhone.setText(earning.business.phone);
        price.setText(String.format("%1$s $", earning.price));
        if (earning.exclusive == 1)
            exclusive.setVisibility(View.VISIBLE);
        else exclusive.setVisibility(View.INVISIBLE);
    }
}
