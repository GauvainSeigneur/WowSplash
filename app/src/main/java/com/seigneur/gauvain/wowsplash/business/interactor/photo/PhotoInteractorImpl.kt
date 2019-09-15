package com.seigneur.gauvain.wowsplash.business.interactor.photo

import androidx.lifecycle.MutableLiveData
import com.seigneur.gauvain.wowsplash.data.TemporaryDataProvider
import com.seigneur.gauvain.wowsplash.data.model.photo.Photo
import com.seigneur.gauvain.wowsplash.data.repository.PhotoRepository
import com.seigneur.gauvain.wowsplash.di.PHOTO_DETAILS_TEMP_SCOPE_NAME
import com.seigneur.gauvain.wowsplash.di.PHOTO_DETAILS_TEMP_SCOPE_SESSION_ID
import com.seigneur.gauvain.wowsplash.ui.photoList.PhotoPresenter
import com.seigneur.gauvain.wowsplash.utils.event.Event
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import org.koin.core.KoinComponent
import org.koin.core.qualifier.named

/**
 *  Must be used only by ViewModel which inject all dependencies
 */
class PhotoInteractorImpl(
    private val compositeDisposable: CompositeDisposable,
    private val photoRepository: PhotoRepository
) : KoinComponent {

    val photoLiked = MutableLiveData<Event<Int>>()
    val globalErrorEvent = MutableLiveData<Event<Throwable>>()

    private lateinit var presenter: PhotoPresenter


    fun likePhoto(id: String?, pos: Int) {
        id?.let {
            compositeDisposable.add(
                photoRepository.likePhoto(id)
                    .subscribeBy(  // named arguments for lambda Subscribers
                        onSuccess = {
                            photoLiked.postValue(Event(pos))
                        },
                        onError = {
                            globalErrorEvent.postValue(Event(it))
                        }
                    )
            )
        } ?: globalErrorEvent.postValue(Event(Throwable("oops")))
    }

    fun onPhotoClicked(photo: Photo?) {
        photo?.let {
            val temporaryDataProviderSession =
                getKoin().getOrCreateScope(PHOTO_DETAILS_TEMP_SCOPE_SESSION_ID, named(PHOTO_DETAILS_TEMP_SCOPE_NAME))
            val temporaryDataProvider = temporaryDataProviderSession.get<TemporaryDataProvider>()
            temporaryDataProvider.photoClicked.postValue(photo)
        }
        presenter.presentPhotoDetails()
    }

}

