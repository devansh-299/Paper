package com.devansh.paper.usecase

import com.devansh.core.repository.NoteRepository
import com.devansh.core.usecase.GetAllNotes
import com.devansh.paper.common.mockNotesList
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class GetAllNotesUseCaseUnitTest {

    @Mock
    lateinit var noteRepository: NoteRepository

    val emptyNotesList = mockNotesList()

    private val getAllNotesUseCase by lazy { GetAllNotes(noteRepository) }

    @Test
    fun test_getAllNotesUseCase_returnsListSuccessfully() = runBlockingTest {
        // setting up the conditions
        Mockito.`when`(noteRepository.getAllNotes())
            .thenReturn(emptyNotesList)
        // invoking the  conditions
        val result = getAllNotesUseCase.invoke()
        // checking the conditions
        assertThat(result).isEqualTo(emptyNotesList)
    }

}