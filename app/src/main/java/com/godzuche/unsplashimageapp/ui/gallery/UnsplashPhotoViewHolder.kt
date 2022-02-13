package com.godzuche.unsplashimageapp.ui.gallery

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.godzuche.unsplashimageapp.R
import com.godzuche.unsplashimageapp.data.remote.model.UnsplashPhoto
import com.godzuche.unsplashimageapp.databinding.ItemUnsplashPhotoBinding

class UnsplashPhotoViewHolder(private val binding: ItemUnsplashPhotoBinding) :
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

    companion object {
        fun create(parent: ViewGroup): UnsplashPhotoViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_unsplash_photo, parent, false)
            val binding = ItemUnsplashPhotoBinding.bind(view)
            return UnsplashPhotoViewHolder(binding)
        }
    }
}