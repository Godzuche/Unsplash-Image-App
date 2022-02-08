package com.godzuche.unsplashimageapp.ui.gallery

import androidx.lifecycle.ViewModel
import com.godzuche.unsplashimageapp.data.UnsplashRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(
    private val repository: UnsplashRepository
): ViewModel() {
}