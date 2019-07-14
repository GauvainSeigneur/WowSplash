package com.seigneur.gauvain.wowsplash.ui.home.list.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import com.seigneur.gauvain.wowsplash.R
import com.seigneur.gauvain.wowsplash.ui.home.list.data.NetworkState
import com.seigneur.gauvain.wowsplash.ui.home.list.data.Status


class NetworkStateViewHolder(
    itemView: View,
    photoItemCallback: PhotoItemCallback) : RecyclerView.ViewHolder(itemView) {

    private val errorMessageTextView = itemView.findViewById(R.id.errorMessageTextView) as TextView
    private val retryLoadingButton = itemView.findViewById(R.id.retryLoadingButton) as Button
    private val loadingProgressBar = itemView.findViewById(R.id.loadingProgressBar) as ProgressBar


    init {
        retryLoadingButton.setOnClickListener { _ -> photoItemCallback.retry() }
    }

    fun bindTo(networkState: NetworkState) {
        //error message
        errorMessageTextView.visibility =
                if (networkState.message.isEmpty())
                    View.VISIBLE
                else
                    View.GONE
        errorMessageTextView.text = networkState.message

        //loading and retry
        retryLoadingButton.visibility = if (networkState.status === Status.FAILED) View.VISIBLE else View.GONE
        loadingProgressBar.visibility = if (networkState.status === Status.RUNNING) View.VISIBLE else View.GONE
    }

    companion object {

        fun create(parent: ViewGroup, photoItemCallback: PhotoItemCallback): NetworkStateViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.list_item_network_state, parent, false)
            return NetworkStateViewHolder(view, photoItemCallback)
        }
    }

}
