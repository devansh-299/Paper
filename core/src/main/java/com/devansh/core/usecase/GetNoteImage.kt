package com.devansh.core.usecase

import com.devansh.core.data.Note
import com.devansh.core.repository.NoteRepository

class GetNoteImage {
    suspend operator fun invoke(note: Note) =  note.image
}