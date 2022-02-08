package com.godzuche.unsplashimageapp.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.godzuche.unsplashimageapp.api.UnsplashApiService
import com.godzuche.unsplashimageapp.data.remote.model.UnsplashPhoto

class UnsplashPagingSource(
    private val unsplashApiService: UnsplashApiService,
    private val query: String
): PagingSource<Int, UnsplashPhoto>() {
    override fun getRefreshKey(state: PagingState<Int, UnsplashPhoto>): Int? {

    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UnsplashPhoto> {
    }
}