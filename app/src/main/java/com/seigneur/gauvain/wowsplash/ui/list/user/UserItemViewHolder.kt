package com.seigneur.gauvain.wowsplash.ui.list.user

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.seigneur.gauvain.wowsplash.R
import com.bumptech.glide.request.RequestOptions
import com.seigneur.gauvain.wowsplash.data.model.user.User

class UserItemViewHolder private constructor(itemView: View) :
    RecyclerView.ViewHolder(itemView) {

    val userProfilePic = itemView.findViewById(R.id.userProfile) as ImageView
    val userName = itemView.findViewById(R.id.userName) as TextView

    init {

    }

    fun bindTo(user: User) {
        val requestOptions = RequestOptions()
        requestOptions.circleCrop()
        requestOptions.error(R.drawable.ic_circle_info_24px)
        requestOptions.fallback(R.drawable.ic_circle_info_24px) //in case of null value

        Glide.with(itemView.context)
            .setDefaultRequestOptions(requestOptions)
            .load(user.profile_image.medium)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(userProfilePic)

        userName.text = user.username
    }


    companion object {
        fun create(parent: ViewGroup): UserItemViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.list_item_search_user, parent, false)
            return UserItemViewHolder(view)
        }
    }

}
