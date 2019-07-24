package com.seigneur.gauvain.wowsplash.ui.search

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.seigneur.gauvain.wowsplash.R
import com.seigneur.gauvain.wowsplash.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_search.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class SearchFragment : BaseFragment() {

    private val mSearchViewModel by viewModel<SearchViewModel>()

    override fun onCreateView(inRootView: View, inSavedInstanceState: Bundle?) {
        //mSearchViewModel.searchPhotos("BasePagedListAdapter")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //listen LiveData
        yoloButton.setOnClickListener {
            mSearchViewModel.searchPhoto()
        }
    }

    override val fragmentLayout: Int
        get() = R.layout.fragment_search

}
