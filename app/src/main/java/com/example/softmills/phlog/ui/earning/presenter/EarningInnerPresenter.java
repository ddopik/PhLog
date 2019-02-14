package com.example.softmills.phlog.ui.earning.presenter;

import android.content.Context;

import com.example.softmills.phlog.ui.earning.view.EarningInnerView;

public interface EarningInnerPresenter {
    void setView(EarningInnerView view);

    void getEarningInner(Context context, String earningId);
}
