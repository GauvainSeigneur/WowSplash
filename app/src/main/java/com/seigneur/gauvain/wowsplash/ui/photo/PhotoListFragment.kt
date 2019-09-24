package com.seigneur.gauvain.wowsplash.ui.photo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.DisplayMetrics
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.seigneur.gauvain.wowsplash.R
import com.seigneur.gauvain.wowsplash.business.paginationInteractor.photo.PhotosDataSource
import com.seigneur.gauvain.wowsplash.data.model.photo.Photo
import com.seigneur.gauvain.wowsplash.ui.base.paging.NetworkItemCallback
import com.seigneur.gauvain.wowsplash.data.model.network.NetworkState
import com.seigneur.gauvain.wowsplash.data.model.photo.PhotoItem
import com.seigneur.gauvain.wowsplash.ui.base.PhotoViewModel
import com.seigneur.gauvain.wowsplash.ui.base.paging.adapter.BasePagedListAdapter
import com.seigneur.gauvain.wowsplash.ui.base.paging.fragment.BasePagingFragment
import com.seigneur.gauvain.wowsplash.ui.base.paging.viewModel.BasePagingListViewModel
import com.seigneur.gauvain.wowsplash.ui.list.photo.PhotoItemCallback
import com.seigneur.gauvain.wowsplash.ui.list.photo.PhotoListAdapter
import com.seigneur.gauvain.wowsplash.ui.list.photo.PhotoViewHolder
import com.seigneur.gauvain.wowsplash.ui.main.MainActivity
import com.seigneur.gauvain.wowsplash.ui.photoDetails.PhotoDetailsActivity
import com.seigneur.gauvain.wowsplash.utils.event.EventObserver
import kotlinx.android.synthetic.main.layout_refresh_list.*
import kotlinx.android.synthetic.main.list_item_network_state.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class PhotoListFragment : BasePagingFragment<PhotosDataSource, Long, Photo>(),
    PhotoItemCallback, NetworkItemCallback {

    private var mTypeOfPhoto: String? = null
    private val displayMetrics = DisplayMetrics()

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

    private val photoListViewModel by viewModel<PhotoListViewModel>()
    private val photoViewModel by viewModel<PhotoViewModel>()

    private val photoListAdapter: PhotoListAdapter by lazy {
        PhotoListAdapter(this, this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val windowManager = context?.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        windowManager.defaultDisplay.getMetrics(displayMetrics)

        val bundle = this.arguments
        if (bundle != null) {
            mTypeOfPhoto = bundle.getString(LIST_ARG)
        }
        photoListViewModel.init(mTypeOfPhoto)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        retryLoadingButton.setOnClickListener {
            photoListViewModel.retry()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode== AppCompatActivity.RESULT_OK) {
            Timber.d("lol is ok")
        }
    }

    override fun subscribeToLiveData() {
        photoListViewModel.list?.observe(
            viewLifecycleOwner, Observer<PagedList<Photo>> {
                photoListAdapter.submitList(it)

            })

        photoListViewModel.networkState.observe(viewLifecycleOwner, Observer<NetworkState> {
            photoListAdapter.setNetworkState(it!!)
        })


        photoViewModel.photoItemViewModel.observe(this, Observer {
            manageLikeEvent(it)
        })

        photoViewModel.onDisplayLoginRequestedMessage.observe(viewLifecycleOwner, EventObserver {
            (mParentActivity as MainActivity).displayRequestLoginSnackBar()
        })

        photoListViewModel.goToDetailsEvent.observe(viewLifecycleOwner, EventObserver {
            val i = Intent(activity, PhotoDetailsActivity::class.java)
            startActivityForResult(i, PhotoDetailsActivity.PHOTO_DETAILS_RESULT_CODE)
        })
    }

    override val listAdapter: BasePagedListAdapter<*, *>
        get() = photoListAdapter

    override val vm: BasePagingListViewModel<PhotosDataSource, Long, Photo>
        get() = photoListViewModel

    override fun initAdapter() {
        photoList.setItemViewCacheSize(10)

        val screenHeight = displayMetrics.heightPixels

        context?.let {
            val layoutManager =
                PreCachingLayoutManager(it, LinearLayoutManager.VERTICAL, false)//GridLayoutManager(context, 1)
            layoutManager.setExtraLayoutSpace(screenHeight)
            photoList.layoutManager = layoutManager
        }
        photoList.adapter.let {
            photoList.adapter = photoListAdapter
        }

    }

    override fun onPhotoClicked(position: Int) {
        val item = photoListAdapter.getPhotoFromPos(position)
        item?.let {
            photoViewModel.photoItem = PhotoItem(it, position)
            photoListViewModel.setPhotoClicked(item, position)
        }
    }

    override fun onPhotoLiked(position: Int, isLiked: Boolean) {
        val item = photoListAdapter.getPhotoFromPos(position)
        item?.let {
            photoViewModel.photoItem = PhotoItem(it, position)
            photoViewModel.likePhoto(isLiked)
        }

    }

    override fun retry() {
        photoListViewModel.retry()
    }

    private fun manageLikeEvent(photoItem: PhotoItem) {
        val holder = photoList.findViewHolderForLayoutPosition(photoItem.position) as? PhotoViewHolder
        holder?.likeThePhoto(photoItem.photo.liked_by_user)
        val item = photoListAdapter.getPhotoFromPos(photoItem.position)
        item?.liked_by_user = photoItem.photo.liked_by_user
        //Give the time to the animation before update the RecyclerView
        Handler().postDelayed({
            photoListAdapter.notifyItemChanged(photoItem.position, item)
        }, resources.getInteger(R.integer.duration_avd_like_unlike).toLong())

    }
}
