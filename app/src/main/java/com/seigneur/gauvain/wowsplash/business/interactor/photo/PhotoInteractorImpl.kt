package com.seigneur.gauvain.wowsplash.business.interactor.photo

import com.seigneur.gauvain.wowsplash.data.TemporaryDataProvider
import com.seigneur.gauvain.wowsplash.data.model.photo.Photo
import com.seigneur.gauvain.wowsplash.data.repository.PhotoRepository
import com.seigneur.gauvain.wowsplash.di.PHOTO_DETAILS_TEMP_SCOPE_NAME
import com.seigneur.gauvain.wowsplash.di.PHOTO_DETAILS_TEMP_SCOPE_SESSION_ID
import com.seigneur.gauvain.wowsplash.ui.photo.PhotoPresenter
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import org.koin.core.KoinComponent
import org.koin.core.qualifier.named
import timber.log.Timber


class PhotoInteractorImpl(
    private val photoRepository: PhotoRepository,
    private val presenter: PhotoPresenter
) : PhotoInteractor,  KoinComponent {

    override fun likePhoto(disposable: CompositeDisposable,id: String?, pos: Int) {
        id?.let {
            disposable.add(
                photoRepository.likePhoto(id)
                    .subscribeBy(  // named arguments for lambda Subscribers
                        onSuccess = {
                            presenter.presentPhotoLiked(pos)
                        },
                        onError = {
                            presenter.presentGlobalError()
                        }
                    )
            )
        } ?: presenter.presentGlobalError()
    }

    override fun onPhotoClicked(photo: Photo?, pos:Int) {
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

