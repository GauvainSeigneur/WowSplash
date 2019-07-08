package com.seigneur.gauvain.wowsplash.ui

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.seigneur.gauvain.wowsplash.R
import com.seigneur.gauvain.wowsplash.ui.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class HomeFragment : BaseFragment() {

    private val mHomeViewModel by viewModel<HomeViewModel>()

    override fun onCreateView(inRootView: View, inSavedInstanceState: Bundle?) {
        mHomeViewModel.getPhotos()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //listen LiveData
        mHomeViewModel.mPhotoResult.observe(viewLifecycleOwner,
           Observer {
                when(it) {
                    is HomeViewModel.PhotoResult.PhotoList -> Timber.d("MY MAN is good $it")
                    is HomeViewModel.PhotoResult.PhotoError -> Timber.d("SHIIIIIT $it")
                }
            })

    }

    override val fragmentLayout: Int
        get() = R.layout.fragment_home

}
