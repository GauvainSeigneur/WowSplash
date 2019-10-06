package com.seigneur.gauvain.wowsplash.ui.addToCollections.list

import android.R
import android.graphics.drawable.ColorDrawable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.seigneur.gauvain.wowsplash.data.model.photo.CollectionItem
import com.seigneur.gauvain.wowsplash.data.model.photo.PhotoCollection
import com.seigneur.gauvain.wowsplash.ui.base.paging.adapter.BasePagedListAdapter
import com.seigneur.gauvain.wowsplash.ui.base.paging.NetworkItemCallback

class AddUserCollectionsListAdapter(private val itemCallback: AddUserCollectionsItemCallback,
                                    networkItemCallback: NetworkItemCallback) :
    BasePagedListAdapter<CollectionItem, RecyclerView.ViewHolder>(collectionDiffCallback,networkItemCallback) {

    override val viewHolder: RecyclerView.ViewHolder
        get() = AddUserCollectionsViewHolder.create(
            itemParentView,
            itemCallback
        )

    override fun bindItemData(holder: RecyclerView.ViewHolder, position: Int) {
        super.bindItemData(holder, position)
        (holder as AddUserCollectionsViewHolder).bindTo(getItem(position)!!)
    }

    fun getCollectionItem(pos: Int): CollectionItem? {
        return getItem(pos)
    }

    companion object {

        private val collectionDiffCallback = object : DiffUtil.ItemCallback<CollectionItem>() {
            override fun areItemsTheSame(oldItem: CollectionItem, newItem: CollectionItem): Boolean {
                return oldItem.photoCollection.id === newItem.photoCollection.id
            }

            override fun areContentsTheSame(oldItem: CollectionItem, newItem: CollectionItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}