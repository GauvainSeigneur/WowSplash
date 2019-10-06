package com.seigneur.gauvain.wowsplash.business.interactor.photoActions

import com.seigneur.gauvain.wowsplash.data.model.photo.PhotoItem
import com.seigneur.gauvain.wowsplash.data.repository.PhotoRepository
import com.seigneur.gauvain.wowsplash.data.repository.TempDataRepository
import com.seigneur.gauvain.wowsplash.data.repository.TokenRepository
import com.seigneur.gauvain.wowsplash.data.repository.UserRepository
import com.seigneur.gauvain.wowsplash.ui.photoActions.PhotoActionsPresenter
import com.seigneur.gauvain.wowsplash.utils.event.Event
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import org.koin.core.KoinComponent
import timber.log.Timber

class PhotoActionsInteractorImpl(
    private val photoRepository: PhotoRepository,
    private val tempDataRepository: TempDataRepository,
    private val actionPresenter: PhotoActionsPresenter
) : PhotoActionsInteractor, KoinComponent {

    override fun likePhoto(
        disposable: CompositeDisposable,
        photoItem: PhotoItem,
        isActionFromDetails: Boolean
    ) {
        if (TokenRepository.accessToken != null) {
            if (photoItem.photo.liked_by_user) {
                unLikeThePhoto(disposable, photoItem.photo.id)
            } else {
                likeThePhoto(disposable, photoItem.photo.id)
            }
            photoItem.photo.liked_by_user = !photoItem.photo.liked_by_user
            actionPresenter.presentPhotoLiked(photoItem)
            if (isActionFromDetails) {
                //save this item in tempDataRepository to update the list when we go back to it
                tempDataRepository.photoItemModifiedFromDetails.postValue(Event(photoItem))
            }
        } else {
            actionPresenter.presentLoginRequestedMessage()
        }
    }

    override fun openCollectionsOptions(photoItem: PhotoItem) {
        if (TokenRepository.accessToken != null) {
            actionPresenter.presentUserCollectionsView(photoItem)
        } else {
            actionPresenter.presentLoginRequestedMessage()
        }
    }

    override fun onPhotoAddedToCollecion(
        disposable: CompositeDisposable,
        photoItem: PhotoItem,
        isActionFromDetails: Boolean
    ) {
        //todo
        //make api calls here
        ///photoItem.photo.liked_by_user = !photoItem.photo.liked_by_user
        //actionPresenter.presentPhotoRegistered(photoItem)
        if (isActionFromDetails) {
            //save this item in tempDataRepository to update the list when we go back to it
            tempDataRepository.photoItemModifiedFromDetails.postValue(Event(photoItem))
        }
    }

    private fun likeThePhoto(
        disposable: CompositeDisposable, id: String
    ) {
        disposable.add(
            photoRepository.likePhoto(id)
                .subscribeBy(
                    onSuccess = {
                        //everything is fine, so we do nothing
                    },
                    onError = {
                        actionPresenter.presentGlobalError()
                    }
                )
        )
    }

    private fun unLikeThePhoto(
        disposable: CompositeDisposable, id: String
    ) {
        disposable.add(
            photoRepository.unlikePhoto(id)
                .subscribeBy(
                    onSuccess = {
                    },
                    onError = {
                        actionPresenter.presentGlobalError()
                    }
                )
        )
    }
}

