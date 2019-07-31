package com.seigneur.gauvain.wowsplash.ui.list.photo

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
import com.seigneur.gauvain.wowsplash.data.model.Photo
import com.bumptech.glide.request.RequestOptions

class PhotoViewHolder private constructor(itemView: View, private val mPhotoItemCallback: PhotoItemCallback) :
    RecyclerView.ViewHolder(itemView), View.OnClickListener {

    val photoImage = itemView.findViewById(R.id.photoImage) as ImageView

    init {
        photoImage.setOnClickListener(this)
    }

    fun bindTo(photo: Photo) {
        val photoColor = Color.parseColor(photo.color)
        val requestOptions = RequestOptions()
        requestOptions.placeholder(ColorDrawable(photoColor))
        requestOptions.error(R.drawable.ic_circle_info_24px)
        requestOptions.fallback(R.drawable.ic_circle_info_24px) //in case of null value

        Glide.with(itemView.context)
            .setDefaultRequestOptions(requestOptions)
            .load(photo.urls.small)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(photoImage)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.photoImage -> mPhotoItemCallback.onPhotoClicked(adapterPosition)
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
