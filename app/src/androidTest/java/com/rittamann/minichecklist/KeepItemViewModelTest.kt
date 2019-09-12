package com.rittamann.minichecklist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.InstrumentationRegistry
import com.rittamann.minichecklist.data.base.Item
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

    @Test
    fun inset_item() {
        viewModel.keep(Item(content = "Tarefa 1"))
        viewModel.getInsertItemResult().observeOnce {
            Assert.assertNotEquals(0, it.id)
        }
        viewModel.getUpdateItemResult().observeOnce {
            Assert.fail()
        }
    }

    @Test
    fun update_item() {
        viewModel.keep(Item(content = "Tarefa 1"))
        viewModel.getInsertItemResult().observeOnce {
            Assert.fail()
        }
        viewModel.getUpdateItemResult().observeOnce {
            Assert.assertNotNull(it)
        }
    }

    @Test
    fun update_item_with_id_wrong() {
        viewModel.keep(Item(id = random().toLong() * 1000, content = "Tarefa 2"))
        viewModel.getInsertItemResult().observeOnce {
            Assert.fail()
        }
        viewModel.getUpdateItemResult().observeOnce {
            Assert.assertNull(it)
        }
    }
}