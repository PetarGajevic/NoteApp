package com.example.noteapp.feature_note.domain.use_case

import com.example.noteapp.feature_note.data.repository.FakeNoteRepository
import com.example.noteapp.feature_note.domain.model.InvalidNoteException
import com.example.noteapp.feature_note.domain.model.Note
import io.mockk.coEvery
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class AddNoteUseCaseTest {

    private lateinit var addNoteUseCase: AddNoteUseCase
    private lateinit var fakeNoteRepository: FakeNoteRepository

    @Before
    fun setUp(){
        fakeNoteRepository = FakeNoteRepository()
        addNoteUseCase = AddNoteUseCase(fakeNoteRepository)
    }


    @Test
    fun `valid note insertion`() = runBlocking {
        val note = Note("Title", "Content", 0,0)
         fakeNoteRepository.insertNote(note)

        addNoteUseCase(note)

        // Verify that the note was inserted successfully
        assertEquals(true, fakeNoteRepository.getNotes().first().contains(note))
    }

    @Test
    fun `empty title throws InvalidNoteException`() {
        val note = Note("", "Content", 0,0)

        val exception = assertThrows(Exception::class.java) {
            runBlocking {
                addNoteUseCase(note)
            }
        }

        assertEquals("The title of the note can't be empty", exception.message)
    }

    @Test
    fun `empty content throws InvalidNoteException`() {
        val note = Note("Title", "", 0,0)

        val exception = assertThrows(InvalidNoteException::class.java) {
            runBlocking {
                addNoteUseCase(note)
            }
        }

        assertEquals("The content of the note can't be empty", exception.message)
    }

}