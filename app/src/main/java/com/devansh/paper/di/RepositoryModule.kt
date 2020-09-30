package com.devansh.paper.di

import android.content.Context
import com.devansh.core.repository.NoteRepository
import com.devansh.paper.RoomNoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ApplicationComponent::class)
object RepositoryModule {

    @Provides
    fun providesRepository(@ApplicationContext application: Context): NoteRepository
    {
        return NoteRepository(RoomNoteDataSource(application))
    }

}