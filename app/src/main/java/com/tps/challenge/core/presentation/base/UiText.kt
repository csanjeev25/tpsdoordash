package com.tps.challenge.core.presentation.base

import android.content.Context

sealed class UiText {
    data class DynamicString(val text: String): UiText()
    data class StaticString(val resId: Int): UiText()

    fun asString(context: Context): String {
            return when(this) {
                is DynamicString -> text
                is StaticString -> context.getString(resId)
            }
    }
}