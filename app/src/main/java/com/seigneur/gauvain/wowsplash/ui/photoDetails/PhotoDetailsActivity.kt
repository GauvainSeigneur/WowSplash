package com.seigneur.gauvain.wowsplash.ui.photoDetails

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.app.ActionBar
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.seigneur.gauvain.wowsplash.R
import com.seigneur.gauvain.wowsplash.data.model.photo.Photo
import kotlinx.android.synthetic.main.activity_photo_details.*
import com.bumptech.glide.request.target.Target
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.seigneur.gauvain.wowsplash.utils.event.EventObserver
import org.koin.androidx.viewmodel.ext.android.viewModel
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AlphaAnimation
import android.view.animation.DecelerateInterpolator
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import androidx.palette.graphics.Palette
import com.seigneur.gauvain.wowsplash.utils.ImageUtils
import com.seigneur.gauvain.wowsplash.utils.MyColorUtils
import timber.log.Timber

class PhotoDetailsActivity : AppCompatActivity() {

    private val mPhotoDetailsViewModel by viewModel<PhotoDetailsViewModel>()

    private val appBarOffsetListener: AppBarLayout.OnOffsetChangedListener =
        AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            val vTotalScrollRange = appBarLayout.totalScrollRange
            val vRatio = (vTotalScrollRange.toFloat() + verticalOffset) / vTotalScrollRange
            manageImageAspectNewStyle(vRatio)
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_details)
        postponeEnterTransition()//todo. make a sharedtranstion to make it work!

        mPhotoDetailsViewModel.getPhotoClicked().observe(this, EventObserver<Photo> {
            resizePhotoImageView(it)
            val photoColor = Color.parseColor(it.color)
            setUpFirstColors(photoColor)
            loadShotImage(true, it)
        })

        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

    }

    private fun resizePhotoImageView(photo: Photo) {
        val params = photoImage.layoutParams as CollapsingToolbarLayout.LayoutParams
        val ratio: Double = (photo.height.toDouble() / photo.width.toDouble())
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val screenWidth = displayMetrics.widthPixels

        val newHeight = screenWidth * (ratio)
        params.height = newHeight.toInt()
        params.width = CollapsingToolbarLayout.LayoutParams.MATCH_PARENT
        // existing height is ok as is, no need to edit it
        photoImage.layoutParams = params
        imageScrim.layoutParams = params
        fullImageViewAccessView.layoutParams = params

    }

    private fun loadShotImage(isTransactionPostponed: Boolean, photo: Photo?) {
        /*if (isTransactionPostponed)
            postponeEnterTransition()*/
        /**
         * AS shot image is loaded from Glide ressource, put listener to define when to start startPostponedEnterTransition
         */
        Glide
            .with(this)
            .load(photo?.urls?.full)
            .apply(
                RequestOptions()
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .error(R.drawable.ic_circle_info_24px)
            )
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any,
                    target: Target<Drawable>,
                    isFirstResource: Boolean
                ): Boolean {
                    resizePhotoImageView(photo!!)
                    //do something! - make an API call or load another image and call mShotDetailPresenter.onShotImageAvailable();
                    if (isTransactionPostponed) {
                        setUpPhotoInfo(photo, false, null)
                    }

                    return false
                }

                override fun onResourceReady(
                    resource: Drawable,
                    model: Any,
                    target: Target<Drawable>,
                    dataSource: DataSource,
                    isFirstResource: Boolean
                ): Boolean {
                    resizePhotoImageView(photo!!)
                    if (isTransactionPostponed) {
                        //TODO - singleLive event and try reload it
                        setUpPhotoInfo(photo, true, resource)
                    }
                    return false
                }
            })
            .into(photoImage)
    }

    private fun setUpPhotoInfo(shot: Photo?, isImageReady: Boolean, drawable: Drawable?) {
        startPostponedEnterTransition() //todo reactivate if use sharedtransition
        if (isImageReady && drawable != null)
            adaptColorToPhoto(drawable)
        initImageScrollBehavior()
        //setUpShotInfo(shot)*/
    }


    /**
     * set up first color, to not let background empty
     */
    private fun setUpFirstColors(color: Int) {
        //window.statusBarColor = color
        //animateStatusBarColorChange(100L, color)
        photoImage.setBackgroundColor(color)
    }

    /**
     * When photo is ready change color top adapt layout to it
     */
    private fun adaptColorToPhoto(resource: Drawable) {
        val bitmap = ImageUtils.drawableToBitmap(resource)
        recolorFromPhotoPalette(bitmap)
        //recolorShadowColor(bitmap)
    }

    private fun recolorFromPhotoPalette(bitmap: Bitmap) {
        val twentyFourDip = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            24f, this.resources.displayMetrics
        ).toInt()
        Palette.from(bitmap)
            .clearFilters()
            .setRegion(0, 0, bitmap.width, twentyFourDip)
            .generate { palette ->
                //work with the palette here
                val defaultValue = 0x000000
                val dominantColor = palette?.getDominantColor(defaultValue)
                val dominantSwatch = palette?.dominantSwatch
                // finally change the color
                if (dominantSwatch != null) {
                    imageScrim.setBackgroundColor(dominantSwatch.rgb)
                    animateColorChange(100L, dominantSwatch.rgb, dominantColor)
                }
            }
    }

    private fun animateColorChange(duration: Long, secondColor: Int, dominantColor: Int?) {
        val mColorAnimation = ValueAnimator.ofObject(ArgbEvaluator(), window.statusBarColor, secondColor)
        mColorAnimation.duration = duration
        mColorAnimation.interpolator = AccelerateDecelerateInterpolator()
        mColorAnimation.addUpdateListener { animator ->
            val color = animator.animatedValue as Int
            // change status, navigation, and action bar color
            window.statusBarColor = color
            dominantColor?.let {
                if (!MyColorUtils.isDark(it)) {
                    toolbar.navigationIcon?.setTint(
                        ContextCompat.getColor(
                            this@PhotoDetailsActivity,
                            R.color.colorBlack
                        )
                    )
                } else {
                    toolbar.navigationIcon?.setTint(
                        ContextCompat.getColor(
                            this@PhotoDetailsActivity,
                            R.color.colorWhite
                        )
                    )
                }
            }
        }

        mColorAnimation.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                super.onAnimationEnd(animation)
                dominantColor?.let {
                    if (!MyColorUtils.isDark(it)) {
                        MyColorUtils.setLightStatusBar(window.decorView)
                    } else {
                        MyColorUtils.clearLightStatusBar(window.decorView)
                    }
                }

            }

        })

        mColorAnimation.start()
    }

    private fun initImageScrollBehavior() {
        appBarLayout.addOnOffsetChangedListener(appBarOffsetListener)
    }

    private fun manageImageAspectNewStyle(vRatio: Float) {
        imageScrim.alpha = (vRatio * -0.7f) + 0.7f
        val scale = vRatio + (1.2f - 1.2f * vRatio)
        photoImage.scaleX = scale
        photoImage.scaleY = scale
    }


}