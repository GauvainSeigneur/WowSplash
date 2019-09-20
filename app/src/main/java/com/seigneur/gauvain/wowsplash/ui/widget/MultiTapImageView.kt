package com.seigneur.gauvain.wowsplash.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.ImageView

class MultiTapImageView
@JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : ImageView(context, attrs) {

    private val gestureDetector: GestureDetector
    private var mListener: MultiTapImageViewListener? = null

    interface MultiTapImageViewListener {
        fun onDoubleTap()
        fun onSingleTap()

    }

    init {
        gestureDetector = GestureDetector(context, GestureListener())
    }

    fun setDoubleTapListener(listener: MultiTapImageViewListener) {
        mListener = listener
    }


    // Because we call this from onTouchEvent, this code will be executed for both
    // normal touch events and for when the system calls this using Accessibility
    override fun performClick(): Boolean {
        super.performClick()
        mListener?.onSingleTap()
        return true
    }

    override fun onTouchEvent(e: MotionEvent): Boolean {
        return gestureDetector.onTouchEvent(e)
    }

    private inner class GestureListener : GestureDetector.SimpleOnGestureListener() {
        override fun onDown(e: MotionEvent): Boolean {
            return true
        }

        // event when double tap occurs
        override fun onDoubleTap(e: MotionEvent): Boolean {
            /*val x = e.x
            val y = e.y*/
            mListener?.onDoubleTap()
            return true
        }


        override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
            mListener?.onSingleTap()
            return true
        }
    }
}

