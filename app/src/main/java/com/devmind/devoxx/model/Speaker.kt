package com.devmind.devoxx.model

import androidx.room.*
import java.util.*

@Entity
data class Speaker(
    @ColumnInfo
    val firstname: String,
    @ColumnInfo
    val lastname: String,
    @ColumnInfo
    val country: String = "France",
    @PrimaryKey
    val uuid: String = UUID.randomUUID().toString()
)

@Dao
interface SpeakerDao {
    // Create
    @Insert
    fun create(element: Speaker)

    // Read
    @Query("select * from speaker")
    fun readAll(): List<Speaker>
    @Query("select * from speaker where uuid=:id")
    fun readOne(id: String): Speaker

    // Update
    @Update
    fun update(element: Speaker)

    // Delete
    @Delete
    fun delete(element: Speaker)
}
