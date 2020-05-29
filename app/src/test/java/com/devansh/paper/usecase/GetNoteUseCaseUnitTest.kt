package com.devansh.paper.usecase

import com.devansh.core.data.Note
import com.devansh.core.repository.NoteRepository
import com.devansh.core.usecase.GetNote
import com.devansh.paper.common.mockNote
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetNoteUseCaseUnitTest {

    @Mock
    lateinit var noteRepository: NoteRepository

    val note  = mockNote()

    private val getNoteUseCase by lazy { GetNote(noteRepository) }

    @Test
    fun test_getNote_returnsNoteSuccessfully() = runBlockingTest {
        // setting up the condition
        Mockito.`when`(noteRepository.getNote(note.id))
            .thenReturn(note)
        // invoking the condition
        val result = getNoteUseCase.invoke(note.id)
        // checking the condition
        assertThat(result, `is`(note))
    }

    @Test
    fun test_getNote_returnsNullOutput() = runBlockingTest {
        // setting up the condition
        Mockito.`when`(noteRepository.getNote(note.id))
            .thenReturn(note)

        val nullNote:Note? = null

        // passing random noteId to give null as response - invoking condition
        val result = getNoteUseCase.invoke(1111L)
        // checking the condition
        assertThat(result, `is`(nullNote))
    }

}