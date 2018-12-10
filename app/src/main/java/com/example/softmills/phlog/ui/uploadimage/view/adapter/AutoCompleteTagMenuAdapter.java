package com.example.softmills.phlog.ui.uploadimage.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.example.softmills.phlog.R;
import com.example.softmills.phlog.base.commonmodel.Tags;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abdalla_maged on 10/28/2018.
 */
public class AutoCompleteTagMenuAdapter extends ArrayAdapter<Tags> {


    private int resourceLayout;
    private Context mContext;
    private List<Tags> tagsList;
    private List<Tags> allTagsList;
    public OnMenuItemClicked onMenuItemClicked;

    public AutoCompleteTagMenuAdapter(Context context, int resource, List<Tags> items) {
        super(context, resource, items);
        this.resourceLayout = resource;
        this.mContext = context;
        this.tagsList = new ArrayList<>(items);
        this.allTagsList = new ArrayList<>(items);
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {


            if (convertView == null) {
                LayoutInflater layoutInflater = LayoutInflater.from(mContext);
                convertView = layoutInflater.inflate(resourceLayout, parent, false);
            }
            Tags tags = getItem(position);
            TextView name =   convertView.findViewById(R.id.tag_auto_complete_name);
             if (onMenuItemClicked !=null){
                name.setOnClickListener((view)-> onMenuItemClicked.onItemSelected(tags));
             }
            name.setText(tags.name);

        return convertView;
    }

    public Tags getItem(int position) {
        return tagsList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public int getCount() {
        return tagsList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {


            @Override
            public String convertResultToString(Object resultValue) {
                return ((Tags) resultValue).name;
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                List<Tags> departmentsSuggestion = new ArrayList<>();
                if (constraint != null) {
                    for (Tags tags : allTagsList) {
                        if (tags.name.toLowerCase().startsWith(constraint.toString().toLowerCase())  || tags.name.toLowerCase().contains(constraint.toString().toLowerCase())  ) {
                            departmentsSuggestion.add(tags);
                        }
                    }
                    filterResults.values = departmentsSuggestion;
                    filterResults.count = departmentsSuggestion.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                tagsList.clear();
                if (results != null && results.count > 0) {

                    for (Object object : (List<?>) results.values) {
                        if (object instanceof Tags) {
                            tagsList.add((Tags) object);
                        }
                    }
                    notifyDataSetChanged();
                } else if (constraint == null) {
                    // no filter, add entire original list back in
                    tagsList.addAll(allTagsList);
                    notifyDataSetInvalidated();
                }
            }
        };
    }

    public interface OnMenuItemClicked{
        void onItemSelected(Tags tags);
     }
}


