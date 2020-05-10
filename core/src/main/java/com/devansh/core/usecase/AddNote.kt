package com.devansh.core.usecase

import com.devansh.core.data.Note
import com.devansh.core.repository.NoteRepository

class AddNote(private val noteRepository: NoteRepository) {
    suspend operator fun invoke(note: Note) =  noteRepository.addNote(note)
}