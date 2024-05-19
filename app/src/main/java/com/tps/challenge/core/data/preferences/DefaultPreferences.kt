package com.tps.challenge.core.data.preferences

import android.content.SharedPreferences
import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.tps.challenge.core.domain.model.Location
import com.tps.challenge.core.domain.preferences.Preferences
import javax.inject.Inject

class DefaultPreferences @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val gson: Gson
): Preferences {
    override fun saveLocation(location: Location) {
        sharedPreferences.edit()
            .putString(Preferences.KEY_LOCATION, gson.toJson(location))
            .apply()
    }

    override fun getLocation(): Location? {
        val locationString = sharedPreferences.getString(Preferences.KEY_LOCATION, null)
        return gson.fromJson(locationString, Location::class.java)
    }
}