package com.godzuche.unsplashimageapp.api

import com.godzuche.unsplashimageapp.data.remote.model.UnsplashPhoto

data class UnsplashResponse(
    val results: List<UnsplashPhoto>
)
