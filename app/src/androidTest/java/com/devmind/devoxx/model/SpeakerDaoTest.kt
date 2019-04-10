package com.devmind.devoxx.model

import androidx.room.Room
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import com.google.common.truth.Truth
import org.junit.After
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class SpeakerDaoTest{

    lateinit var database: DevoxxDatabase
    lateinit var dao: SpeakerDao
    private val speaker = Speaker("Chet", "Haase", "USA")

    @Before
    fun onInit(){
        val testContext = InstrumentationRegistry.getInstrumentation().context

        database = Room.inMemoryDatabaseBuilder(testContext, DevoxxDatabase::class.java).allowMainThreadQueries().build()
        dao = database.speakerDao()
        dao.create(speaker)
    }

    @After
    fun onClose() = database.close()

    @Test
    fun readAll(){
        val speakers = dao.readAll()
        Truth.assertThat(speakers).containsExactly(speaker)
    }

    @Test
    fun readOne(){
        val speaker = dao.readOne(speaker.uuid)
        Truth.assertThat(speaker).isEqualTo(speaker)
    }

    @Test
    fun readOneByUnknownId(){
        val speaker = dao.readOne("unknown")
        Truth.assertThat(speaker).isNull()
    }

    @Test
    fun update(){
        dao.update(Speaker("Chet", "Guy", "USA", speaker.uuid))
        val speaker = dao.readOne(speaker.uuid)
        Truth.assertThat(speaker.lastname).isEqualTo("Guy")
    }

    @Test
    fun delete(){
        dao.delete(speaker)
        val speakers = dao.readAll()
        Truth.assertThat(speakers).isEmpty()
    }
}