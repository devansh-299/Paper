package com.devansh.paper.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.devansh.core.data.Note
import com.devansh.core.repository.NoteRepository
import com.devansh.core.usecase.AddNote
import com.devansh.core.usecase.DeleteNote
import com.devansh.core.usecase.GetAllNotes
import com.devansh.core.usecase.GetNote
import com.devansh.paper.RoomNoteDataSource
import com.devansh.paper.UseCases
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteViewModel(application: Application): AndroidViewModel(application) {

    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    private val repository = NoteRepository(RoomNoteDataSource(application))
    private val useCases = UseCases(
        AddNote(repository),
        DeleteNote(repository),
        GetAllNotes(repository),
        GetNote(repository)
    )

    val noteSaved = MutableLiveData<Boolean>()
    val notesList = MutableLiveData<List<Note>>()

    fun saveNote(note: Note) {
        coroutineScope.launch {
            useCases.addNote(note)
            noteSaved.postValue(true)
        }
    }

    fun fetchAllNotes() {
        coroutineScope.launch {
            notesList.postValue(useCases.getAllNotes())
        }
    }
}