package com.seigneur.gauvain.wowsplash.ui.collections.list.adapter

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.seigneur.gauvain.wowsplash.data.model.PhotoCollection
import com.seigneur.gauvain.wowsplash.ui.base.list.BasePagedListAdapter
import com.seigneur.gauvain.wowsplash.ui.base.list.NetworkItemCallback



class CollectionsListAdapter(private val photoItemCallback: CollectionsItemCallback,
                             networkItemCallback: NetworkItemCallback) :
    BasePagedListAdapter<PhotoCollection, RecyclerView.ViewHolder>(collectionDiffCallback,networkItemCallback) {

    override val viewHolder: RecyclerView.ViewHolder
        get() =  CollectionsViewHolder.create(itemParent, photoItemCallback)

    override fun bindItemData(holder: RecyclerView.ViewHolder, position: Int) {
        super.bindItemData(holder, position)
        (holder as CollectionsViewHolder).bindTo(getItem(position)!!)
    }

    fun getCollectionClicked(pos: Int): PhotoCollection? {
        return getItem(pos)
    }

    companion object {

        private val collectionDiffCallback = object : DiffUtil.ItemCallback<PhotoCollection>() {
            override fun areItemsTheSame(oldItem: PhotoCollection, newItem: PhotoCollection): Boolean {
                return oldItem.id === newItem.id
            }

            override fun areContentsTheSame(oldItem: PhotoCollection, newItem: PhotoCollection): Boolean {
                return oldItem == newItem
            }
        }
    }


}




/*
class CollectionsListAdapter(private val photoItemCallback: CollectionsItemCallback,
                             private val networkItemCallback: NetworkItemCallback) :
    PagedListAdapter<PhotoCollection, RecyclerView.ViewHolder>(collectionDiffCallback) {

    private var networkState: NetworkState? = null

    private val pos: Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            ITEM -> return CollectionsViewHolder.create(parent, photoItemCallback)
            LOADING -> return NetworkStateViewHolder.create(parent, networkItemCallback)
            else -> throw IllegalArgumentException("unknown view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            ITEM -> (holder as CollectionsViewHolder).bindTo(getItem(position)!!)
            LOADING -> (holder as NetworkStateViewHolder).bindTo(networkState!!)
        }
    }

    private fun hasExtraRow(): Boolean {
        return networkState != null && networkState != NetworkState.LOADED
    }

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) {
            LOADING
        } else {
            ITEM
        }
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasExtraRow()) 1 else 0
    }

    fun getCollectionClicked(pos: Int): PhotoCollection? {
        return getItem(pos)
    }*/

    /**
     * Set the current network state to the adapter
     * but this work only after the initial load
     * and the adapter already have list to add new loading raw to it
     * so the initial loading state the activity responsible for handle it
     *
     * @param newNetworkState the new network state
     */
 /*   fun setNetworkState(newNetworkState: NetworkState) {
        //todo - issue with refresh action: after rferesh the recyclerview focus at the bottom
        //to fix this : check if tis is initial load or not
        if (currentList != null) {
            if (currentList!!.size != 0) {
                val previousState = this.networkState
                val hadExtraRow = hasExtraRow()
                this.networkState = newNetworkState
                val hasExtraRow = hasExtraRow()
                if (hadExtraRow != hasExtraRow) {
                    if (hadExtraRow) {
                        notifyItemRemoved(super.getItemCount())
                    } else {
                        notifyItemInserted(super.getItemCount())
                    }
                } else if (hasExtraRow && previousState != newNetworkState) {
                    notifyItemChanged(itemCount - 1)
                }
            }
        }
    }

    companion object {

        val ITEM = 0
        val LOADING = 1

        private val collectionDiffCallback = object : DiffUtil.ItemCallback<PhotoCollection>() {
            override fun areItemsTheSame(oldItem: PhotoCollection, newItem: PhotoCollection): Boolean {
                return oldItem.id === newItem.id
            }

            override fun areContentsTheSame(oldItem: PhotoCollection, newItem: PhotoCollection): Boolean {
                return oldItem == newItem
            }
        }
    }


}*/