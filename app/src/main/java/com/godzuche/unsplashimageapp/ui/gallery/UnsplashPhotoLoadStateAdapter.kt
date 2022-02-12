package com.godzuche.unsplashimageapp.ui.gallery

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.godzuche.unsplashimageapp.R
import com.godzuche.unsplashimageapp.databinding.UnsplashPhotoLoadStateFooterViewItemBinding

class UnsplashPhotoLoadStateAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<UnsplashPhotoLoadStateAdapter.UnsplashPhotoLoadStateViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState,
    ): UnsplashPhotoLoadStateViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.unsplash_photo_load_state_footer_view_item, parent, false)
        val binding = UnsplashPhotoLoadStateFooterViewItemBinding.bind(view)
        return UnsplashPhotoLoadStateViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UnsplashPhotoLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }


    inner class UnsplashPhotoLoadStateViewHolder(private val binding: UnsplashPhotoLoadStateFooterViewItemBinding) :
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
    }

}