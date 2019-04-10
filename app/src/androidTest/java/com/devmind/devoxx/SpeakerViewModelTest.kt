package com.devmind.devoxx

import androidx.arch.core.executor.testing.CountingTaskExecutorRule
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.devmind.devoxx.model.Speaker
import com.devmind.devoxx.model.SpeakerDao
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.TimeUnit


@RunWith(AndroidJUnit4ClassRunner::class)
class SpeakerViewModelTest {

    /**
     * Swaps background executor used by the Architecture Components with a different one which executes
     * each task synchronously.
     */
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

//    @get:Rule
//    val countingTaskExecutorRule = CountingTaskExecutorRule()

    lateinit var viewModel: SpeakerViewModel
    lateinit var speakerDao: SpeakerDao
    lateinit var observer: Observer<Speaker>


    @Before
    fun onInit() {
        speakerDao = mockk(relaxUnitFun = true)
        observer = mockk(relaxed = true)

        val devoxxApplication = mockk<DevoxxApplication>(){
            every { speakerDao() } returns speakerDao
        }
        viewModel = SpeakerViewModel(devoxxApplication)
    }

    @After
    fun onTearDown(){
        viewModel.speakerLiveData.removeObserver(observer)
    }

    @Test
    fun shouldLoadSpeaker() {
        val speaker = Speaker("Romain", "Guy")

        every { speakerDao.readOne(speaker.uuid) } returns speaker

        instantTaskExecutorRule.run {
            viewModel.speakerLiveData.observeForever(observer)
            viewModel.loadSpeaker(speaker.uuid)

            drain()
            verify { speakerDao.readOne(speaker.uuid) }
            verify { observer.onChanged(speaker) }
        }
    }

    @Test
    fun shouldSaveSpeaker() {
        val speaker = Speaker("Romain", "Guy")

        instantTaskExecutorRule.run {
            viewModel.speakerLiveData.observeForever(observer)
            viewModel.createSpeaker(speaker)

            drain()
            verify { speakerDao.create(speaker) }
            verify { observer.onChanged(speaker) }
        }
    }

    @Test
    fun shouldUpdateSpeaker() {
        val speaker = Speaker("Romain", "Guy")

        instantTaskExecutorRule.run {
            viewModel.speakerLiveData.observeForever(observer)
            viewModel.updateSpeaker(speaker)

            verify { speakerDao.update(speaker) }
            verify { observer.onChanged(speaker) }
        }
        drain()
//        verify { speakerDao.update(speaker) }
//        verify { observer.onChanged(speaker) }
    }

    fun drain(){
        instantTaskExecutorRule.run {  }
//        countingTaskExecutorRule.drainTasks(1, TimeUnit.MINUTES)
    }
}