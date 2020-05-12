package com.devansh.paper

import android.content.Context
import com.devansh.core.data.Note
import com.devansh.core.repository.NoteDataSource
import com.devansh.paper.database.DatabaseService
import com.devansh.paper.database.NoteEntity

/**
 * Connects the DatabaseService with the Repository in the Core Module
 * by implementing the NoteDataSource interface
 */
class RoomNoteDataSource(context: Context): NoteDataSource {

    private val noteDao = DatabaseService.getInstance(context).noteDao()

    override suspend fun addNote(note: Note) = noteDao.addNoteEntity(
        NoteEntity.createNoteEntity(note))

    override suspend fun getNote(id: Long) = noteDao.getNoteEntity(id)?.createNoteObject()

    override suspend fun getAllNotes() = noteDao.getAllNoteEntities().map { it.createNoteObject() }

    override suspend fun deleteNote(note: Note) = noteDao.deleteNoteEntity(
        NoteEntity.createNoteEntity(note))

}