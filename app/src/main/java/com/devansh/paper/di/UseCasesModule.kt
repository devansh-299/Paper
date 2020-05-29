package com.devansh.paper.di


import com.devansh.core.repository.NoteRepository
import com.devansh.core.usecase.*
import com.devansh.paper.UseCases
import dagger.Module
import dagger.Provides

@Module
class UseCasesModule {

    @Provides
    fun providesUseCases(noteRepository: NoteRepository) = UseCases(
        AddNote(noteRepository),
        DeleteNote(noteRepository),
        GetAllNotes(noteRepository),
        GetNote(noteRepository)
    )
}