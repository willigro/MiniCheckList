package com.rittamann.minichecklist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.InstrumentationRegistry
import com.rittamann.minichecklist.data.base.Note
import com.rittamann.minichecklist.data.repository.NoteDAO
import com.rittamann.minichecklist.ui.keepnote.KeepNoteModel
import com.rittamann.minichecklist.ui.keepnote.KeepNoteViewModel
import org.junit.Assert
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.runners.MethodSorters

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class KeepNoteViewModelTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()
    private val viewModel = KeepNoteViewModel(KeepNoteModel(context()))
    private fun context() = InstrumentationRegistry.getTargetContext()
    private val checkListDAO = NoteDAO(context())

    @Test
    fun update_item() {
        Note().apply {
            content = "Inicio"
            id = checkListDAO.insert(this)
            viewModel.attachNote(this)
            viewModel.getNoteAttached().observeOnce {
                Assert.assertNotNull(it)
                Assert.assertNotEquals("Inicio", it.content)
            }
            viewModel.update("Fim")
            viewModel.getUpdateNoteResult().observeOnce {
                Assert.assertNotNull(it)
                Assert.assertNotEquals(0, it.id)
                Assert.assertNotEquals("Inicio", it.content)
            }
        }
    }

    @Test
    fun update_item_with_id_wrong() {
        Note().apply {
            content = "Inicio"
            checkListDAO.insert(this)
            viewModel.attachNote(this)
            viewModel.getNoteAttached().observeOnce {
                Assert.assertNotNull(it)
                Assert.assertNotEquals("Inicio", it.content)
            }
            id = 10000
            viewModel.update("Fim")
            viewModel.getUpdateNoteResult().observeOnce {
                Assert.assertNull(it)
            }
        }
    }
}