package com.devansh.paper.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.devansh.core.data.Note
import com.devansh.paper.UseCases
import com.devansh.paper.di.ApplicationModule
import com.devansh.paper.di.DaggerViewModelComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class NoteViewModel(application: Application): AndroidViewModel(application) {

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    @Inject
    lateinit var useCases: UseCases

    init {
        DaggerViewModelComponent.builder()
            .applicationModule(ApplicationModule(getApplication()))
            .build()
            .inject(this)
    }

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