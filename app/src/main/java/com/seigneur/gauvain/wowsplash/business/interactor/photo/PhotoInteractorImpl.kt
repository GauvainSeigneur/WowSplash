package com.seigneur.gauvain.wowsplash.business.interactor.photo

import com.seigneur.gauvain.wowsplash.data.TemporaryDataProvider
import com.seigneur.gauvain.wowsplash.data.model.photo.Photo
import com.seigneur.gauvain.wowsplash.data.repository.PhotoRepository
import com.seigneur.gauvain.wowsplash.data.repository.UserRepository
import com.seigneur.gauvain.wowsplash.di.PHOTO_DETAILS_TEMP_SCOPE_NAME
import com.seigneur.gauvain.wowsplash.di.PHOTO_DETAILS_TEMP_SCOPE_SESSION_ID
import com.seigneur.gauvain.wowsplash.ui.photo.PhotoListPresenter
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import org.koin.core.KoinComponent
import org.koin.core.qualifier.named

class PhotoInteractorImpl(
    private val photoRepository: PhotoRepository,
    private val userRepository: UserRepository,
    private val presenter: PhotoListPresenter
) : PhotoInteractor, KoinComponent {

    override fun likePhoto(
        disposable: CompositeDisposable,
        id: String?,
        pos: Int,
        isLiked: Boolean
    ) {
        if (userRepository.isConnected) {
            //just presenter the like/unlike animation first
            //make the request after
            presenter.presentPhotoLiked(pos, isLiked)
            id?.let {
                if (isLiked) {
                    likeThePhoto(disposable, it, pos)
                } else {
                    unLikeThePhoto(disposable, it, pos)
                }

            } ?: presenter.presentGlobalError()
        } else {
            presenter.presentLoginRequestedMessage()
        }
    }

    private fun likeThePhoto(
        disposable: CompositeDisposable, id: String,
        pos: Int
    ) {
        disposable.add(
            photoRepository.likePhoto(id)
                .subscribeBy(
                    onSuccess = {
                        presenter.updateDataPhotoLiked(pos, it)
                    },
                    onError = {
                        presenter.presentGlobalError()
                    }
                )
        )
    }

    private fun unLikeThePhoto(
        disposable: CompositeDisposable, id: String,
        pos: Int
    ) {

    }

    override fun onPhotoClicked(photo: Photo?, pos: Int) {
        photo?.let {
            //Create a session for tempProvider
            val temporaryDataProviderSession =
                getKoin().getOrCreateScope(PHOTO_DETAILS_TEMP_SCOPE_SESSION_ID, named(PHOTO_DETAILS_TEMP_SCOPE_NAME))
            //Create an instance from that session
            val temporaryDataProvider = temporaryDataProviderSession.get<TemporaryDataProvider>()
            //store data
            temporaryDataProvider.photoClicked.postValue(photo)
        }
        presenter.presentPhotoDetails(pos)
    }


}

