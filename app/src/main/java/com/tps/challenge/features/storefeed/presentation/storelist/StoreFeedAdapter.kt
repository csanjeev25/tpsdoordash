package com.tps.challenge.features.storefeed.presentation.storelist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tps.challenge.R
import com.tps.challenge.core.presentation.utils.ItemClickListener
import com.tps.challenge.features.storefeed.domain.model.StoreItem

/**
 * A RecyclerView.Adapter to populate the screen with a store feed.
 */
class StoreFeedAdapter(
    private val stores: ArrayList<StoreItem>,
    private val onItemClicked: (StoreItem) -> Unit
) : RecyclerView.Adapter<StoreItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoreItemViewHolder {
        return StoreItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_store, parent, false)
        )
    }

    override fun onBindViewHolder(holder: StoreItemViewHolder, position: Int) {
        val storeItem = stores[position]
        with(holder.itemView) {
            findViewById<TextView>(R.id.name).text = storeItem.name
            findViewById<TextView>(R.id.description).text = storeItem.description
            setOnClickListener {

            }
        }
    }

    override fun getItemCount(): Int {
        return if (stores != null) stores.size else 0
    }

    fun addStores(list: List<StoreItem>) {
        stores.addAll(list)
    }
}

/**
 * Holds the view for the Store item.
 */
class StoreItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
