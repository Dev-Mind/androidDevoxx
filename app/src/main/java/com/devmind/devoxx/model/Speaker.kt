package com.devmind.devoxx.model

import java.util.*


data class Speaker(
    val firstname: String,
    val lastname: String,
    val country: String = "France",
    val uuid: String = UUID.randomUUID().toString()
)


class SpeakerService {
    val speakers = mutableListOf(Speaker("Guillaume", "EHRET"))
}

interface DaoCrud<T> {
    // Create
    fun create(element: T)

    // Read
    fun readAll(): List<T>

    fun readOne(id: String): T

    // Update
    fun update(element: T)

    // Delete
    fun delete(element: T)
}

class SpeakerDao(private val speakerService: SpeakerService) : DaoCrud<Speaker> {

    override fun create(element: Speaker) {
        speakerService.speakers.add(element)
    }

    override fun readAll() = speakerService.speakers

    override fun readOne(id: String): Speaker = speakerService.speakers.first { it.uuid == id }

    override fun update(element: Speaker) {
        delete(readOne(element.uuid))
        create(element)
    }

    override fun delete(element: Speaker) {
        speakerService.speakers.removeAll { it.uuid == element.uuid }
    }

}
