package com.tps.challenge.features.storefeed.presentation.storelist

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tps.challenge.R
import dagger.hilt.android.AndroidEntryPoint

/**
 * The initial Activity for the app.
 */
@AndroidEntryPoint
class StoreListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val storeFeedFragment = StoreFeedFragment()
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.container, storeFeedFragment,
                StoreFeedFragment.TAG
            )
            .commit()
    }
}
