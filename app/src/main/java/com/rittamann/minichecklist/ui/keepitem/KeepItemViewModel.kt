package com.rittamann.minichecklist.ui.keepitem

import androidx.lifecycle.MutableLiveData
import com.rittamann.minichecklist.data.base.Item
import com.rittamann.minichecklist.ui.base.BaseViewModel

class KeepItemViewModel(private val keepItemModel: KeepItemModel) : BaseViewModel() {

    private val insertResult: MutableLiveData<Item> = MutableLiveData()
    private val updateResult: MutableLiveData<Item> = MutableLiveData()
    private val contentValidateResult: MutableLiveData<Boolean> = MutableLiveData()
    fun getInsertItemResult() = insertResult
    fun getUpdateItemResult() = updateResult
    fun getContentResult() = contentValidateResult

    fun keep(item: Item) {
        contentValidateResult.value = true
        if (item.validName().not()) {
            contentValidateResult.value = false
        } else {
            if (item.id == 0L) insert(item) else update(item)
        }
    }

    private fun insert(item: Item) {
        keepItemModel.insert(item).also {
            item.id = it
            insertResult.value = if (it > 0) item else null
        }
    }

    private fun update(item: Item) {
        keepItemModel.update(item).also {
            updateResult.value = if (it) item else null
        }
    }
}