package com.rittamann.minichecklist.ui.keepitem

import androidx.lifecycle.MutableLiveData
import com.rittamann.minichecklist.data.base.Item
import com.rittamann.minichecklist.ui.base.BaseViewModel

class KeepItemViewModel(private val keepItemModel: KeepItemModel) : BaseViewModel() {

    private val updateResult: MutableLiveData<Item> = MutableLiveData()
    private val contentValidateResult: MutableLiveData<Boolean> = MutableLiveData()
    fun getUpdateItemResult() = updateResult
    fun getContentResult() = contentValidateResult

    fun update(item: Item) {
        contentValidateResult.value = true

        if(item.valid()){
            contentValidateResult.value = false
        }

        keepItemModel.update(item).also {
            updateResult.value = if (it) item else null
        }
    }
}