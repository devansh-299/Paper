package com.devansh.paper.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [NoteEntity::class], version = 1)
abstract class DatabaseService: RoomDatabase() {

    companion object {
        private const val DATABASE_NAME = "Note.db"
        private var instance: DatabaseService? = null

        // for creating a new database
        private fun create(context: Context): DatabaseService =
            Room.databaseBuilder(context, DatabaseService::class.java, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build()

        // for creating a Singleton of DatabaseService
        fun getInstance(context: Context): DatabaseService =
            (instance ?: create(context)).also { instance = it }
    }

    abstract fun noteDao(): NoteDao
}