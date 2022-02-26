package com.godzuche.unsplashimageapp.data.remote.model

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class UnsplashPhoto(
    val id: String,
    val color: String,
/*    @Json(name = "blur_hash")
    val blurHash: String?,*/
    val description: String?,
    val urls: UnsplashPhotoUrls,
    val user: UnsplashUser,
) : Parcelable {

    @Parcelize
    data class UnsplashPhotoUrls(
        val raw: String,
        val full: String,
        val regular: String,
        val small: String,
        val thumb: String,
    ) : Parcelable

    @Parcelize
    data class UnsplashUser(
        val name: String,
        val username: String,
        @Json(name = "profile_image")
        val profileImage: UnsplashUserProfileImage,
    ) : Parcelable {
        val attributionUrl get() = "https://unsplash.com/$username?utm_source=Images_App&utm_medium=referral"

        @Parcelize
        data class UnsplashUserProfileImage(
            val small: String,
            val medium: String,
            val large: String,
        ) : Parcelable
    }
}
