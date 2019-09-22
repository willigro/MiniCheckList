package com.rittamann.minichecklist.ui.checklist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rittamann.minichecklist.data.base.Item
import com.rittamann.minichecklist.ui.base.BaseViewModel

class CheckListViewModel(private val checkListModel: CheckListModel) : BaseViewModel() {

    private val listItems: MutableLiveData<List<Item>> = MutableLiveData()
    private val newItemResult: MutableLiveData<Item> = MutableLiveData()
    private val checkedItemResult: MutableLiveData<Boolean> = MutableLiveData()
    private val uncheckedItemResult: MutableLiveData<Boolean> = MutableLiveData()

    fun getCheckListResult(): LiveData<List<Item>> = listItems
    fun getNewItemReuslt(): LiveData<Item> = newItemResult
    fun getCheckResult(): LiveData<Boolean> = checkedItemResult
    fun getUncheckResult(): LiveData<Boolean> = uncheckedItemResult

    fun addItem(item: Item) {
        item.also {
            item.id = checkListModel.newItem(it)
            newItemResult.value = item
        }
    }

    fun checkItem(item: Item) {
        item.apply {
            checked = true
            checkedItemResult.value = checkListModel.setChecked(this)
        }
    }

    fun uncheckItem(item: Item) {
        item.apply {
            checked = false
            uncheckedItemResult.value = checkListModel.setChecked(this)
        }
    }

    fun fetchCheckList() {
        listItems.value = checkListModel.getAll()
    }
}