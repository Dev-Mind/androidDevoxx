package com.devmind.devoxx

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import com.devmind.devoxx.model.SpeakerDao
import com.devmind.devoxx.model.SpeakerService

class DevoxxApplication:Application(){

    val speakerService by lazy{
        SpeakerService()
    }

    fun speakerDao() = SpeakerDao(speakerService)
}

val AppCompatActivity.devoxxApplication
    get() = this.application as DevoxxApplication