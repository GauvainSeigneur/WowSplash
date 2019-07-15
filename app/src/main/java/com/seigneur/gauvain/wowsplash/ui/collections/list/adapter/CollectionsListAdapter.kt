package com.seigneur.gauvain.wowsplash.ui.collections.list.adapter

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.seigneur.gauvain.wowsplash.data.model.PhotoCollection
import com.seigneur.gauvain.wowsplash.ui.base.pagingList.BasePagedListAdapter
import com.seigneur.gauvain.wowsplash.ui.base.pagingList.NetworkItemCallback

class CollectionsListAdapter(private val photoItemCallback: CollectionsItemCallback,
                             networkItemCallback: NetworkItemCallback) :
    BasePagedListAdapter<PhotoCollection, RecyclerView.ViewHolder>(collectionDiffCallback,networkItemCallback) {

    override val viewHolder: RecyclerView.ViewHolder
        get() =  CollectionsViewHolder.create(itemParentView, photoItemCallback)

    override fun bindItemData(holder: RecyclerView.ViewHolder, position: Int) {
        super.bindItemData(holder, position)
        (holder as CollectionsViewHolder).bindTo(getItem(position)!!)
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