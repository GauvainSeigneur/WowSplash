package com.seigneur.gauvain.wowsplash.ui.list.photo

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.seigneur.gauvain.wowsplash.R
import com.seigneur.gauvain.wowsplash.data.model.photo.Photo
import com.bumptech.glide.request.RequestOptions
import com.seigneur.gauvain.wowsplash.utils.safeClick.setSafeOnClickListener

class PhotoViewHolder private constructor(itemView: View, private val mPhotoItemCallback: PhotoItemCallback) :
    RecyclerView.ViewHolder(itemView) {

    val photoImage = itemView.findViewById(R.id.photoImage) as ImageView
    val photoImageParent = itemView.findViewById(R.id.photoImageParent) as FrameLayout

    init {
        photoImage.setSafeOnClickListener {
            mPhotoItemCallback.onPhotoClicked(adapterPosition)
        }
    }

    fun bindTo(photo: Photo) {
        val photoColor = Color.parseColor(photo.color)
        photoImageParent.setBackgroundColor(photoColor)
        Glide.with(itemView.context)
            .load(photo.urls.full)
            .apply(
                RequestOptions()
                    .placeholder(ColorDrawable(photoColor))
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .error(R.drawable.ic_circle_info_24px)
                    .fallback(R.drawable.ic_circle_info_24px) //in case of null value
            )
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(photoImage)

    }

    companion object {
        fun create(parent: ViewGroup, photoItemCallback: PhotoItemCallback): PhotoViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.list_item_photo, parent, false)
            return PhotoViewHolder(view, photoItemCallback)
        }
    }

}
