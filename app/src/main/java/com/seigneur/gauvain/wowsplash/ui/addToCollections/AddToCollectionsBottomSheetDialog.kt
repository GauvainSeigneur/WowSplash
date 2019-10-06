package com.seigneur.gauvain.wowsplash.ui.addToCollections

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.seigneur.gauvain.wowsplash.R
import com.seigneur.gauvain.wowsplash.data.model.network.NetworkState
import com.seigneur.gauvain.wowsplash.data.model.network.Status
import com.seigneur.gauvain.wowsplash.data.model.photo.CollectionItem
import com.seigneur.gauvain.wowsplash.data.model.photo.PhotoCollection
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

    private val listViewModel by viewModel<AddToCollectionsListViewModel>()
    private val viewModel by viewModel<AddToCollectionsViewModel>()

    private val userCollectionAdapter: AddUserCollectionsListAdapter by lazy {
        AddUserCollectionsListAdapter(this, this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            val photoItem = it.getParcelable(PHOTO_ITEM_KEY) as? PhotoItem
        }
        viewModel.fetchUserName()
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
        viewModel.userName.observe(viewLifecycleOwner, EventObserver {
            listViewModel.initList(it)
            listenListLiveData()
        })

        listenListLiveData() //call it again in case the LiveData is already created

    }

    private fun listenListLiveData() {
        listViewModel.list?.observe(
            viewLifecycleOwner, Observer<PagedList<CollectionItem>> {
                userCollectionAdapter.submitList(it)
                //listen it only when list is not empty or null!
                listenListModifications()
            })

        listViewModel.initialNetworkState?.observe(viewLifecycleOwner, Observer {
            setInitialLoadingState(it)
        })

        listViewModel.networkState?.observe(viewLifecycleOwner, Observer {
            userCollectionAdapter.setNetworkState(it)
        })


    }

    private fun listenListModifications(){
        viewModel.selectedCollections.observe(viewLifecycleOwner, Observer {
            for(item in it){
                var listItem = userCollectionAdapter.getCollectionItem(item.position)
                listItem?.selected = item.collectionItem.selected
                userCollectionAdapter.notifyItemChanged(item.position)
                Toast.makeText(context, "lol ${userCollectionAdapter.getCollectionItem(item.position)?.selected}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}