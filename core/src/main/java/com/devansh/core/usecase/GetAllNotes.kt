package com.devansh.core.usecase

import com.devansh.core.repository.NoteRepository

class GetAllNotes(private val noteRepository: NoteRepository) {
    suspend operator fun invoke() =  noteRepository.getAllNotes()
}