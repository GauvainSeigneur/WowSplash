package com.seigneur.gauvain.wowsplash.ui.list.photo

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
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

    val photoImage = itemView.findViewById(R.id.photoImage) as ImageView
    val photoImageParent = itemView.findViewById(R.id.photoImageParent) as FrameLayout
    val userPic = itemView.findViewById(R.id.userPic) as ImageView
    val userName = itemView.findViewById<TextView>(R.id.userName)

    val displayMetrics = DisplayMetrics()
    val screenWidth = displayMetrics.widthPixels

    init {
        photoImage.setSafeOnClickListener {
            mPhotoItemCallback.onPhotoClicked(adapterPosition)
        }

    }

    fun bindTo(photo: Photo) {
        //resize(photo)
        photo.user?.let {
            bindUserInfo(it)
        }
        val photoColor = Color.parseColor(photo.color)
        photoImageParent.setBackgroundColor(photoColor)
        Glide.with(itemView.context)
            .load(photo.urls.full)
            .thumbnail(
                Glide.with(itemView.context)
                    .load(photo.urls.thumb)
                    .listener(object : RequestListener<Drawable> {
                        override fun onLoadFailed(
                            p0: GlideException?,
                            p1: Any?,
                            p2: Target<Drawable>?,
                            p3: Boolean
                        ): Boolean {
                            return false
                        }

                        override fun onResourceReady(
                            resource: Drawable?,
                            model: Any?,
                            target: Target<Drawable>?,
                            dataSource: com.bumptech.glide.load.DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {
                            //resize(photo)
                            return false
                        }
                    })
            )
            .apply(
                RequestOptions()
                    .placeholder(ColorDrawable(photoColor))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .error(R.drawable.ic_circle_info_24px)
                    .fallback(R.drawable.ic_circle_info_24px) //in case of null value
            )
            .transition(DrawableTransitionOptions.withCrossFade(100))
            .into(photoImage)

    }

    private fun resize(photo: Photo) {
        photoImageParent.viewTreeObserver.addOnGlobalLayoutListener(
            object : ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    photoImageParent.viewTreeObserver.removeOnGlobalLayoutListener(this);

                    val params = photoImageParent.layoutParams as ConstraintLayout.LayoutParams
                    val ratio: Double = (photo.height.toDouble() / photo.width.toDouble())
                    val newHeight = photoImageParent.width * (ratio)
                    params.height = newHeight.toInt()
                    params.width = ConstraintLayout.LayoutParams.MATCH_PARENT
                    photoImageParent.layoutParams = params
                }
            })
    }

    private fun bindUserInfo(user: User) {
        userName.text = user.username
        Glide.with(itemView.context)
            .load(user.profile_image.medium)
            .apply(
                RequestOptions().circleCrop()
            )
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
