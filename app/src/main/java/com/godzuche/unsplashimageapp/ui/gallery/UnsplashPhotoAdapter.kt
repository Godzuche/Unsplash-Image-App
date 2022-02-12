package com.godzuche.unsplashimageapp.ui.gallery

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.godzuche.unsplashimageapp.R
import com.godzuche.unsplashimageapp.data.remote.model.UnsplashPhoto
import com.godzuche.unsplashimageapp.databinding.ItemUnsplashPhotoBinding

class UnsplashPhotoAdapter :
    PagingDataAdapter<UnsplashPhoto, UnsplashPhotoAdapter.UnsplashPhotoViewHolder>(PHOTO_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UnsplashPhotoViewHolder {
        val binding =
            ItemUnsplashPhotoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UnsplashPhotoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UnsplashPhotoViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    inner class UnsplashPhotoViewHolder(private val binding: ItemUnsplashPhotoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(unsplashPhoto: UnsplashPhoto) {
            binding.apply {
                /*imvUnsplashPhoto.load(unsplashPhoto.urls.regular) {
                    crossfade(true)
//                    placeholder() could be used to add loading animation drawable
                    error(R.drawable.ic_broken_image)
                    build()
                }*/

                Glide.with(itemView)
                    .load(unsplashPhoto.urls.regular)
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.ic_broken_image)
                    .into(imvUnsplashPhoto)

                tvUsername.text = unsplashPhoto.user.username
            }
        }
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