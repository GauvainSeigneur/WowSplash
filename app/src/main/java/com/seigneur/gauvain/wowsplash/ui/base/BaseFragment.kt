package com.seigneur.gauvain.wowsplash.ui.base

import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity

/**
 * Base fragment which allows to not duplicate some methods in child
 * Fragment - dedicated to UI not DI
 */
abstract class BaseFragment :Fragment() {

    lateinit var mRootview: View

    val mParentActivity: FragmentActivity? by lazy {
        this.activity
    }


    /**
     * Every fragment has to inflate a layout in the onCreateView method. We have added this method to
     * avoid duplicate all the inflate code in every fragment. You only have to return the layout to
     * inflate in this method when extends BaseFragment.
     */
    protected abstract val fragmentLayout: Int

    /**
     * Inject dependencies to child fragment
     */
    override fun onAttach(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        }
        super.onAttach(context)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mRootview = inflater.inflate(fragmentLayout, container, false)
        onCreateView(mRootview, savedInstanceState)
        return mRootview

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        subscribeToLiveData()
    }

    /**
     * To be overridden
     */
    open fun subscribeToLiveData(){}

    /**
     * method to be overriden.
     * In this method, root view is already inflated and one can use below view getters
     */
    open fun onCreateView(rootView: View, savedInstanceState: Bundle?) {}

}

