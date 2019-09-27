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
import com.seigneur.gauvain.wowsplash.data.model.photo.PhotoCollection
import com.seigneur.gauvain.wowsplash.data.model.photo.PhotoItem
import com.seigneur.gauvain.wowsplash.ui.base.paging.NetworkItemCallback
import com.seigneur.gauvain.wowsplash.ui.addToCollections.list.AddUserCollectionsItemCallback
import com.seigneur.gauvain.wowsplash.ui.addToCollections.list.AddUserCollectionsListAdapter
import kotlinx.android.synthetic.main.bottom_sheet_dialog_add_to_collections.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.KoinComponent

class AddToCollectionsBottomSheetDialog:BottomSheetDialogFragment(), KoinComponent, NetworkItemCallback,
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

    private val userCollectionListViewModel by viewModel<AddToCollectionsListViewModel>()

    private val userCollectionAdapter: AddUserCollectionsListAdapter by lazy {
        AddUserCollectionsListAdapter(this, this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            val photoItem = it.getParcelable(PHOTO_ITEM_KEY) as? PhotoItem
        }
        userCollectionListViewModel.init()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.bottom_sheet_dialog_add_to_collections, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setInitialLoadingState(NetworkState.LOADED)
        initAdapter()
        listenLiveData()
        listenScrollEvent()
        listenScrollEvent()
    }

    override fun onAddClicked(position: Int) {

    }

    override fun retry() {

    }

    /**
     * Show the current network state for the first load when the user list
     * in the adapter is empty and disable swipe to scroll at the first loading
     *
     * @param networkState the new network state
     */
    private fun setInitialLoadingState(networkState: NetworkState) {
    }

    private fun initAdapter(){
        context?.let {
            val layoutManager =
                LinearLayoutManager(it)
            userCollectionsList.layoutManager = layoutManager
        }
        userCollectionsList.adapter.let {
            userCollectionsList.adapter = userCollectionAdapter
        }
    }

    private fun  listenScrollEvent(){
        nestedScrollView.setOnScrollChangeListener(object:NestedScrollView.OnScrollChangeListener {
            override fun onScrollChange(
                v: NestedScrollView,
                scrollX: Int,
                scrollY: Int,
                oldScrollX: Int,
                oldScrollY: Int
            ) {
                header.isSelected = v.canScrollVertically(-1)
            }
        })
    }

    private fun listenLiveData(){

        userCollectionListViewModel.list?.observe(
            viewLifecycleOwner, Observer<PagedList<PhotoCollection>> {
                userCollectionAdapter.submitList(it)
            })
    }
}