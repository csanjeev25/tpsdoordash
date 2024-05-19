package com.tps.challenge.core.domain.preferences

import com.tps.challenge.core.domain.model.Location

interface Preferences {
    fun saveLocation(location: Location)
    fun getLocation(): Location?

    companion object {
        const val KEY_LOCATION = "location"
    }
}