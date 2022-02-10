package com.godzuche.unsplashimageapp.data

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.godzuche.unsplashimageapp.api.UnsplashApiService
import com.godzuche.unsplashimageapp.data.remote.model.UnsplashPhoto
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UnsplashRepository @Inject constructor(
    private val unsplashApi: UnsplashApiService,
) {

    // Get search results as a stream using flow
    fun getSearchResultStream(query: String): Flow<PagingData<UnsplashPhoto>> {
        Log.d("UnsplashRepository", "New query: $query")
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                maxSize = 100,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                UnsplashPagingSource(unsplashApi, query)
            }
        ).flow
    }

    companion object {
        const val NETWORK_PAGE_SIZE = 20
    }
}