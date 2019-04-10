package com.devmind.devoxx

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.devmind.devoxx.model.Speaker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext


class SpeakerListViewModel(application : Application): AndroidViewModel(application), CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Default

    val speakersLiveData: MutableLiveData<List<Speaker>> by lazy {
        MutableLiveData<List<Speaker>>().also {
            loadSpeakers(it)
        }
    }

    private fun loadSpeakers(liveData: MutableLiveData<List<Speaker>>){
        launch {
            val speakers = getApplication<DevoxxApplication>().speakerDao().readAll()
            liveData.postValue(speakers)
        }

    }
}
