package com.seigneur.gauvain.wowsplash.ui.addToCollections.list

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
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
import com.seigneur.gauvain.wowsplash.data.model.photo.PhotoCollection

class AddUserCollectionsViewHolder
private constructor(
    itemView: View,
    private val itemCallback: AddUserCollectionsItemCallback
) :
    RecyclerView.ViewHolder(itemView), View.OnClickListener {

    val collectionCover = itemView.findViewById(R.id.collectionCover) as ImageView
    val collectionTitle = itemView.findViewById(R.id.collectionTitle) as TextView

    init {
        collectionCover.setOnClickListener(this)
    }

    fun bindTo(collection: PhotoCollection) {
        collectionTitle.text = collection.title
        val photoCover =  collection.cover_photo

        photoCover?.let {
            val photoColor = Color.parseColor(it.color)
            val requestOptions = RequestOptions()
            requestOptions.placeholder(ColorDrawable(photoColor))
            requestOptions.error(R.drawable.ic_circle_info_24px) //todo - place holder
            requestOptions.fallback(R.drawable.ic_circle_info_24px)  //todo - place holder

            Glide.with(itemView.context)
                .setDefaultRequestOptions(requestOptions)
                .load(it.urls.small)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(collectionCover)
        }

    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.photoImage -> itemCallback.onAddClicked(adapterPosition)
        }
    }

    companion object {
        fun create(parent: ViewGroup, itemCallback: AddUserCollectionsItemCallback): AddUserCollectionsViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.list_item_add_to_collection, parent, false)
            return AddUserCollectionsViewHolder(
                view,
                itemCallback
            )
        }
    }

}
