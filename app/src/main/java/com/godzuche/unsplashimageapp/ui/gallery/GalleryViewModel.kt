package com.godzuche.unsplashimageapp.ui.gallery

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.godzuche.unsplashimageapp.data.UnsplashRepository
import com.godzuche.unsplashimageapp.data.remote.model.UnsplashPhoto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class GalleryViewModel @Inject constructor(
    private val repository: UnsplashRepository,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    val state: StateFlow<UiState>
    val photosPagingDataFlow: Flow<PagingData<UnsplashPhoto>>
    val accept: (UiAction) -> Unit

    init {
        val initialQuery: String = savedStateHandle.get(LAST_SEARCH_QUERY) ?: DEFAULT_QUERY
        val lastQueryScrolled: String = savedStateHandle.get(LAST_QUERY_SCROLLED) ?: DEFAULT_QUERY

        // SharedFlow used for ui events/actions
        val actionStateFlow = MutableSharedFlow<UiAction>()

        val searches = actionStateFlow
            .filterIsInstance<UiAction.Search>()
            .distinctUntilChanged()
            .onStart { emit(UiAction.Search(query = initialQuery)) }
        val queriesScrolled = actionStateFlow
            .filterIsInstance<UiAction.Scroll>()
            .distinctUntilChanged()
            .shareIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
                replay = 1
            )
            .onStart { emit(UiAction.Scroll(lastQueryScrolled)) }

        photosPagingDataFlow = searches
            .flatMapLatest { searchPhotos(query = it.query) }
            .cachedIn(viewModelScope)

        state = combine(
            searches,
            queriesScrolled,
            ::Pair
        ).map { (search, scroll) ->
            UiState(
                query = search.query,
                lastQueryScrolled = scroll.currentQuery,
                hasNotScrolledForCurrentSearch = search.query != scroll.currentQuery
            )
        }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
                initialValue = UiState()
            )

        accept = { action ->
            viewModelScope.launch {
                actionStateFlow.emit(action)
                saveState()
            }
        }
    }

    /**
     * Saves the queries using the [SavedStateHandle] to survive system initiated process death
     * */
    private fun saveState() {
        savedStateHandle[LAST_SEARCH_QUERY] = state.value.query
        savedStateHandle[LAST_QUERY_SCROLLED] = state.value.lastQueryScrolled
    }

    override fun onCleared() {
        savedStateHandle[LAST_SEARCH_QUERY] = state.value.query
        savedStateHandle[LAST_QUERY_SCROLLED] = state.value.lastQueryScrolled
        super.onCleared()
    }

    private fun searchPhotos(query: String): Flow<PagingData<UnsplashPhoto>> =
        repository.getSearchResultStream(query = query)

}

sealed class UiAction {
    data class Search(val query: String) : UiAction()
    data class Scroll(val currentQuery: String) : UiAction()
}

data class UiState(
    val query: String = DEFAULT_QUERY,
    val lastQueryScrolled: String = DEFAULT_QUERY,
    val hasNotScrolledForCurrentSearch: Boolean = false,
)

private const val LAST_SEARCH_QUERY: String = "last_search_query"
private const val LAST_QUERY_SCROLLED: String = "last_query_scrolled"
private const val DEFAULT_QUERY = "dogs"