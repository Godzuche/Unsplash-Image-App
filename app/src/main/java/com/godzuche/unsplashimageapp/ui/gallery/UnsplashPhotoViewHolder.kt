package com.godzuche.unsplashimageapp.ui.gallery

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.godzuche.unsplashimageapp.R
import com.godzuche.unsplashimageapp.data.remote.model.UnsplashPhoto
import com.godzuche.unsplashimageapp.databinding.ItemUnsplashPhotoBinding

class UnsplashPhotoViewHolder(private val binding: ItemUnsplashPhotoBinding, private val listener: UnsplashPhotoAdapter.OnItemClickListener) :
    RecyclerView.ViewHolder(binding.root) {

    var currentItem: UnsplashPhoto? = null

    init {
        binding.root.setOnClickListener {
//            val position = bindingAdapterPosition
           /* if (position != RecyclerView.NO_POSITION) {
                // Should be done with an inner class
                val item = getItem(position)
                if (item != null) listener.onItemClick(item)
            }*/

                listener.onItemClick(currentItem!!)

        }
    }

    fun bind(unsplashPhoto: UnsplashPhoto) {
        binding.apply {
/*            imvUnsplashPhoto.load(unsplashPhoto.urls.regular) {
                crossfade(true)
//                    placeholder() could be used to add loading animation drawable
                error(R.drawable.ic_broken_image)
                transformations(CircleCropTransformation())
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
        fun create(parent: ViewGroup, listener: UnsplashPhotoAdapter.OnItemClickListener): UnsplashPhotoViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_unsplash_photo, parent, false)
            val binding = ItemUnsplashPhotoBinding.bind(view)
            return UnsplashPhotoViewHolder(binding, listener)
        }
    }
}