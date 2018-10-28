package com.example.softmills.phlog.ui.uploadimage.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.inputmethod.CompletionInfo;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.example.softmills.phlog.R;
import com.example.softmills.phlog.base.BaseActivity;
import com.example.softmills.phlog.base.widgets.CustomRecyclerView;
import com.example.softmills.phlog.ui.uploadimage.model.Tag;
import com.example.softmills.phlog.ui.uploadimage.view.adapter.AutoCompleteTagMenuAdapter;
import com.example.softmills.phlog.ui.uploadimage.view.adapter.SelectedTagAdapter;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abdalla_maged on 10/28/2018.
 */
public class AddTagActivity extends BaseActivity {
    private AutoCompleteTextView autoCompleteTextView;
    private List<Tag> tagList=new ArrayList<Tag>();
    private List<Tag> tagMenuList=new ArrayList<Tag>();
    private SelectedTagAdapter selectedTagAdapter;
    private AutoCompleteTagMenuAdapter autoCompleteTagMenuAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tag);

        initView();
        initPresenter();
        initListener();
    }


    @Override
    public void initView() {

        Tag tag=new Tag();
        tag.tagName="Tag";
        tagList.add(tag);
        tagList.add(tag);
        tagList.add(tag);
        tagList.add(tag);
        tagList.add(tag);
        tagList.add(tag);

        autoCompleteTextView = findViewById(R.id.tag_auto_complete);
         autoCompleteTagMenuAdapter=new AutoCompleteTagMenuAdapter(this,R.layout.item_drop_down,tagList);
        autoCompleteTextView.setAdapter(autoCompleteTagMenuAdapter);
        autoCompleteTextView.setThreshold(1);
        autoCompleteTagMenuAdapter.notifyDataSetChanged();




        CustomRecyclerView tagsRv = findViewById(R.id.tags_rv);

        // Create the FlexboxLayoutMananger, only flexbox library version 0.3.0 or higher support.
        FlexboxLayoutManager flexboxLayoutManager = new FlexboxLayoutManager(getApplicationContext());
        // Set flex direction.
        flexboxLayoutManager.setFlexDirection(FlexDirection.ROW);
        // Set JustifyContent.
        flexboxLayoutManager.setJustifyContent(JustifyContent.SPACE_AROUND);
        tagsRv.setLayoutManager(flexboxLayoutManager);

        // Set adapter object.
        SelectedTagAdapter selectedTagAdapter = new SelectedTagAdapter(tagList);

        tagsRv.setAdapter(selectedTagAdapter);
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void showToast(String msg) {
        super.showToast(msg);
    }

    private void initListener() {
        autoCompleteTextView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                Toast.makeText(AddTagActivity.this," selected", Toast.LENGTH_LONG).show();

            }

            public void onNothingSelected(AdapterView<?> arg0) {
                Toast.makeText(AddTagActivity.this,autoCompleteTextView.getText().toString(),Toast.LENGTH_SHORT).show();

            }
        });

    }
}
