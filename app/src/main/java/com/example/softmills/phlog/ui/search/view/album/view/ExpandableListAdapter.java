package com.example.softmills.phlog.ui.search.view.album.view;

import android.content.Context;
import android.graphics.Typeface;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.softmills.phlog.R;
import com.example.softmills.phlog.base.widgets.CustomTextView;
import com.example.softmills.phlog.ui.search.view.album.model.FilterOption;
import com.example.softmills.phlog.ui.search.view.album.model.SearchFilter;

import java.util.List;

/**
 * Created by abdalla_maged on 10/30/2018.
 */
public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private Context _context;
    private List<SearchFilter> searchFilterList;
    public OnChildViewListener onChildViewListener;

    public ExpandableListAdapter(Context context, List<SearchFilter> searchFilterList) {
        this._context = context;
        this.searchFilterList = searchFilterList;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return searchFilterList.get(groupPosition).options.get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        FilterOption filterOption = searchFilterList.get(groupPosition).options.get(childPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.item_expand_list, null);
        }

        TextView txtListChild = convertView.findViewById(R.id.filter_item_val);
        ConstraintLayout itemContainer = convertView.findViewById(R.id.item_filter_container);
        RadioButton filterRadioButton = convertView.findViewById(R.id.filter_select);
        txtListChild.setText(filterOption.displayName);


        if (filterOption.isSelected) {
            filterRadioButton.setChecked(true);

        } else {
            filterRadioButton.setChecked(false);
        }

        itemContainer.setOnClickListener(v -> onChildViewListener.onChildViewClickListener(filterOption));
        filterRadioButton.setOnClickListener(v -> onChildViewListener.onChildViewClickListener(filterOption));


        return convertView;
    }


    @Override
    public int getChildrenCount(int groupPosition) {
        return searchFilterList.get(groupPosition).options.size();

    }

    @Override
    public Object getGroup(int groupPosition) {
        return searchFilterList.get(groupPosition).options;
    }

    @Override
    public int getGroupCount() {
        return searchFilterList.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = searchFilterList.get(groupPosition).displayName;
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group_header, null);
        }

        CustomTextView lblListHeader = convertView.findViewById(R.id.lblListHeader);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);
        ImageView arrow = convertView.findViewById(R.id.header_item_down_btn);
        if (isExpanded) {
            arrow.setBackgroundResource(R.drawable.arrow_up_orange);
            lblListHeader.setTextColor(convertView.getContext().getResources().getColor(R.color.text_input_color));
        } else {
            arrow.setBackgroundResource(R.drawable.down_arrow_white);
            lblListHeader.setTextColor(convertView.getContext().getResources().getColor(R.color.white));
        }

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


    public interface OnChildViewListener {
        void onChildViewClickListener(FilterOption filterOption);

    }
}
