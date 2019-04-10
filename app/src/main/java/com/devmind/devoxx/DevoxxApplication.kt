package com.devmind.devoxx

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.devmind.devoxx.model.DevoxxDatabase

class DevoxxApplication : Application() {

    val devoxxDatabase by lazy {
        Room.databaseBuilder(applicationContext, DevoxxDatabase::class.java, "devoxx2").build()
    }

    fun speakerDao() = devoxxDatabase.speakerDao()
}

val AppCompatActivity.devoxxApplication
    get() = this.application as DevoxxApplication