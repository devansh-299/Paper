package com.devansh.paper.usecase

import com.devansh.core.repository.NoteRepository
import com.devansh.core.usecase.DeleteNote
import com.devansh.paper.common.mockNote
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class DeleteUseCaseUnitTest {

    @Mock
    lateinit var noteRepository: NoteRepository

    // getting mock note
    private val note  = mockNote()

    private val deleteUseCase by lazy { DeleteNote(noteRepository) }

    @Test
    fun test_deleteUseCase_success() = runBlockingTest {
        // setting up conditions
        Mockito.`when`(noteRepository.deleteNote(note))
            .thenReturn(true)
        // invoking the conditions
        val result = deleteUseCase.invoke(note)
        // checking the conditions
        assertThat(result).isTrue()

    }
}