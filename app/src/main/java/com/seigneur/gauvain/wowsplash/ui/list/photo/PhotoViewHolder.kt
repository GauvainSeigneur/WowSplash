package com.seigneur.gauvain.wowsplash.ui.list.photo

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.util.DisplayMetrics
import android.view.*
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.seigneur.gauvain.wowsplash.R
import com.seigneur.gauvain.wowsplash.data.model.photo.Photo
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.seigneur.gauvain.wowsplash.data.model.user.User
import com.seigneur.gauvain.wowsplash.utils.safeClick.setSafeOnClickListener
import timber.log.Timber
import javax.sql.DataSource

class PhotoViewHolder private constructor(itemView: View, private val mPhotoItemCallback: PhotoItemCallback) :
    RecyclerView.ViewHolder(itemView) {

    private val photoImage = itemView.findViewById(R.id.photoImage) as ImageView
    private val photoImageParent = itemView.findViewById(R.id.photoImageParent) as FrameLayout
    private val userPic = itemView.findViewById(R.id.userPic) as ImageView
    private val userName = itemView.findViewById<TextView>(R.id.userName)

    private val displayMetrics = DisplayMetrics()
    private val wm = itemView.context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    private val screenWidth: Int

    init {
        photoImage.setSafeOnClickListener { view ->
           mPhotoItemCallback.onPhotoClicked(adapterPosition)
        }
        wm.defaultDisplay.getMetrics(displayMetrics)
        screenWidth = displayMetrics.widthPixels
    }

    fun bindTo(photo: Photo) {
        resize(photo)
        loadImage(photo)
        photo.user?.let {
            bindUserInfo(it)
        }
    }

    private fun loadImage(photo: Photo) {
        Glide.with(itemView.context)
            .load(photo.urls.regular)
            .thumbnail(
                Glide.with(itemView.context)
                    .load(photo.urls.thumb)
            )
            .apply(
                RequestOptions()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .error(R.drawable.ic_circle_info_24px)
                    .fallback(R.drawable.ic_circle_info_24px) //in case of null value
            )
            .error(
                Glide.with(itemView.context)
                    .load(photo.urls.small)
            ) // if an error happened, try to download a smaller image
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(photoImage)
    }

    private fun resize(photo: Photo) {
        val ratio = photo.height.toDouble() / photo.width.toDouble()
        val newHeight = screenWidth * (ratio)
        photoImage.minimumHeight = newHeight.toInt()
    }

    private fun bindUserInfo(user: User) {
        userName.text = user.username
        Glide.with(itemView.context)
            .load(user.profile_image.medium)
            .error(
                Glide.with(itemView.context)
                    .load(user.profile_image.small)
            ) // if an error happened, try to download a smaller image
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(userPic)
    }

    companion object {
        fun create(parent: ViewGroup, photoItemCallback: PhotoItemCallback): PhotoViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.list_item_photo, parent, false)
            return PhotoViewHolder(view, photoItemCallback)
        }
    }

}
