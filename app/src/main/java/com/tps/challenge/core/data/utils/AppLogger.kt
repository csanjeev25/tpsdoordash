package com.tps.challenge.core.data.utils

import android.util.Log
import com.tps.challenge.core.domain.utils.Logger

class AppLogger: Logger {
    override fun d(tag: String, msg: String) {
        Log.d(tag, msg)
    }
}