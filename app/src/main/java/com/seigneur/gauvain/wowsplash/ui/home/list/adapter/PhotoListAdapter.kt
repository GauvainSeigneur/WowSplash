package com.seigneur.gauvain.wowsplash.ui.home.list.adapter

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.seigneur.gauvain.wowsplash.data.model.Photo
import com.seigneur.gauvain.wowsplash.ui.base.pagingList.adapter.BasePagedListAdapter
import com.seigneur.gauvain.wowsplash.ui.base.pagingList.NetworkItemCallback

class PhotoListAdapter(private val photoItemCallback: PhotoItemCallback,
                       private val networkItemCallback: NetworkItemCallback)
    : BasePagedListAdapter<Photo, RecyclerView.ViewHolder>(UserDiffCallback,networkItemCallback) {


    override val viewHolder: RecyclerView.ViewHolder
        get() =  PhotoViewHolder.create(itemParentView, photoItemCallback)

    override fun bindItemData(holder: RecyclerView.ViewHolder, position: Int) {
        super.bindItemData(holder, position)
        (holder as PhotoViewHolder).bindTo(getItem(position)!!)
    }

    fun getShotClicked(pos: Int): Photo? {
        return getItem(pos)
    }


    companion object {

        private val UserDiffCallback = object : DiffUtil.ItemCallback<Photo>() {
            override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean {
                return oldItem.id === newItem.id
            }

            override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean {
                return oldItem == newItem
            }
        }
    }


}