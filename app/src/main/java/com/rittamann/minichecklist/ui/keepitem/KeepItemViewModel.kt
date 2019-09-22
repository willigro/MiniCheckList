package com.rittamann.minichecklist.ui.keepitem

import androidx.lifecycle.MutableLiveData
import com.rittamann.minichecklist.data.base.Item
import com.rittamann.minichecklist.ui.base.BaseViewModel

class KeepItemViewModel(private val keepItemModel: KeepItemModel) : BaseViewModel() {

    private val updateResult: MutableLiveData<Item> = MutableLiveData()
    private val itemAttached: MutableLiveData<Item> = MutableLiveData()

    fun getUpdateItemResult() = updateResult
    fun getItemAttached() = itemAttached

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
}