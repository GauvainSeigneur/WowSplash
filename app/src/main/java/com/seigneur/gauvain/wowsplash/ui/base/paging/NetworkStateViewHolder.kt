package com.seigneur.gauvain.wowsplash.ui.base.paging

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import com.seigneur.gauvain.wowsplash.R
import com.seigneur.gauvain.wowsplash.data.model.network.NetworkState
import com.seigneur.gauvain.wowsplash.data.model.network.Status


class NetworkStateViewHolder(
    itemView: View,
    networkItemCallback: NetworkItemCallback
) : RecyclerView.ViewHolder(itemView) {

    private val errorMessageTextView = itemView.findViewById(R.id.errorMessageTextView) as TextView
    private val retryLoadingButton = itemView.findViewById(R.id.retryLoadingButton) as Button
    private val loadingProgressBar = itemView.findViewById(R.id.loadingProgressBar) as ProgressBar


    init {
        retryLoadingButton.setOnClickListener { _ -> networkItemCallback.retry() }
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

        fun create(parent: ViewGroup, networkItemCallback: NetworkItemCallback): NetworkStateViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.list_item_network_state, parent, false)
            return NetworkStateViewHolder(view, networkItemCallback)
        }
    }

}
