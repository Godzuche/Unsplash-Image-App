package com.godzuche.unsplashimageapp.ui.gallery

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.godzuche.unsplashimageapp.R
import com.godzuche.unsplashimageapp.databinding.FragmentGalleryBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class GalleryFragment : Fragment(R.layout.fragment_gallery) {
    private val viewModel by viewModels<GalleryViewModel>()
    private var _binding: FragmentGalleryBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentGalleryBinding.bind(view)

        val unsplashPhotoAdapter = UnsplashPhotoAdapter()

        binding.apply {
            recyclerview.apply {
                setHasFixedSize(true)
                adapter = unsplashPhotoAdapter.withLoadStateHeaderAndFooter(
                    header = UnsplashPhotoLoadStateAdapter { unsplashPhotoAdapter.retry() },
                    footer = UnsplashPhotoLoadStateAdapter { unsplashPhotoAdapter.retry() }
                )
            }
        }

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.photosPagingDataFlow.collectLatest(unsplashPhotoAdapter::submitData)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}