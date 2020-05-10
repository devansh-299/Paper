package com.devansh.core.repository

import com.devansh.core.data.Note

class NoteRepository(private val noteDataSource: NoteDataSource) {

    suspend fun addNote(note: Note) = noteDataSource.addNote(note)

    suspend fun getNote(id: Long) = noteDataSource.getNote(id)

    suspend fun getAllNotes() = noteDataSource.getAllNotes()

    suspend fun deleteNote(note: Note) = noteDataSource.deleteNote(note)
}