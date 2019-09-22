package com.rittamann.minichecklist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.InstrumentationRegistry
import com.rittamann.minichecklist.data.base.Item
import com.rittamann.minichecklist.data.repository.CheckListDAO
import com.rittamann.minichecklist.exceptions.ItemNameEmptyException
import com.rittamann.minichecklist.exceptions.ItemPositionLessZeroException
import com.rittamann.minichecklist.ui.keepitem.KeepItemModel
import com.rittamann.minichecklist.ui.keepitem.KeepItemViewModel
import org.junit.Assert
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.runners.MethodSorters
import java.lang.Math.random

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class KeepItemViewModelTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()
    private val viewModel = KeepItemViewModel(KeepItemModel(context()))
    private fun context() = InstrumentationRegistry.getTargetContext()
    private val checkListDAO = CheckListDAO(context())

    @Test
    fun update_item() {
        Item().apply {
            content = "Inicio"
            id = checkListDAO.insert(this)
            viewModel.attachItem(this)
            viewModel.getItemAttached().observeOnce {
                Assert.assertNotNull(it)
                Assert.assertNotEquals("Inicio", it.content)
            }
            viewModel.update("Fim")
            viewModel.getUpdateItemResult().observeOnce {
                Assert.assertNotNull(it)
                Assert.assertNotEquals(0, it.id)
                Assert.assertNotEquals("Inicio", it.content)
            }
        }
    }

    @Test
    fun update_item_with_id_wrong() {
        Item().apply {
            content = "Inicio"
            checkListDAO.insert(this)
            viewModel.attachItem(this)
            viewModel.getItemAttached().observeOnce {
                Assert.assertNotNull(it)
                Assert.assertNotEquals("Inicio", it.content)
            }
            id = 10000
            viewModel.update("Fim")
            viewModel.getUpdateItemResult().observeOnce {
                Assert.assertNull(it)
            }
        }
    }

    @Test(expected = ItemPositionLessZeroException::class)
    fun add_new_item_with_negative_position() {
        Item().apply {
            id = checkListDAO.insert(this)
            viewModel.attachItem(this)
            position = -2
            viewModel.update("Testando")
        }
    }
}