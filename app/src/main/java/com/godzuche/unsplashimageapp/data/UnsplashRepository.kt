package com.godzuche.unsplashimageapp.data

import com.godzuche.unsplashimageapp.api.UnsplashApiService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UnsplashRepository @Inject constructor(
    private val unsplashApi: UnsplashApiService
) {
}