package com.godzuche.unsplashimageapp.ui.gallery

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import com.godzuche.unsplashimageapp.R
import com.godzuche.unsplashimageapp.databinding.FragmentGalleryBinding
import com.godzuche.unsplashimageapp.util.onQueryTextChange
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class GalleryFragment : Fragment(R.layout.fragment_gallery) {
    private val viewModel by viewModels<GalleryViewModel>()
    private var _binding: FragmentGalleryBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val unsplashPhotoAdapter = UnsplashPhotoAdapter()

        binding.apply {
            recyclerview.apply {
                setHasFixedSize(true)
                adapter = unsplashPhotoAdapter.withLoadStateHeaderAndFooter(
                    header = UnsplashPhotoLoadStateAdapter { unsplashPhotoAdapter.retry() },
                    footer = UnsplashPhotoLoadStateAdapter { unsplashPhotoAdapter.retry() }
                )
            }
            btRetry.setOnClickListener { unsplashPhotoAdapter.retry() }
        }

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.photosPagingDataFlow.collectLatest(unsplashPhotoAdapter::submitData)
            }
        }
        lifecycleScope.launch {
            unsplashPhotoAdapter.loadStateFlow.collect { loadState ->
                binding.apply {
                    // Loading
                    circularProgressBar.isVisible = loadState.source.refresh is LoadState.Loading
                    // Successful and finished loading
                    recyclerview.isVisible = loadState.source.refresh is LoadState.NotLoading

                    // Error
                    btRetry.isVisible = loadState.source.refresh is LoadState.Error
                    tvError.isVisible = loadState.source.refresh is LoadState.Error

                    // Empty List textView
                    if (loadState.source.refresh is LoadState.NotLoading &&
                        loadState.append.endOfPaginationReached &&
                        unsplashPhotoAdapter.itemCount < 1
                    ) {
                        recyclerview.isVisible = false
                        tvResultNotFound.isVisible = true
                    } else {
                        tvResultNotFound.isVisible = false
                    }
                }
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.menu_gallery, menu)

        // Find the search item
        val searchItem = menu.findItem(R.id.action_search)

        // Reference to the searchView
        val searchView = searchItem.actionView as SearchView

        searchView.apply {
            queryHint = "Search Photos"
//            setIconifiedByDefault(true)
            onQueryTextChange(binding) { queryText ->
                // Update search query
                viewModel.searchPhotos(query = queryText)
            }
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.action_search) {
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}