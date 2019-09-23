package com.seigneur.gauvain.wowsplash.business.interactor.photo

import com.seigneur.gauvain.wowsplash.data.TemporaryDataProvider
import com.seigneur.gauvain.wowsplash.data.model.photo.Photo
import com.seigneur.gauvain.wowsplash.data.model.photo.PhotoItem
import com.seigneur.gauvain.wowsplash.data.repository.AuthRepository
import com.seigneur.gauvain.wowsplash.data.repository.PhotoRepository
import com.seigneur.gauvain.wowsplash.data.repository.UserRepository
import com.seigneur.gauvain.wowsplash.di.PHOTO_DETAILS_TEMP_SCOPE_NAME
import com.seigneur.gauvain.wowsplash.di.PHOTO_DETAILS_TEMP_SCOPE_SESSION_ID
import com.seigneur.gauvain.wowsplash.ui.base.PhotoPresenter
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import org.koin.core.KoinComponent
import org.koin.core.qualifier.named
import timber.log.Timber

class PhotoInteractorImpl(
    private val photoRepository: PhotoRepository,
    private val userRepository: UserRepository,
    private val presenter: PhotoPresenter
) : PhotoInteractor, KoinComponent {

    override fun init(){
        userRepository.init()
    }

    override fun likePhoto(
        disposable: CompositeDisposable,
        photoItem: PhotoItem?,
        isLiked: Boolean
    ) {
        photoItem?.let {

            Timber.d("lol ${userRepository.isConnected}")
            if (userRepository.isConnected) {
                //just presenter the like/unlike animation first
                //make the request after
                presenter.presentPhotoLiked(it.position, isLiked)
                if (isLiked) {
                    likeThePhoto(disposable, it.photo.id, it.position)
                } else {
                    unLikeThePhoto(disposable, it.photo.id, it.position)
                }
            } else {
                presenter.presentLoginRequestedMessage()
            }
        } ?: presenter.presentGlobalError()
    }

    override fun onPhotoClicked(photo: Photo?, pos: Int) {
        photo?.let {
            //Create a session for tempProvider
            val temporaryDataProviderSession =
                getKoin().getOrCreateScope(PHOTO_DETAILS_TEMP_SCOPE_SESSION_ID, named(PHOTO_DETAILS_TEMP_SCOPE_NAME))
            //Create an instance from that session
            val temporaryDataProvider = temporaryDataProviderSession.get<TemporaryDataProvider>()
            //store data
            temporaryDataProvider.photoClicked.postValue(PhotoItem(it, pos))
        }
        presenter.presentPhotoDetails(pos)
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
        disposable.add(
            photoRepository.unlikePhoto(id)
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
}

