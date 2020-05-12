package com.devansh.paper

import com.devansh.core.usecase.*

data class UseCases(
    val addNote: AddNote,
    val deleteNote: DeleteNote,
    val getAllNotes: GetAllNotes,
    val getNote: GetNote,
    val getNoteImage: GetNoteImage
)