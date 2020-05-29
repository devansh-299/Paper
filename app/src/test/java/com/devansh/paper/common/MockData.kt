package com.devansh.paper.common

import com.devansh.core.data.Note

inline fun mockNote() = Note(1L, "title", "content", 1L, 1L, null)

inline fun mockNotesList() = emptyList<Note>()
