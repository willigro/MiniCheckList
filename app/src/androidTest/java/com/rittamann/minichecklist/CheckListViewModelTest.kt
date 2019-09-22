package com.rittamann.minichecklist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.InstrumentationRegistry
import com.rittamann.minichecklist.data.base.Item
import com.rittamann.minichecklist.data.repository.CheckListDAO
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
        viewModel.getNewItemReuslt().observeOnce {
            Assert.assertNotNull(it)
            Assert.assertNotEquals(0, it.id)
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