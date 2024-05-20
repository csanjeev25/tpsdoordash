package com.tps.challenge.features.storefeed.presentation.storelist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.tps.challenge.R
import com.tps.challenge.core.presentation.utils.ImageLoader
import com.tps.challenge.core.presentation.utils.ItemClickListener
import com.tps.challenge.features.storefeed.domain.model.StoreItem

/**
 * A RecyclerView.Adapter to populate the screen with a store feed.
 */
class StoreFeedAdapter(
    private val imageLoader: ImageLoader,
    private val onItemClicked: (StoreItem) -> Unit,
) : RecyclerView.Adapter<StoreItemViewHolder>() {

    private var stores: MutableList<StoreItem> = mutableListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoreItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_store, parent, false)
        return StoreItemViewHolder(view, imageLoader, onItemClicked)
    }

    override fun onBindViewHolder(holder: StoreItemViewHolder, position: Int) {
        holder.bind(stores[position])
    }

    override fun getItemCount(): Int {
        return if (stores != null) stores.size else 0
    }

    fun updateStores(newStores: List<StoreItem>) {
        val diffCallback = StoreDiffCallback(this.stores, newStores)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        this.stores.clear()
        this.stores.addAll(newStores)
        diffResult.dispatchUpdatesTo(this)
    }
}

/**
 * Holds the view for the Store item.
 */
class StoreItemViewHolder(
    itemView: View,
    private val imageLoader: ImageLoader,
    private val onItemClicked: (StoreItem) -> Unit
) : RecyclerView.ViewHolder(itemView) {
    fun bind(storeItem: StoreItem) {
        with(itemView) {
            findViewById<TextView>(R.id.name).text = storeItem.name
            findViewById<TextView>(R.id.description).text = storeItem.description
            imageLoader.load(
                storeItem.coverImgUrl,
                findViewById(R.id.store_image)
            )
            setOnClickListener {
                onItemClicked(storeItem)
            }
        }
    }
}

class StoreDiffCallback(
    private val oldList: List<StoreItem>,
    private val newList: List<StoreItem>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}
