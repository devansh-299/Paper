package com.devansh.paper.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query

/**
 * Performs operations with the Room Database.
 */
@Dao
interface NoteDao {

    @Insert(onConflict = REPLACE)
    suspend fun addNoteEntity(noteEntity: NoteEntity)

    @Query("SELECT * FROM Note WHERE id = :id")
    suspend  fun getNoteEntity(id: Long): NoteEntity?

    @Query("SELECT * FROM Note")
    suspend fun getAllNoteEntities(): List<NoteEntity>

    @Delete
    suspend fun deleteNoteEntity(noteEntity: NoteEntity)
}
