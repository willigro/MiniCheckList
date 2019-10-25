package com.rittamann.minichecklist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.InstrumentationRegistry
import com.rittamann.minichecklist.data.base.Note
import com.rittamann.minichecklist.data.repository.dao.NoteDAO
import com.rittamann.minichecklist.ui.notelist.NoteListModel
import com.rittamann.minichecklist.ui.notelist.NoteListViewModel
import org.junit.Assert
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.runners.MethodSorters

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class NoteListViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()
    private val viewModel = NoteListViewModel(NoteListModel(context()))
    private fun context() = InstrumentationRegistry.getTargetContext()

    @Test
    fun add_new_item() {
        viewModel.addNote(Note(id = 1, content = "Novo item"))
        viewModel.getNewItemReuslt().observeOnce {
            Assert.assertNotNull(it)
            Assert.assertNotEquals(0, it.id)
        }
    }

    @Test
    fun check_item() {
        val item = Note()
        item.id = NoteDAO(context()).insert(item)
        viewModel.checkNote(item)
        viewModel.getCheckResult().observeOnce {
            Assert.assertTrue(it)
        }
        viewModel.getUncheckResult().observeOnce {
            Assert.fail()
        }
    }

    @Test
    fun uncheck_item() {
        val item = Note()
        item.id = NoteDAO(context()).insert(item)
        viewModel.uncheckNote(item)
        viewModel.getUncheckResult().observeOnce {
            Assert.assertTrue(it)
        }
        viewModel.getCheckResult().observeOnce {
            Assert.fail()
        }
    }

    @Test
    fun check_item_without_id() {
        val item = Note()
        viewModel.checkNote(item)
        viewModel.getCheckResult().observeOnce {
            Assert.assertFalse(it)
        }
        viewModel.getUncheckResult().observeOnce {
            Assert.fail()
        }
    }

    @Test
    fun uncheck_item_without_id() {
        val item = Note()
        viewModel.uncheckNote(item)
        viewModel.getUncheckResult().observeOnce {
            Assert.assertFalse(it)
        }
        viewModel.getCheckResult().observeOnce {
            Assert.fail()
        }
    }
}