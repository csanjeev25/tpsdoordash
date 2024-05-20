package com.tps.challenge.core.presentation.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide

class GlideImageLoader(private val context: Context) : ImageLoader {
    override fun load(url: String, into: ImageView) {
        Glide.with(context)
            .load(url)
            .centerCrop()
            .into(into)
    }
}
