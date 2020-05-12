package com.devansh.paper.di

import android.app.Application
import com.devansh.core.repository.NoteRepository
import com.devansh.paper.RoomNoteDataSource
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

    @Provides
    fun providesRepository(application: Application) =
        NoteRepository(RoomNoteDataSource(application))
}