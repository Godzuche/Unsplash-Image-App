package com.godzuche.unsplashimageapp.ui.gallery

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.godzuche.unsplashimageapp.data.remote.model.UnsplashPhoto

class UnsplashPhotoAdapter(private val listener: OnItemClickListener) :
    PagingDataAdapter<UnsplashPhoto, UnsplashPhotoViewHolder>(PHOTO_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UnsplashPhotoViewHolder =
        UnsplashPhotoViewHolder.create(parent, listener)

    override fun onBindViewHolder(holder: UnsplashPhotoViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.currentItem = currentItem
            holder.bind(currentItem)
        }
    }

    interface OnItemClickListener{
        fun onItemClick(photo: UnsplashPhoto)
    }

    companion object {
        private val PHOTO_COMPARATOR = object : DiffUtil.ItemCallback<UnsplashPhoto>() {
            override fun areItemsTheSame(oldItem: UnsplashPhoto, newItem: UnsplashPhoto): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: UnsplashPhoto,
                newItem: UnsplashPhoto,
            ): Boolean =
                oldItem == newItem
        }
    }

}