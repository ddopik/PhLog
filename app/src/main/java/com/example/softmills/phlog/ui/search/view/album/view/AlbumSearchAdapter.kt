package com.example.softmills.phlog.ui.search.view.album.view

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView

import com.example.softmills.phlog.R
import com.example.softmills.phlog.Utiltes.GlideApp
import com.example.softmills.phlog.ui.search.view.album.model.AlbumSearch

/**
 * Created by abdalla_maged on 11/6/2018.
 */
class AlbumSearchAdapter(private var albumSearchList: List<AlbumSearch> ?) : RecyclerView.Adapter<AlbumSearchAdapter.AlbumSearchViewHolder>() {
    private var context: Context? = null
    var onAlbumPreview: OnAlbumPreview? = null

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): AlbumSearchViewHolder {
        this.context = viewGroup.context
        val layoutInflater = LayoutInflater.from(context)
        val view = layoutInflater.inflate(R.layout.view_holder_album_search, viewGroup, false)
        return AlbumSearchViewHolder(view)
    }

    override fun onBindViewHolder(albumSearchViewHolder: AlbumSearchViewHolder, i: Int) {


            albumSearchList?.get(i)?.photos?.getOrNull(0).let {
            GlideApp.with(context!!)
                    .load(it?.url)
                    .placeholder(R.drawable.default_place_holder)
                    .error(R.drawable.default_error_img)
                    .into(albumSearchViewHolder.image1)
        }
        albumSearchList?.get(i)?.photos?.getOrNull(1).let {

                GlideApp.with(context!!)
                        .load(it?.url)
                        .placeholder(R.drawable.default_place_holder)
                        .error(R.drawable.default_error_img)
                        .into(albumSearchViewHolder.image2)
        }
        albumSearchList?.get(i)?.photos?.getOrNull(2).let {

                GlideApp.with(context!!)
                        .load(it?.url)
                        .placeholder(R.drawable.default_place_holder)
                        .error(R.drawable.default_error_img)
                        .into(albumSearchViewHolder.image3)
        }
        albumSearchList?.get(i)?.name?.let {
            albumSearchViewHolder.albumName.text = it
        }
        albumSearchList?.get(i)?.photosCount?.let {
            albumSearchViewHolder.albumPhotoCount.text = this.toString()
        }


        if (onAlbumPreview != null) {
            albumSearchViewHolder.albumSearchListItemContainer.setOnClickListener { onAlbumPreview!!.openAlbumPreviewListener(albumSearchList?.get(i) !!) }
        }

    }

    override fun getItemCount(): Int {
        return albumSearchList?.size!!
    }

     inner class AlbumSearchViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var albumSearchListItemContainer: FrameLayout
        var openAlbumPreview: ImageButton
        var image1: ImageView
        var image2: ImageView
        var image3: ImageView
        var albumName: TextView
        var albumPhotoCount: TextView


        init {
            albumSearchListItemContainer = view.findViewById(R.id.album_search_list_item_container)
            openAlbumPreview = view.findViewById(R.id.open_album_preview)
            image1 = view.findViewById(R.id.album_search_img_1)
            image2 = view.findViewById(R.id.album_search_img_2)
            image3 = view.findViewById(R.id.album_search_img_3)

            albumName = view.findViewById(R.id.album_name)
            albumPhotoCount = view.findViewById(R.id.album_photo_count)

        }
    }

    interface OnAlbumPreview {
        fun openAlbumPreviewListener(albumSearch: AlbumSearch)
    }
}
