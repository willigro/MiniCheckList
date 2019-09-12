package com.rittamann.minichecklist.ui.checklist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rittamann.minichecklist.data.base.Item
import com.rittamann.minichecklist.ui.base.BaseViewModel

class CheckListViewModel(private val checkListModel: CheckListModel) : BaseViewModel() {

    private val arrayCheckList: MutableLiveData<ArrayCheckList> = MutableLiveData()
    private val checkedItemResult: MutableLiveData<Boolean> = MutableLiveData()
    private val uncheckedItemResult: MutableLiveData<Boolean> = MutableLiveData()

    fun getCheckList(): LiveData<ArrayCheckList> = arrayCheckList
    fun getCheckResult(): LiveData<Boolean> = checkedItemResult
    fun getUncheckResult(): LiveData<Boolean> = uncheckedItemResult

    fun addItem(item: Item) {
        getList().addItem(item)
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

    private fun getList(): ArrayCheckList {
        if (arrayCheckList.value == null)
            arrayCheckList.value = ArrayCheckList()
        return arrayCheckList.value!!
    }
}