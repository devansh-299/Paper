package com.devansh.core.usecase

import com.devansh.core.repository.NoteRepository

class GetNote(private val noteRepository: NoteRepository) {
    suspend operator fun invoke(noteId: Long) =  noteRepository.getNote(noteId)
}