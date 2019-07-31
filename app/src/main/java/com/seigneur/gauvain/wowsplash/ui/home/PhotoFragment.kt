package com.seigneur.gauvain.wowsplash.ui.home

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.recyclerview.widget.GridLayoutManager
import com.seigneur.gauvain.wowsplash.business.paginationInteractor.photo.PhotosDataSource
import com.seigneur.gauvain.wowsplash.data.model.Photo
import com.seigneur.gauvain.wowsplash.ui.base.paging.NetworkItemCallback
import com.seigneur.gauvain.wowsplash.data.model.network.NetworkState
import com.seigneur.gauvain.wowsplash.ui.base.paging.adapter.BasePagedListAdapter
import com.seigneur.gauvain.wowsplash.ui.base.paging.fragment.BasePagingFragment
import com.seigneur.gauvain.wowsplash.ui.base.paging.viewModel.BasePagingListViewModel
import com.seigneur.gauvain.wowsplash.ui.list.photo.PhotoItemCallback
import com.seigneur.gauvain.wowsplash.ui.list.photo.PhotoListAdapter
import kotlinx.android.synthetic.main.fragment_refresh_list.*
import kotlinx.android.synthetic.main.list_item_network_state.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class PhotoFragment : BasePagingFragment<PhotosDataSource, Long, Photo>(),
    PhotoItemCallback, NetworkItemCallback {

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

    override val listAdapter: BasePagedListAdapter<*, *>
        get() = photoListAdapter

    override val vm: BasePagingListViewModel<PhotosDataSource, Long, Photo>
        get() = mHomeViewModel

    override fun initAdapter() {
        if (photoList.layoutManager==null && photoList.adapter==null) {
            mGridLayoutManager = GridLayoutManager(context, 1)
            photoList.layoutManager =  GridLayoutManager(context, 1)
            photoList.adapter = photoListAdapter
        }

    }

    override fun onPhotoClicked(position: Int) {
        val photoItem = photoListAdapter.getPhotoFromPos(position)
       // mHomeViewModel.likePhoto(photoItem?.id)
    }


    override fun retry() {
        mHomeViewModel.retry()
    }


}
