package com.godzuche.unsplashimageapp.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.godzuche.unsplashimageapp.api.UnsplashApiService
import com.godzuche.unsplashimageapp.data.UnsplashRepository.Companion.NETWORK_PAGE_SIZE
import com.godzuche.unsplashimageapp.data.remote.model.UnsplashPhoto
import retrofit2.HttpException
import java.io.IOException

private const val UNSPLASH_STARTING_PAGE_INDEX = 1
class UnsplashPagingSource(
    private val unsplashApiService: UnsplashApiService,
    private val query: String
): PagingSource<Int, UnsplashPhoto>() {
    // This method triggers the Api request and turns the data into pages
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UnsplashPhoto> {
        // Current page
        val position = params.key ?: UNSPLASH_STARTING_PAGE_INDEX
        // Use try-catch 'cause the request is not always successful
        return try {
            // Make the Api call
            val response = unsplashApiService.searchPhotos(
                query,
                position,
                params.loadSize
            )
            val photos = response.results
            // Return
            LoadResult.Page(
                data = photos,
                prevKey = if (position == UNSPLASH_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (photos.isEmpty()) null else {
//                    position + 1
                    position + (params.loadSize / NETWORK_PAGE_SIZE)
                }
            )
        } catch (exception: IOException) {
            // This can be thrown when there is no internet connection when making the request
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            // If there is something wrong with the server
            // For instance if the request is not authorized maybe because we forgot to pass the access key or if there is no data in the endpoint
            LoadResult.Error(exception)
        }
    }

    // The refresh key is used for subsequent refresh calls to PagingSource.load after the initial load
    override fun getRefreshKey(state: PagingState<Int, UnsplashPhoto>): Int? {
        // We need to get the previous key (or next key if previous is null) of the page
        // that was closest to the most recently accessed index.
        // Anchor position is the most recently accessed index
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}