package com.devmind.devoxx

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.devmind.devoxx.model.Speaker
import com.devmind.devoxx.model.SpeakerDao
import com.google.common.truth.Truth
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class SpeakerListViewModelTest {

    /**
     * Swaps background executor used by the Architecture Components with a different one which executes
     * each task synchronously.
     */
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    lateinit var viewModel: SpeakerListViewModel
    lateinit var speakerDao: SpeakerDao
    lateinit var observer: Observer<List<Speaker>>


    @Before
    fun onInit() {
        speakerDao = mockk(relaxUnitFun = true)
        observer = mockk(relaxed = true)

        val devoxxApplication = mockk<DevoxxApplication>(){
            every { speakerDao() } returns speakerDao
        }
        viewModel = SpeakerListViewModel(devoxxApplication)
    }

    @After
    fun onTearDown(){
        viewModel.speakersLiveData.removeObserver(observer)
    }

    @Test
    fun shouldLoadSpeakers() {
        val speakers = arrayListOf(Speaker("Romain", "Guy"))
        every { speakerDao.readAll() } returns speakers

        viewModel.speakersLiveData.observeForever(observer)

        verify { speakerDao.readAll() }
        verify { observer.onChanged(any()) }
    }
}