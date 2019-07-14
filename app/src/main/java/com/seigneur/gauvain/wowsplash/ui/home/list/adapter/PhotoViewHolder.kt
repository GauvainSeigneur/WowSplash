package com.seigneur.gauvain.wowsplash.ui.home.list.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.seigneur.gauvain.wowsplash.R
import com.seigneur.gauvain.wowsplash.data.model.Photo


class PhotoViewHolder private constructor(itemView: View, private val mPhotoItemCallback: PhotoItemCallback) : RecyclerView.ViewHolder(itemView) , View.OnClickListener {

    val shotImage = itemView.findViewById(R.id.shot_image) as ImageView

    init {
        shotImage.setOnClickListener(this)
    }

    fun bindTo(shot: Photo) {
        Glide.with(itemView.context)
                .asBitmap()
                .load(shot.urls.small)
                .into(shotImage)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.shot_image -> mPhotoItemCallback.onShotClicked(adapterPosition)
        }
    }

    companion object {
        fun create(parent: ViewGroup, photoItemCallback: PhotoItemCallback): PhotoViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.list_item_photo, parent, false)
            return PhotoViewHolder(view, photoItemCallback)
        }
    }

}
