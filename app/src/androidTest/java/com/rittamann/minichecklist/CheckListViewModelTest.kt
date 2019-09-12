package com.rittamann.minichecklist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.InstrumentationRegistry
import com.rittamann.minichecklist.data.base.Item
import com.rittamann.minichecklist.data.repository.CheckListDAO
import com.rittamann.minichecklist.exceptions.ItemNameEmptyException
import com.rittamann.minichecklist.exceptions.ItemPositionLessZeroException
import com.rittamann.minichecklist.ui.checklist.CheckListModel
import com.rittamann.minichecklist.ui.checklist.CheckListViewModel
import org.junit.Assert
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.runners.MethodSorters

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class CheckListViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()
    private val viewModel = CheckListViewModel(CheckListModel(context()))
    private fun context() = InstrumentationRegistry.getTargetContext()

    @Test
    fun add_new_item() {
        viewModel.addItem(Item(id = 1, content = "Novo item", position = 0))
        viewModel.getCheckList().observeOnce {
            Assert.assertNotNull(it)
            Assert.assertEquals(1, it.size)
        }
    }

    @Test
    fun add_new_item_two_times() {
        viewModel.addItem(Item(id = 1, content = "Novo item", position = 0))
        viewModel.addItem(Item(id = 2, content = "Novo item", position = 1))
        viewModel.getCheckList().observeOnce {
            Assert.assertNotNull(it)
            Assert.assertEquals(2, it.size)
            Assert.assertEquals(1, it[0].id)
            Assert.assertEquals(2, it[1].id)
        }
    }

    @Test(expected = ItemPositionLessZeroException::class)
    fun add_new_item_with_negative_position() {
        viewModel.addItem(Item(id = 1, content = "Novo item", position = -2))
    }

    @Test(expected = ItemNameEmptyException::class)
    fun add_new_item_with_name_empty() {
        viewModel.addItem(Item(id = 1, content = "", position = 0))
    }

    @Test(expected = ItemNameEmptyException::class)
    fun add_new_item_with_name_empty_with_spaces() {
        viewModel.addItem(Item(id = 1, content = "    ", position = 0))
    }

    @Test
    fun add_items_with_same_position() {
        viewModel.addItem(Item(id = 1, content = "New item 1", position = 0))
        viewModel.addItem(Item(id = 1, content = "New item 2", position = 0))
        viewModel.getCheckList().observeOnce {
            Assert.assertEquals(1, it[1].position)
        }

        viewModel.addItem(Item(id = 1, content = "New item 3", position = 0))
        viewModel.addItem(Item(id = 1, content = "New item 4", position = 0))
        viewModel.getCheckList().observeOnce {
            Assert.assertEquals(2, it[2].position)
            Assert.assertEquals("New item 3", it[2].content)
            Assert.assertEquals(3, it[3].position)
        }

        viewModel.addItem(Item(id = 1, content = "New item 5", position = 4))
        viewModel.getCheckList().observeOnce {
            Assert.assertEquals(4, it[4].position)
            Assert.assertEquals("New item 5", it[4].content)
        }
    }

    @Test
    fun check_item() {
        val item = Item()
        item.id = CheckListDAO(context()).insert(item)
        viewModel.checkItem(item)
        viewModel.getCheckResult().observeOnce {
            Assert.assertTrue(it)
        }
        viewModel.getUncheckResult().observeOnce {
            Assert.fail()
        }
    }


    @Test
    fun uncheck_item() {
        val item = Item()
        item.id = CheckListDAO(context()).insert(item)
        viewModel.uncheckItem(item)
        viewModel.getUncheckResult().observeOnce {
            Assert.assertTrue(it)
        }
        viewModel.getCheckResult().observeOnce {
            Assert.fail()
        }
    }

    @Test
    fun check_item_without_id() {
        val item = Item()
        viewModel.checkItem(item)
        viewModel.getCheckResult().observeOnce {
            Assert.assertFalse(it)
        }
        viewModel.getUncheckResult().observeOnce {
            Assert.fail()
        }
    }

    @Test
    fun uncheck_item_without_id() {
        val item = Item()
        viewModel.uncheckItem(item)
        viewModel.getUncheckResult().observeOnce {
            Assert.assertFalse(it)
        }
        viewModel.getCheckResult().observeOnce {
            Assert.fail()
        }
    }
}