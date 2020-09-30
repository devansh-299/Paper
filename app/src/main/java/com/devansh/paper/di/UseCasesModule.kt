package com.devansh.paper.di


import com.devansh.core.repository.NoteRepository
import com.devansh.core.usecase.*
import com.devansh.paper.UseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
object UseCasesModule {

    @Provides
    fun providesUseCases(noteRepository: NoteRepository): UseCases {return UseCases(
        AddNote(noteRepository),
        DeleteNote(noteRepository),
        GetAllNotes(noteRepository),
        GetNote(noteRepository)
    )}
}