package com.devmind.devoxx.model

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase


@Database(entities = [ Speaker::class], version = 1)
abstract class DevoxxDatabase: RoomDatabase(){
    abstract fun speakerDao(): SpeakerDao
}