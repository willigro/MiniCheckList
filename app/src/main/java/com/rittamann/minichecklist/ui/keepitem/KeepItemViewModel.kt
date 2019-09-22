package com.rittamann.minichecklist.ui.keepitem

import androidx.lifecycle.MutableLiveData
import com.rittamann.minichecklist.data.base.Item
import com.rittamann.minichecklist.ui.base.BaseViewModel

class KeepItemViewModel(private val keepItemModel: KeepItemModel) : BaseViewModel() {

    private val itemAttached: MutableLiveData<Item> = MutableLiveData()
    private val updateResult: MutableLiveData<Item> = MutableLiveData()
    private val deleteResult: MutableLiveData<Boolean> = MutableLiveData()

    fun getItemAttached() = itemAttached
    fun getUpdateItemResult() = updateResult
    fun getDeleteItemResult() = deleteResult

    fun attachItem(item: Item) {
        itemAttached.value = item
    }

    fun update(content: String) {
        itemAttached.value!!.apply {
            this.content = content
            keepItemModel.update(this).also {
                updateResult.value = if (it) this else null
            }
        }
    }

    fun deleteItem() {
        deleteResult.value = keepItemModel.delete(itemAttached.value!!)
    }
}