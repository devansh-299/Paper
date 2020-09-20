package com.devansh.paper.viewmodel

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.devansh.core.data.Note
import com.devansh.paper.UseCases
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class NoteViewModel @ViewModelInject constructor (private val useCases: UseCases, @Assisted private val savedStateHandle: SavedStateHandle): ViewModel() {

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    // for telling status saving/updating note
    val noteSaved = MutableLiveData<Boolean>()
    val notesList = MutableLiveData<List<Note>>()
    // for fetching note from database using noteId
    val currentNote = MutableLiveData<Note?>()
    val deletedNote = MutableLiveData<Boolean>()

    fun saveNote(note: Note) {
        coroutineScope.launch {
            val success = useCases.addNote(note)
            noteSaved.postValue(success)
        }
    }

    fun fetchAllNotes() {
        coroutineScope.launch {
            notesList.postValue(useCases.getAllNotes())
        }
    }

    fun fetchNoteDetails(noteId: Long) {
        coroutineScope.launch {
            currentNote.postValue(useCases.getNote(noteId))
        }
    }

    fun deleteNote(note: Note) {
        coroutineScope.launch {
            val success = useCases.deleteNote(note)
            deletedNote.postValue(success)
        }
    }
}