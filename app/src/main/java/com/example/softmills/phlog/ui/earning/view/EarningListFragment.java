package com.example.softmills.phlog.ui.earning.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.softmills.phlog.R;
import com.example.softmills.phlog.base.BaseFragment;
import com.example.softmills.phlog.base.widgets.CustomRecyclerView;
import com.example.softmills.phlog.base.widgets.PagingController;
import com.example.softmills.phlog.ui.MainActivity;
import com.example.softmills.phlog.ui.earning.model.Earning;
import com.example.softmills.phlog.ui.earning.presenter.EarningListPresenter;
import com.example.softmills.phlog.ui.earning.presenter.EarningListPresenterImpl;

import java.util.ArrayList;
import java.util.List;

import static com.example.softmills.phlog.Utiltes.Constants.NavigationHelper.EARNING_INNER;

/**
 * Created by abdalla_maged On Nov,2018
 */
public class EarningListFragment extends BaseFragment implements EarningListFragmentView {

    //    EarningListAdapter
    private View mainView;
    private TextView earningListSize;
    private EarningListAdapter earningListAdapter;
    private List<Earning> earningList = new ArrayList<>();
    private CustomRecyclerView earningListRv;
    private ProgressBar earningListProgress;
    private EarningListPresenter earningListPresenter;
    private PagingController pagingController;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.fragment_earning_2, container, false);
        return mainView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initPresenter();
        initViews();
        initListener();
        if (earningList.isEmpty())
        earningListPresenter.getEarningList(getContext(), "1");
    }


    @Override
    protected void initPresenter() {
        earningListPresenter = new EarningListPresenterImpl(this, getContext());
    }

    @Override
    protected void initViews() {

        earningListSize = mainView.findViewById(R.id.total_size_val);
        earningListRv = mainView.findViewById(R.id.earning_list_rv);
        earningListProgress = mainView.findViewById(R.id.earning_list_progress);
        earningListAdapter = new EarningListAdapter(earningList);
        earningListRv.setAdapter(earningListAdapter);
    }

    private void initListener() {
        earningListAdapter.onEarningClick = earning -> {
            MainActivity.navigationManger.setExtraData(String.valueOf(earning.getId()));
            MainActivity.navigationManger.navigate(EARNING_INNER);
        };
        pagingController = new PagingController(earningListRv) {
            @Override
            public void getPagingControllerCallBack(int page) {
                earningListPresenter.getEarningList(getContext(), String.valueOf(page));
            }
        };
    }

    @Override
    public void viewEarningList(List<Earning> earningList) {
        this.earningList.addAll(earningList);
        earningListAdapter.notifyDataSetChanged();

    }

    @Override
    public void viewEaringListProgress(boolean state) {
        if (state) {
            earningListProgress.setVisibility(View.VISIBLE);
        } else {
            earningListProgress.setVisibility(View.GONE);
        }
    }

    @Override
    public void viewMessage(String msg) {
        showToast(msg);
    }

    @Override
    public void setSalesNumber(int total) {
        if (total != 0) {
            earningListSize.setText(getString(R.string.total_sales_number, total));
        } else {
            earningListSize.setText(getString(R.string.no_sales_yet));
        }
    }
}
