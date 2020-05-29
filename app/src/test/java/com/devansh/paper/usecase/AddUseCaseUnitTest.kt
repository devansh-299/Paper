package com.devansh.paper.usecase

import com.devansh.core.repository.NoteRepository
import com.devansh.core.usecase.AddNote
import com.devansh.paper.common.mockNote
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class AddUseCaseUnitTest {

    @Mock
    lateinit var noteRepository: NoteRepository

    // getting mock data
    private val note  = mockNote()

    private val addUseCase by lazy { AddNote(noteRepository) }

    @Test
    fun test_addUseCase_success() = runBlockingTest {
        // setting up conditions
        Mockito.`when`(noteRepository.addNote(note))
            .thenReturn(true)
        // invoking the conditions
        val result = addUseCase.invoke(note)
        // checking the conditions
        assertTrue(result)
    }
}