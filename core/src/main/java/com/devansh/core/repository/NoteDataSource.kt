package com.devansh.core.repository

import com.devansh.core.data.Note

interface NoteDataSource {

    suspend fun addNote(note: Note): Boolean

    suspend fun getNote(id: Long): Note?

    suspend fun getAllNotes(): List<Note>

    suspend fun deleteNote(note: Note): Boolean
}