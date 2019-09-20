package com.seigneur.gauvain.wowsplash.ui.photo

import android.app.Activity
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.view.WindowManager
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.recyclerview.widget.GridLayoutManager
import com.seigneur.gauvain.wowsplash.business.paginationInteractor.photo.PhotosDataSource
import com.seigneur.gauvain.wowsplash.data.model.photo.Photo
import com.seigneur.gauvain.wowsplash.ui.base.paging.NetworkItemCallback
import com.seigneur.gauvain.wowsplash.data.model.network.NetworkState
import com.seigneur.gauvain.wowsplash.ui.base.paging.adapter.BasePagedListAdapter
import com.seigneur.gauvain.wowsplash.ui.base.paging.fragment.BasePagingFragment
import com.seigneur.gauvain.wowsplash.ui.base.paging.viewModel.BasePagingListViewModel
import com.seigneur.gauvain.wowsplash.ui.list.photo.PhotoItemCallback
import com.seigneur.gauvain.wowsplash.ui.list.photo.PhotoListAdapter
import com.seigneur.gauvain.wowsplash.ui.photoDetails.PhotoDetailsActivity
import com.seigneur.gauvain.wowsplash.utils.event.EventObserver
import kotlinx.android.synthetic.main.layout_refresh_list.*
import kotlinx.android.synthetic.main.list_item_network_state.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber


class PhotoListFragment : BasePagingFragment<PhotosDataSource, Long, Photo>(),
    PhotoItemCallback, NetworkItemCallback {

    private var mTypeOfPhoto: String? = null

    companion object {
        private val LIST_ARG = "Photo_list_arg"
        fun newInstance(photoListType: String?): PhotoListFragment {
            val args: Bundle = Bundle()
            args.putSerializable(LIST_ARG, photoListType)
            val fragment = PhotoListFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private val mHomeViewModel by viewModel<PhotoViewModel>()

    private val photoListAdapter: PhotoListAdapter by lazy {
        PhotoListAdapter(this, this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = this.arguments
        if (bundle != null) {
            mTypeOfPhoto = bundle.getString(LIST_ARG)
        }
        mHomeViewModel.init(mTypeOfPhoto)
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

        Timber.d("vm $mHomeViewModel")

        mHomeViewModel.networkState.observe(viewLifecycleOwner, Observer<NetworkState> {
            photoListAdapter.setNetworkState(it!!)
        })

        mHomeViewModel.goToDetailsEvent.observe(viewLifecycleOwner, EventObserver<Int> {
            Timber.d("goToDetailsEvent change")
            var options: ActivityOptions? = null
            val i = Intent(activity, PhotoDetailsActivity::class.java)
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                options = ActivityOptions.makeSceneTransitionAnimation(
                    activity as Activity?,
                    photoList.layoutManager?.findViewByPosition(it),
                    "lol"
                    //activity!!.getString(R.string.shot_transition_name)
                )
                context?.startActivity(i /*, options!!.toBundle()*/)
            }
        })

    }

    override val listAdapter: BasePagedListAdapter<*, *>
        get() = photoListAdapter

    override val vm: BasePagingListViewModel<PhotosDataSource, Long, Photo>
        get() = mHomeViewModel

    override fun initAdapter() {
        photoList.setItemViewCacheSize(5)

        val displayMetrics = DisplayMetrics()
        val windowManager = context?.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val screenHeight = displayMetrics.heightPixels

        context?.let {
            val layoutManager = PreCachingLayoutManager(it, null, null)//GridLayoutManager(context, 1)
            layoutManager.setExtraLayoutSpace(screenHeight)
            photoList.layoutManager = layoutManager
        }

        photoList.adapter.let {
            photoList.adapter = photoListAdapter
        }

    }

    override fun onPhotoClicked(position: Int) {
        val photoItem = photoListAdapter.getPhotoFromPos(position)
        mHomeViewModel.setPhotoClicked(photoItem,position)
    }

    override fun onPhotoLiked(position: Int) {
        mHomeViewModel.likePhoto("0", position)
    }


    override fun retry() {
        mHomeViewModel.retry()
    }


}
