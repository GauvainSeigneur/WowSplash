package com.seigneur.gauvain.wowsplash.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.seigneur.gauvain.wowsplash.R
import kotlinx.android.synthetic.main.view_like_share_save.view.*


class LikeSaveShareView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val STATE_LIKED = intArrayOf(
        R.attr.state_like
    )
    private val STATE_UNLIKED = intArrayOf(
        -R.attr.state_like
    )

    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.view_like_share_save, this)
    }

    fun animHeartSateChange(isLiked:Boolean, jump: Boolean){
        if (isLiked) {
            likePhotoButton.setImageState(STATE_LIKED, true)
            if(jump) likePhotoButton.jumpDrawablesToCurrentState()
        } else {
            likePhotoButton.setImageState(STATE_UNLIKED, true)
            if(jump)likePhotoButton.jumpDrawablesToCurrentState()
        }
    }

    fun setOnIconClick(listener: OnIconClickListener) {
        likePhotoButton.setOnClickListener {
            listener.onLikeClicked()
        }
        savePhotoButton.setOnClickListener {
            listener.onSaveClicked()
        }
        sharePhotoButton.setOnClickListener {
            listener.onShareClicked()
        }
    }


    interface OnIconClickListener {
        fun onLikeClicked()
        fun onSaveClicked()
        fun onShareClicked()
    }

}
