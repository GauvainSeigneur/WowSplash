package com.seigneur.gauvain.wowsplash.ui.base.pagingList

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.seigneur.gauvain.wowsplash.data.model.network.NetworkState

abstract class BasePagedListAdapter<T, VH : RecyclerView.ViewHolder>(
    diffCallback:DiffUtil.ItemCallback<T>,
    private val networkItemCallback: NetworkItemCallback) : PagedListAdapter<T, VH>(diffCallback) {

    lateinit var itemParentView:ViewGroup
    internal var networkState: NetworkState? = null

    /**
     * To be overridden
     */
    abstract val viewHolder:RecyclerView.ViewHolder

    /**
     * Not to be overridden,
     * but if you do, make it after call super
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        itemParentView = parent
        when (viewType) {
            ITEM -> return getItemViewHolder() as VH
            LOADING -> return NetworkStateViewHolder.create(parent, networkItemCallback) as VH
            else -> throw IllegalArgumentException("unknown view type")
        }
    }

    /**
     * Not to be overridden,
     * but if you do, make it after call super
     */
    override fun onBindViewHolder(holder: VH, position: Int) {
        when (getItemViewType(position)) {
            ITEM -> bindItemData(holder, position)
            LOADING -> (holder as NetworkStateViewHolder).bindTo(networkState!!)
        }
    }

    /**
     * Not to be overridden,
     * but if you do, make it after call super
     */
    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) {
           LOADING
        } else {
            ITEM
        }
    }

    /**
     * Not to be overridden,
     * but if you do, make it after call super
     */
    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasExtraRow()) 1 else 0
    }

    /**
     * To be overridden
     */
    open fun bindItemData(holder: RecyclerView.ViewHolder, position: Int){}

    private fun hasExtraRow(): Boolean {
        return networkState != null && networkState != NetworkState.LOADED
    }

    private fun getItemViewHolder():RecyclerView.ViewHolder {
        return viewHolder
    }

    /**
     * Set the current network state to the adapter
     * but this work only after the initial load
     * and the adapter already have list to add new loading raw to it
     * so the initial loading state the activity responsible for handle it
     *
     * @param newNetworkState the new network state
     */
    fun setNetworkState(newNetworkState: NetworkState) {
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
    }

}
