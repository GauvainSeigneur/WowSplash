package com.seigneur.gauvain.wowsplash.ui.search.user

import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.seigneur.gauvain.wowsplash.business.paginationInteractor.search.user.SearchUserDataSource
import com.seigneur.gauvain.wowsplash.data.model.user.User
import com.seigneur.gauvain.wowsplash.ui.base.paging.NetworkItemCallback
import com.seigneur.gauvain.wowsplash.ui.base.paging.adapter.BasePagedListAdapter
import com.seigneur.gauvain.wowsplash.ui.base.paging.fragment.BaseSearchPagingFragment
import com.seigneur.gauvain.wowsplash.ui.base.paging.viewModel.BaseSearchResultViewModel
import com.seigneur.gauvain.wowsplash.ui.list.user.SearchUserListAdapter
import com.seigneur.gauvain.wowsplash.ui.search.SearchViewModel
import com.seigneur.gauvain.wowsplash.utils.event.EventObserver
import kotlinx.android.synthetic.main.fragment_search_result.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class SearchUserFragment : BaseSearchPagingFragment<SearchUserDataSource, Long, User>(), NetworkItemCallback {

    private val mSearchViewModel by sharedViewModel<SearchViewModel>(from = { parentFragment!! })
    private val mSearchUserViewModel by viewModel<SearchUserViewModel>()

    private val mSearchUserListAdapter: SearchUserListAdapter by lazy {
        SearchUserListAdapter(this)
    }

    override val listAdapter: BasePagedListAdapter<*, *>
        get() = mSearchUserListAdapter

    override val vm: BaseSearchResultViewModel<SearchUserDataSource, Long, User>
        get() = mSearchUserViewModel

    override fun submitList(list: PagedList<User>) {
        Timber.d("submit list called ")
        mSearchUserListAdapter.submitList(list)
    }

    override fun subscribeToLiveData() {
        mSearchViewModel.searchUserQuery.observe(viewLifecycleOwner, EventObserver<String> {
            performSearch(it)
        })
    }

    override fun initAdapter() {
        if (searchResultList.layoutManager == null && searchResultList.adapter == null) {
            searchResultList.layoutManager = LinearLayoutManager(context)
            searchResultList.adapter = mSearchUserListAdapter
        }
    }

    override fun retry() {

    }

}
