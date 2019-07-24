package com.seigneur.gauvain.wowsplash.ui.user

import android.content.Context
import android.os.Bundle
import android.view.View
import com.seigneur.gauvain.wowsplash.R
import com.seigneur.gauvain.wowsplash.ui.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserFragment : BaseFragment() {

    private val mUserViewModel by viewModel<UserViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onCreateView(inRootView: View, inSavedInstanceState: Bundle?) {
        mUserViewModel.getMe()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override val fragmentLayout: Int
        get() = R.layout.fragment_user

}
