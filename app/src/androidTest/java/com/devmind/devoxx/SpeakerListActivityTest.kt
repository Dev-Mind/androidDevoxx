package com.devmind.devoxx

import androidx.arch.core.executor.testing.CountingTaskExecutorRule
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.devmind.devoxx.model.Speaker
import com.devmind.devoxx.model.SpeakerAdapter
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.TimeUnit


@RunWith(AndroidJUnit4ClassRunner::class)
class SpeakerListActivityTest {

    @get:Rule
    val activityRule = ActivityTestRule<SpeakerListActivity>(SpeakerListActivity::class.java, false, false)

    @get:Rule
    val countingTaskExecutorRule = CountingTaskExecutorRule()

    constructor() {
        val application =
            InstrumentationRegistry.getInstrumentation().targetContext.applicationContext as DevoxxApplication
        application.deleteDatabase("devoxx2")
        val dao = application.speakerDao()
        if (dao.readAll().isEmpty()) {
            dao.create(Speaker("Romain", "Guy"))
            dao.create(Speaker("Chet", "Haase"))
        }
    }

    @Before
    fun init() {
        activityRule.launchActivity(null)
    }

    @Test
    fun shouldDisplaySpeakers() {
        drain()
        Espresso.onView(ViewMatchers.withText("Romain Guy")).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withText("Chet Haase")).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

    }

    @Test
    fun shouldCreateSpeaker() {
        drain()
        Espresso.onView(ViewMatchers.withId(R.id.buttonAddSpeaker)).perform(ViewActions.click())
        // After this click we should be on speaker view in creation mode
        Espresso.onView(ViewMatchers.withId(R.id.speakerFirstname)).check(ViewAssertions.matches(ViewMatchers.withText("")))
        Espresso.onView(ViewMatchers.withId(R.id.speakerLastname)).check(ViewAssertions.matches(ViewMatchers.withText("")))
    }

    @Test
    fun shouldUpdateSpeaker() {
        drain()
        Espresso.onView(ViewMatchers.withId(R.id.speakerList)).perform(
            RecyclerViewActions.actionOnItemAtPosition<SpeakerAdapter.ViewHolder>(0, ViewActions.click()))
        // After this click we should be on speaker view in update mode
        Espresso.onView(ViewMatchers.withId(R.id.speakerFirstname)).check(ViewAssertions.matches(ViewMatchers.withText("Romain")))
        Espresso.onView(ViewMatchers.withId(R.id.speakerLastname)).check(ViewAssertions.matches(ViewMatchers.withText("Guy")))
    }

    fun drain() {
        countingTaskExecutorRule.drainTasks(1, TimeUnit.MINUTES)
    }
}