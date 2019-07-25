package com.seigneur.gauvain.wowsplash.ui.home

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.recyclerview.widget.GridLayoutManager
import com.seigneur.gauvain.wowsplash.R
import com.seigneur.gauvain.wowsplash.data.model.Photo
import com.seigneur.gauvain.wowsplash.ui.base.BaseFragment
import com.seigneur.gauvain.wowsplash.ui.base.pagingList.NetworkItemCallback
import com.seigneur.gauvain.wowsplash.data.model.network.NetworkState
import com.seigneur.gauvain.wowsplash.data.model.network.Status
import com.seigneur.gauvain.wowsplash.ui.home.list.adapter.PhotoItemCallback
import com.seigneur.gauvain.wowsplash.ui.home.list.adapter.PhotoListAdapter
import kotlinx.android.synthetic.main.fragment_refresh_list.*
import kotlinx.android.synthetic.main.list_item_network_state.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class PhotoFragment() : BaseFragment(), PhotoItemCallback, NetworkItemCallback {

    companion object {
        private val LIST_ARG = "Photo_list_arg"
        fun newInstance(photoListType: String?): PhotoFragment {
            val args: Bundle = Bundle()
            args.putSerializable(LIST_ARG, photoListType)
            val fragment = PhotoFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private val mHomeViewModel by viewModel<PhotoViewModel>()
    private lateinit var mGridLayoutManager:GridLayoutManager

    private val photoListAdapter: PhotoListAdapter by lazy {
        PhotoListAdapter(this, this)
    }

    override val fragmentLayout: Int
        get() = R.layout.fragment_refresh_list

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = this.arguments
        if (bundle != null) {
            val string = bundle.getString(LIST_ARG)
            mHomeViewModel.init(string)
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        initSwipeToRefresh()
        retryLoadingButton.setOnClickListener {
            mHomeViewModel.retry()
        }
    }

    override fun subscribeToLiveData() {
        mHomeViewModel.list?.observe(
            viewLifecycleOwner, Observer<PagedList<Photo>> {
                photoListAdapter.submitList(it)

            })

        mHomeViewModel.networkState.observe(viewLifecycleOwner, Observer<NetworkState> {
            photoListAdapter.setNetworkState(it!!)
        })
    }

    private fun initAdapter() {
        if (photoList.layoutManager==null && photoList.adapter==null) {
            mGridLayoutManager = GridLayoutManager(context, 1)
            photoList.layoutManager =  GridLayoutManager(context, 1)
            photoList.adapter = photoListAdapter
        }

    }

    override fun onShotClicked(position: Int) {
        val photoItem = photoListAdapter.getPhotoFromPos(position)
        mHomeViewModel.likePhoto(photoItem?.id)
    }

    /**
     * Init swipe to refresh and enable pull to refresh only when there are items in the adapter
     */
    private fun initSwipeToRefresh() {
       mHomeViewModel.refreshState.observe(this,
            Observer<NetworkState> { networkState ->
                if (networkState != null) {
                    if (photoListAdapter.currentList != null) {
                        if (photoListAdapter.currentList!!.size > 0) {
                            photoSwipeRefreshLayout.isRefreshing = networkState.status == NetworkState.LOADING.status
                            setInitialLoadingState(networkState)
                            Timber.d("networkState A")
                        } else {
                            Timber.d("networkState B")
                            setInitialLoadingState(networkState)
                            if (photoSwipeRefreshLayout.isRefreshing) {
                                photoSwipeRefreshLayout.isRefreshing=false
                                Timber.d("networkState B1")
                            }
                        }
                    } else {
                        Timber.d("networkState C")
                        setInitialLoadingState(networkState)
                        if (photoSwipeRefreshLayout.isRefreshing) {
                            photoSwipeRefreshLayout.isRefreshing=false
                            Timber.d("networkState C")
                        }
                    }
                }
            })
        photoSwipeRefreshLayout.setOnRefreshListener {
            Timber.d("photoSwipeRefreshLayout called")
            mHomeViewModel.refresh()
        }
    }

    /**
     * Show the current network state for the first load when the user list
     * in the adapter is empty and disable swipe to scroll at the first loading
     *
     * @param networkState the new network state
     */
    private fun setInitialLoadingState(networkState: NetworkState) {
        //error message
        errorMessageTextView.visibility = if (!networkState.message.isEmpty()) View.VISIBLE else View.GONE
        errorMessageTextView.text = networkState.message
        //Visibility according to state
        retryLoadingButton.visibility = if (networkState.status == Status.FAILED) View.VISIBLE else View.GONE
        loadingProgressBar.visibility = if (networkState.status == Status.RUNNING) View.VISIBLE else View.GONE
        photoGlobalNetworkState.visibility = if (networkState.status == Status.SUCCESS) View.GONE else View.VISIBLE
        //set default state
        photoSwipeRefreshLayout.isEnabled = networkState.status == Status.SUCCESS

    }

    override fun retry() {
        mHomeViewModel.retry()
    }


}
