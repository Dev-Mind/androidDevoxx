package com.devmind.devoxx

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.devmind.devoxx.model.Speaker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class SpeakerViewModel(application: Application) : AndroidViewModel(application), CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Default

    val speakerLiveData = MutableLiveData<Speaker>()

    fun dao() = getApplication<DevoxxApplication>().speakerDao()

    fun loadSpeaker(id: String) {
        if (id.isEmpty()) {
            speakerLiveData.postValue(Speaker.empty())
        } else {
            launch {
                speakerLiveData.postValue(dao().readOne(id))
            }
        }
    }

    fun createSpeaker(speaker: Speaker) =
        launch {
            dao().create(speaker)
            speakerLiveData.postValue(speaker)
        }

    fun updateSpeaker(speaker: Speaker) =
        launch {
            dao().update(speaker)
            speakerLiveData.postValue(speaker)
        }
}
