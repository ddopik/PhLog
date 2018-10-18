package com.example.softmills.phlog.ui.uploadimage.view;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.softmills.phlog.R;
import com.example.softmills.phlog.Utiltes.BitmapUtils;
import com.example.softmills.phlog.Utiltes.SpacesItemDecoration;
import com.example.softmills.phlog.base.BaseFragment;
import com.zomato.photofilters.FilterPack;
import com.zomato.photofilters.imageprocessors.Filter;
import com.zomato.photofilters.utils.ThumbnailItem;
import com.zomato.photofilters.utils.ThumbnailsManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abdalla_maged on 10/18/2018.
 */
public class FiltersListFragment extends BaseFragment implements ThumbnailsAdapter.ThumbnailsAdapterListener {

    private static String TAG = "FiltersListFragment";
    RecyclerView recyclerView;
    ThumbnailsAdapter mAdapter;
    List<ThumbnailItem> thumbnailItemList;
    FiltersListFragmentListener listener;

    public static FiltersListFragment getInstance(String PickedImageName) {
        FiltersListFragment filtersListFragment = new FiltersListFragment();

//        Bundle bundle = new Bundle();
//        bundle.putString("pickedImageName", PickedImageName);
//        filtersListFragment.setArguments(bundle);
        return new FiltersListFragment();
    }

    public void setListener(FiltersListFragmentListener listener) {
        this.listener = listener;
    }

    public FiltersListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_filters_list, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);

        thumbnailItemList = new ArrayList<>();
        mAdapter = new ThumbnailsAdapter(getActivity(), thumbnailItemList, this);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        int space = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics());
        recyclerView.addItemDecoration(new SpacesItemDecoration(space));
        recyclerView.setAdapter(mAdapter);

        prepareThumbnail(null,"");

        return view;
    }

    @Override
    protected void initPresenter() {

    }

    @Override
    protected void initViews() {

    }

    /**
     * Renders thumbnails in horizontal list
     * loads default image from Assets if passed param is null
     *
     * @param bitmap
     */
    public void prepareThumbnail(final Bitmap bitmap,String pickedImageName) {
        Runnable r = () -> {
            Bitmap thumbImage;

            if (bitmap == null) {
                thumbImage = BitmapUtils.getBitmapFromAssets(getActivity(), pickedImageName, 100, 100);
            } else {
                thumbImage = Bitmap.createScaledBitmap(bitmap, 100, 100, false);
            }

            if (thumbImage == null) {
                Log.e(TAG, "prepareThumbnail() ---> thumbImage is null");
                return;
            }


            ThumbnailsManager.clearThumbs();
            thumbnailItemList.clear();

            // add normal bitmap first
            ThumbnailItem thumbnailItem = new ThumbnailItem();
            thumbnailItem.image = thumbImage;
            thumbnailItem.filterName = getString(R.string.filter_normal);
            ThumbnailsManager.addThumb(thumbnailItem);

            List<Filter> filters = FilterPack.getFilterPack(getActivity());

            for (Filter filter : filters) {
                ThumbnailItem tI = new ThumbnailItem();
                tI.image = thumbImage;
                tI.filter = filter;
                tI.filterName = filter.getName();
                ThumbnailsManager.addThumb(tI);
            }

            thumbnailItemList.addAll(ThumbnailsManager.processThumbs(getActivity()));

            getActivity().runOnUiThread(() -> mAdapter.notifyDataSetChanged());
        };

        new Thread(r).start();
    }

    @Override
    public void onFilterSelected(Filter filter) {
        if (listener != null)
            listener.onFilterSelected(filter);
    }

    public interface FiltersListFragmentListener {
        void onFilterSelected(Filter filter);
    }
}