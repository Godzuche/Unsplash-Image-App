package com.godzuche.unsplashimageapp.ui.gallery

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.godzuche.unsplashimageapp.R
import com.godzuche.unsplashimageapp.databinding.UnsplashPhotoLoadStateFooterViewItemBinding

class UnsplashPhotoLoadStateViewHolder(
    private val binding: UnsplashPhotoLoadStateFooterViewItemBinding,
    retry: () -> Unit,
) :
    RecyclerView.ViewHolder(binding.root) {

    init {
        binding.btRetry.setOnClickListener { retry.invoke() }
    }

    fun bind(loadState: LoadState) {
        binding.apply {
            if (loadState is LoadState.Error) {
                errorMsg.text = loadState.error.localizedMessage
            }

            progressBar.isVisible = loadState is LoadState.Loading
            btRetry.isVisible = loadState is LoadState.Error
            errorMsg.isVisible = loadState is LoadState.Error
        }
    }

    companion object {
        fun create(parent: ViewGroup, retry: () -> Unit): UnsplashPhotoLoadStateViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.unsplash_photo_load_state_footer_view_item, parent, false)
            val binding = UnsplashPhotoLoadStateFooterViewItemBinding.bind(view)
            return UnsplashPhotoLoadStateViewHolder(binding, retry)
        }
    }
}