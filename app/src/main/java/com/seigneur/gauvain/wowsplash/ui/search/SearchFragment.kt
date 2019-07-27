package com.seigneur.gauvain.wowsplash.ui.search

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.recyclerview.widget.GridLayoutManager
import com.seigneur.gauvain.wowsplash.R
import com.seigneur.gauvain.wowsplash.data.model.Photo
import com.seigneur.gauvain.wowsplash.data.model.network.NetworkState
import com.seigneur.gauvain.wowsplash.data.model.user.User
import com.seigneur.gauvain.wowsplash.ui.base.BaseFragment
import com.seigneur.gauvain.wowsplash.ui.base.pagingList.NetworkItemCallback
import com.seigneur.gauvain.wowsplash.ui.home.list.adapter.PhotoItemCallback
import com.seigneur.gauvain.wowsplash.ui.home.list.adapter.PhotoListAdapter
import kotlinx.android.synthetic.main.fragment_refresh_list.*
import kotlinx.android.synthetic.main.fragment_search.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class SearchFragment : BaseFragment(), PhotoItemCallback, NetworkItemCallback {

    private val mSearchViewModel by viewModel<SearchViewModel>()
    private lateinit var mGridLayoutManager:GridLayoutManager
    private val photoListAdapter: PhotoListAdapter by lazy {
        PhotoListAdapter(this, this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //mSearchViewModel.init("yolo")
    }

    override fun onCreateView(inRootView: View, inSavedInstanceState: Bundle?) {
        mSearchViewModel.search("yolo")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initAdapter()
    }

    override fun subscribeToLiveData() {
        mSearchViewModel.list?.observe(
            viewLifecycleOwner, Observer<PagedList<Photo>> {
                photoListAdapter.submitList(it)

            })

    }

    override val fragmentLayout: Int
        get() = R.layout.fragment_refresh_list

    private fun initAdapter() {
        if (photoList.layoutManager==null && photoList.adapter==null) {
            mGridLayoutManager = GridLayoutManager(context, 1)
            photoList.layoutManager =  GridLayoutManager(context, 1)
            photoList.adapter = photoListAdapter
        }

    }

    override fun onShotClicked(position: Int) {

    }

    override fun retry() {

    }

}
