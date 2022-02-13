package com.godzuche.unsplashimageapp.ui.gallery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.godzuche.unsplashimageapp.data.UnsplashRepository
import com.godzuche.unsplashimageapp.data.remote.model.UnsplashPhoto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(
    private val repository: UnsplashRepository,
) : ViewModel() {

    //    private val currentQuery = MutableStateFlow<String>(DEFAULT_QUERY)
    private val currentQuery = MutableStateFlow(DEFAULT_QUERY)

    @ExperimentalCoroutinesApi
    val photosPagingDataFlow = currentQuery.flatMapLatest { queryString ->
        getSearchResults(query = queryString)
    }.cachedIn(viewModelScope)

    private fun getSearchResults(query: String): Flow<PagingData<UnsplashPhoto>> =
        repository.getSearchResultStream(query = query)

    fun searchPhotos(query: String) {
        currentQuery.value = query
    }

    companion object {
        private const val DEFAULT_QUERY = "dogs"
    }
}