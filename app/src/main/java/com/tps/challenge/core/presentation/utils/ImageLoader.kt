package com.tps.challenge.core.presentation.utils

import android.widget.ImageView

interface ImageLoader {
    fun load(url: String, into: ImageView)
}
