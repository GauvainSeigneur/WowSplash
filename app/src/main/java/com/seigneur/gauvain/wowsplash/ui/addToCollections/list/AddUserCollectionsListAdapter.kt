package com.seigneur.gauvain.wowsplash.ui.addToCollections.list

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.seigneur.gauvain.wowsplash.data.model.photo.PhotoCollection
import com.seigneur.gauvain.wowsplash.ui.base.paging.adapter.BasePagedListAdapter
import com.seigneur.gauvain.wowsplash.ui.base.paging.NetworkItemCallback

class AddUserCollectionsListAdapter(private val itemCallback: AddUserCollectionsItemCallback,
                                    networkItemCallback: NetworkItemCallback) :
    BasePagedListAdapter<PhotoCollection, RecyclerView.ViewHolder>(collectionDiffCallback,networkItemCallback) {

    override val viewHolder: RecyclerView.ViewHolder
        get() = AddUserCollectionsViewHolder.create(
            itemParentView,
            itemCallback
        )

    override fun bindItemData(holder: RecyclerView.ViewHolder, position: Int) {
        super.bindItemData(holder, position)
        (holder as AddUserCollectionsViewHolder).bindTo(getItem(position)!!)
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