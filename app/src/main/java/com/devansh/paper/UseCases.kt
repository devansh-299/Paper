package com.devansh.paper

import com.devansh.core.usecase.AddNote
import com.devansh.core.usecase.DeleteNote
import com.devansh.core.usecase.GetAllNotes
import com.devansh.core.usecase.GetNote


data class UseCases (
    val addNote: AddNote,
    val deleteNote: DeleteNote,
    val getAllNotes: GetAllNotes,
    val getNote: GetNote
)