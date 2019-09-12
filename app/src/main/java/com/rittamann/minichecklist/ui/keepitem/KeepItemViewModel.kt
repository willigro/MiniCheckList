package com.rittamann.minichecklist.ui.keepitem

import androidx.lifecycle.MutableLiveData
import com.rittamann.minichecklist.data.base.Item
import com.rittamann.minichecklist.ui.base.BaseViewModel

class KeepItemViewModel(private val keepItemModel: KeepItemModel) : BaseViewModel() {

    private val keptItemResult: MutableLiveData<Item> = MutableLiveData()
    fun getKeptItem() = keptItemResult

    fun keep(item: Item) {
        
    }
}