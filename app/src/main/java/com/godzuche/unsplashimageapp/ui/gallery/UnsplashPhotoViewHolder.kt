package com.godzuche.unsplashimageapp.ui.gallery

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.godzuche.unsplashimageapp.R
import com.godzuche.unsplashimageapp.data.remote.model.UnsplashPhoto
import com.godzuche.unsplashimageapp.databinding.ItemUnsplashPhotoBinding
import com.godzuche.unsplashimageapp.util.BlurHashDecoder

class UnsplashPhotoViewHolder(
    private val binding: ItemUnsplashPhotoBinding,
    private val listener: UnsplashPhotoAdapter.OnItemClickListener,
) :
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

            var bitmap: Bitmap? = null
            bitmap = BlurHashDecoder.decode(unsplashPhoto.blurHash, 2, 1)
            val drawable = BitmapDrawable(itemView.context.applicationContext.resources, bitmap)

            Glide.with(itemView)
                .load(unsplashPhoto.urls.small)
                .centerCrop()
                .transition(DrawableTransitionOptions.withCrossFade())
                .placeholder(drawable)
//                .error(R.drawable.ic_broken_image)
                .into(imvUnsplashPhoto)

            tvUsername.text = unsplashPhoto.user.username
        }
    }

    companion object {
        fun create(
            parent: ViewGroup,
            listener: UnsplashPhotoAdapter.OnItemClickListener,
        ): UnsplashPhotoViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_unsplash_photo, parent, false)
            val binding = ItemUnsplashPhotoBinding.bind(view)
            return UnsplashPhotoViewHolder(binding, listener)
        }
    }
}