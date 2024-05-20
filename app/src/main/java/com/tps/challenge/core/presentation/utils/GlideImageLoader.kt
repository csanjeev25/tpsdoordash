package com.tps.challenge.core.presentation.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager

class GlideImageLoader(private val glideRequestManager: RequestManager) : ImageLoader {
    override fun load(url: String, into: ImageView) {
        glideRequestManager
            .load(url)
            .centerCrop()
            .into(into)
    }
}
