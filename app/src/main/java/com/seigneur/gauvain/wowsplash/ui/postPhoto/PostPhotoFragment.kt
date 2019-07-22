package com.seigneur.gauvain.wowsplash.ui.postPhoto

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.seigneur.gauvain.wowsplash.R
import com.seigneur.gauvain.wowsplash.ui.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class PostPhotoFragment : BaseFragment() {

    private val mPostPhotoViewModel by viewModel<PostPhotoViewModel>()

    override fun onCreateView(inRootView: View, inSavedInstanceState: Bundle?) {
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //listen LiveData
    }

    override val fragmentLayout: Int
        get() = R.layout.fragment_post_photo

}
