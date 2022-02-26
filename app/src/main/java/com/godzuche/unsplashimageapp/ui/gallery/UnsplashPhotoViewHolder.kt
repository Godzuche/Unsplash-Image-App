package com.godzuche.unsplashimageapp.ui.gallery

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.godzuche.unsplashimageapp.R
import com.godzuche.unsplashimageapp.data.remote.model.UnsplashPhoto
import com.godzuche.unsplashimageapp.databinding.ItemUnsplashPhotoBinding

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

            /*         var bitmap: Bitmap? = null
                     bitmap = BlurHashDecoder.decode(unsplashPhoto.blurHash, 3, 2)
                     val drawable = BitmapDrawable(itemView.context.applicationContext.resources, bitmap)*/

            // Using Coil
/*            imvUnsplashPhoto.load(unsplashPhoto.urls.regular) {
                crossfade(true)
                placeholder(drawable) *//*could be used to add loading animation drawable*//*
                error(drawable)
                transformations(RoundedCornersTransformation())
                build()
            }*/


            // Using Glide
            Glide.with(itemView)
                .load(unsplashPhoto.urls.small)
                .centerCrop()
                .transition(DrawableTransitionOptions.withCrossFade())
//                .placeholder(R.drawable.ic_broken_image)
                .error(R.drawable.ic_broken_image)
                .into(imvUnsplashPhoto)

            tvUsername.text = unsplashPhoto.user.username

            Glide.with(itemView)
                .load(unsplashPhoto.user.profileImage.medium)
                .circleCrop()
                .transition(DrawableTransitionOptions.withCrossFade())
                .placeholder(R.drawable.ic_baseline_person_24)
                .error(R.drawable.ic_baseline_person_24)
                .into(imvUserProfile)

            /*imvUserProfile.load(unsplashPhoto.user.profileImage.small) {
                crossfade(true)
                placeholder(R.drawable.ic_baseline_person_24) *//*could be used to add loading animation drawable*//*
                error(R.drawable.ic_baseline_person_24)
                transformations(CircleCropTransformation())
                listener(
                    onError = { request, throwable ->
                        CoroutineScope(Dispatchers.IO).launch{
                            *//*itemView.context.imageLoader.enqueue(request.newBuilder(itemView.context)
                                .placeholder(R.drawable.ic_baseline_person_24)
                                .error(R.drawable.ic_baseline_person_24)
                                .build())*//*
                            itemView.context.imageLoader.enqueue(request).await()

                        }

                    }
                )
                build()
            }*/
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