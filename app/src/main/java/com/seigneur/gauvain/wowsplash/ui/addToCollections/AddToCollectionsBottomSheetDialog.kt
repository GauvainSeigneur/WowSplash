package com.seigneur.gauvain.wowsplash.ui.addToCollections

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.seigneur.gauvain.wowsplash.R
import com.seigneur.gauvain.wowsplash.data.model.network.NetworkState
import com.seigneur.gauvain.wowsplash.data.model.network.Status
import com.seigneur.gauvain.wowsplash.data.model.photo.CollectionItem
import com.seigneur.gauvain.wowsplash.data.model.photo.PhotoItem
import com.seigneur.gauvain.wowsplash.ui.base.paging.NetworkItemCallback
import com.seigneur.gauvain.wowsplash.ui.addToCollections.list.AddUserCollectionsItemCallback
import com.seigneur.gauvain.wowsplash.ui.addToCollections.list.AddUserCollectionsListAdapter
import com.seigneur.gauvain.wowsplash.utils.event.EventObserver
import kotlinx.android.synthetic.main.bottom_sheet_dialog_add_to_collections.*
import kotlinx.android.synthetic.main.list_item_network_state.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.KoinComponent
import timber.log.Timber

class AddToCollectionsBottomSheetDialog : BottomSheetDialogFragment(), KoinComponent,
    NetworkItemCallback,
    AddUserCollectionsItemCallback {

    companion object {
        val TAG = AddToCollectionsBottomSheetDialog::class.java.name
        private val PHOTO_ITEM_KEY = "photoItem"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param myObject as MyObject.
         * @return A new instance of fragment MyFragment.
         */
        fun newInstance(photoItem: PhotoItem): AddToCollectionsBottomSheetDialog {
            val fragment = AddToCollectionsBottomSheetDialog()
            val args = Bundle()
            args.putParcelable(PHOTO_ITEM_KEY, photoItem)
            fragment.arguments = args
            return fragment
        }
    }

    private var photoItem :PhotoItem? =null
    private val listViewModel by viewModel<UserCollectionsListViewModel>()
    private val viewModel by viewModel<AddToCollectionsViewModel>()

    private val userCollectionAdapter: AddUserCollectionsListAdapter by lazy {
        AddUserCollectionsListAdapter(this, this, photoItem)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            photoItem = it.getParcelable(PHOTO_ITEM_KEY) as? PhotoItem
            viewModel.photoId = photoItem?.photo?.id

            Timber.d("lol it is a ${photoItem?.photo}")
        }
        listViewModel.fetchUserName()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.bottom_sheet_dialog_add_to_collections, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setInitialLoadingState(NetworkState.LOADED)
        retryLoadingButton.setOnClickListener {
            listViewModel.retry()
        }
        initAdapter()
        listenLiveData()
        listenScrollEvent()
        listenScrollEvent()
    }

    override fun onAddClicked(position: Int) {
        val item = userCollectionAdapter.getCollectionItem(position)
        viewModel.onAddClicked(position, item)
        Timber.d("wesh on add cliccked ")
    }

    override fun retry() {
        listViewModel.retry()
    }

    /**
     * Show the current network state for the first load when the user list
     * in the adapter is empty and disable swipe to scroll at the first loading
     *
     * @param networkState the new network state
     */
    private fun setInitialLoadingState(networkState: NetworkState) {
        //error message
        errorMessageTextView.visibility =
            if (!networkState.message.isEmpty()) View.VISIBLE else View.GONE
        errorMessageTextView.text = networkState.message
        //Visibility according to state
        retryLoadingButton.visibility =
            if (networkState.status == Status.FAILED) View.VISIBLE else View.GONE
        loadingProgressBar.visibility =
            if (networkState.status == Status.RUNNING) View.VISIBLE else View.GONE
        globalNetworkState.visibility =
            if (networkState.status == Status.SUCCESS) View.GONE else View.VISIBLE
    }

    private fun initAdapter() {
        context?.let {
            val layoutManager =
                LinearLayoutManager(it)
            userCollectionsList.layoutManager = layoutManager
        }
        userCollectionsList.adapter.let {
            userCollectionsList.adapter = userCollectionAdapter
        }
    }

    private fun listenScrollEvent() {
        nestedScrollView.setOnScrollChangeListener { v: NestedScrollView,
                                                     scrollX: Int,
                                                     scrollY: Int,
                                                     oldScrollX: Int,
                                                     oldScrollY: Int ->

            header.isSelected = v.canScrollVertically(-1)
        }
    }


    private fun listenLiveData() {
        listenListLiveData() //listen first if not null
        listViewModel.listInitializedEvent.observe(viewLifecycleOwner, EventObserver {
            //listen it again when we have fetched the username
            listenListLiveData()
        })

    }

    private fun listenListLiveData() {
        listViewModel.list?.observe(
            viewLifecycleOwner, Observer<PagedList<CollectionItem>> {
                userCollectionAdapter.submitList(it)
            })

        listViewModel.initialNetworkState?.observe(viewLifecycleOwner, Observer {
            setInitialLoadingState(it)
        })

        listViewModel.networkState?.observe(viewLifecycleOwner, Observer {
            userCollectionAdapter.setNetworkState(it)
        })

        viewModel.selectedCollectionEvent.observe(viewLifecycleOwner, EventObserver {
            val listItem = userCollectionAdapter.getCollectionItem(it.position)
            listItem?.selected = it.selected
            userCollectionAdapter.notifyItemChanged(it.position)
        })


    }

}