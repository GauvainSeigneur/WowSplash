package com.seigneur.gauvain.wowsplash.ui.collections.list.adapter

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.seigneur.gauvain.wowsplash.R
import com.bumptech.glide.request.RequestOptions
import com.seigneur.gauvain.wowsplash.data.model.PhotoCollection

class CollectionsViewHolder
private constructor(
    itemView: View,
    private val collectionItemCallback: CollectionsItemCallback) :
    RecyclerView.ViewHolder(itemView), View.OnClickListener {

    val shotImage = itemView.findViewById(R.id.photoImage) as ImageView

    init {
        shotImage.setOnClickListener(this)
    }

    fun bindTo(collection: PhotoCollection) {
        val photoColor = Color.parseColor(collection.cover_photo.color)
        val requestOptions = RequestOptions()
        requestOptions.placeholder(ColorDrawable(photoColor))
        requestOptions.error(R.drawable.ic_circle_info_24px)
        requestOptions.fallback(R.drawable.ic_circle_info_24px) //in case of null value

        Glide.with(itemView.context)
            .setDefaultRequestOptions(requestOptions)
            .load(collection.cover_photo.urls.small)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(shotImage)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.photoImage -> collectionItemCallback.onCollectionClicked(adapterPosition)
        }
    }

    companion object {
        fun create(parent: ViewGroup, photoItemCallback: CollectionsItemCallback): CollectionsViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.list_item_photo, parent, false)
            return CollectionsViewHolder(view, photoItemCallback)
        }
    }

}
