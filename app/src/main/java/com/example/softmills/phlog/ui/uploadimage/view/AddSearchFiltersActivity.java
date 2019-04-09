package com.example.softmills.phlog.ui.uploadimage.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ProgressBar;

import com.example.softmills.phlog.R;
import com.example.softmills.phlog.base.BaseActivity;
import com.example.softmills.phlog.base.commonmodel.UploadImageData;
import com.example.softmills.phlog.base.widgets.CustomRecyclerView;
import com.example.softmills.phlog.ui.search.view.album.model.Filter;
import com.example.softmills.phlog.ui.search.view.album.model.FilterOption;
import com.example.softmills.phlog.ui.search.view.album.view.ExpandableListAdapter;
import com.example.softmills.phlog.ui.uploadimage.presenter.AddSearchFiltersPresenter;
import com.example.softmills.phlog.ui.uploadimage.presenter.AddSearchFiltersPresenterImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.functions.Consumer;

public class AddSearchFiltersActivity extends BaseActivity implements AddSearchFiltersView {

    public final static String IMAGE_TYPE = "image_type";

    private ExpandableListAdapter adapter;
    private ExpandableListView filterExpandableListView;
    private ProgressBar loading;
    private Button nextButton;
    private List<Filter> filterList = new ArrayList<>();
    private UploadImageData uploadImageData;

    private AddSearchFiltersPresenter presenter;

    private ExpandableListAdapter.OnChildViewListener adapterListener = filterOption -> {
        for (int i = 0; i < filterList.size(); i++) {
            for (int x = 0; x < filterList.get(i).options.size(); x++) {
                FilterOption currFilterOption = filterList.get(i).options.get(x);
                if (currFilterOption.displayName.equals(filterOption.displayName)) {
                    if (currFilterOption.isSelected) {
                        filterList.get(i).options.get(x).isSelected = false;
                    } else {
                        filterList.get(i).options.get(x).isSelected = true;
                    }
                    adapter.notifyDataSetChanged();
                    break;
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_search_filters);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.getParcelable(IMAGE_TYPE) != null) {
            uploadImageData = bundle.getParcelable(IMAGE_TYPE);
            initPresenter();
            initView();
            initListeners();
            presenter.getSearchFilters(this, listConsumer);
        }
    }

    private Consumer<List<Filter>> listConsumer = filters -> {
        filterList.addAll(filters);
        adapter.notifyDataSetChanged();
    };

    private void initListeners() {
        nextButton.setOnClickListener(v -> {
            uploadImageData.setFilters(getFilter(filterList));
            Bundle extras = new Bundle();
            Intent intent = new Intent(this, AddTagActivity.class);
            extras.putParcelable(AddTagActivity.IMAGE_TYPE, uploadImageData);
            intent.putExtras(extras);
            startActivity(intent);
        });
    }

    @Override
    public void initView() {
        filterExpandableListView = findViewById(R.id.filters_expand);
        filterList.clear();
        adapter = new ExpandableListAdapter(this, filterList);
        adapter.onChildViewListener = adapterListener;
        filterExpandableListView.setAdapter(adapter);
        loading = findViewById(R.id.loading);
        nextButton = findViewById(R.id.next_button);
    }

    @Override
    public void initPresenter() {
        presenter = new AddSearchFiltersPresenterImpl();
        presenter.setView(this);
    }

    public Map<String,String> getFilter(List<Filter> filterList){
        int filterCount=0;
        Map<String,String> filtersMap=new HashMap<String, String>();
        for (int i = 0; i< filterList.size(); i++){
            for (int x = 0; x< filterList.get(i).options.size(); x++){
                if (filterList.get(i).options.get(x).isSelected) {
                    filtersMap.put("filters["+filterCount+"]", filterList.get(i).options.get(x).id.toString());
                    filterCount ++;
                }
            }

        }
        return filtersMap;
    }

    @Override
    public void showProgress(boolean b) {
        if (b) loading.setVisibility(View.VISIBLE);
        else loading.setVisibility(View.GONE);
    }
}
