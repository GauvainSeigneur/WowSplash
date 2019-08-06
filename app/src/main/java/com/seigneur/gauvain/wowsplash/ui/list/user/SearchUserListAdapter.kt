package com.seigneur.gauvain.wowsplash.ui.list.user

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.seigneur.gauvain.wowsplash.data.model.user.User
import com.seigneur.gauvain.wowsplash.ui.base.paging.adapter.BasePagedListAdapter
import com.seigneur.gauvain.wowsplash.ui.base.paging.NetworkItemCallback

class SearchUserListAdapter(private val networkItemCallback: NetworkItemCallback)
    : BasePagedListAdapter<User, RecyclerView.ViewHolder>(UserDiffCallback,networkItemCallback) {

    override val viewHolder: RecyclerView.ViewHolder
        get() = UserItemViewHolder.create(
            itemParentView
        )

    override fun bindItemData(holder: RecyclerView.ViewHolder, position: Int) {
        super.bindItemData(holder, position)
        (holder as UserItemViewHolder).bindTo(getItem(position)!!)
    }

    fun getUserFromPos(pos: Int): User? {
        return getItem(pos)
    }

    companion object {

        private val UserDiffCallback = object : DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem.id === newItem.id
            }

            override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem == newItem
            }
        }
    }


}