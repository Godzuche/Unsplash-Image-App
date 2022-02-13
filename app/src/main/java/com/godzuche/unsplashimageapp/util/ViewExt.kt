package com.godzuche.unsplashimageapp.util

import androidx.appcompat.widget.SearchView
import com.godzuche.unsplashimageapp.databinding.FragmentGalleryBinding

// View Extensions

inline fun SearchView.onQueryTextChange(binding: FragmentGalleryBinding, crossinline listener: (String) -> Unit) {
    this.setOnQueryTextListener( object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            query?.let {
                binding.recyclerview.scrollToPosition(0)
                listener(it)
                this@onQueryTextChange.clearFocus()
            }
            return true
        }

        override fun onQueryTextChange(newText: String?): Boolean {
//            listener(newText.orEmpty())
            return true
        }

    })
}