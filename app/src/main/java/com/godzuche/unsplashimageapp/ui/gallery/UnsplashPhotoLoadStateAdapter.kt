package com.godzuche.unsplashimageapp.ui.gallery

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter

class UnsplashPhotoLoadStateAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<UnsplashPhotoLoadStateViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState,
    ): UnsplashPhotoLoadStateViewHolder {
        return UnsplashPhotoLoadStateViewHolder.create(parent, retry)
    }

    override fun onBindViewHolder(holder: UnsplashPhotoLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

}