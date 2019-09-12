package com.rittamann.minichecklist.ui.checklist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rittamann.minichecklist.data.base.Item
import com.rittamann.minichecklist.ui.base.BaseViewModel

class CheckListViewModel : BaseViewModel() {

    private val arrayCheckList: MutableLiveData<ArrayCheckList> = MutableLiveData()

    fun getCheckList(): LiveData<ArrayCheckList> = arrayCheckList

    public fun addItem(item: Item) {
        getList().addItem(item)
    }

    private fun getList(): ArrayCheckList {
        if (arrayCheckList.value == null)
            arrayCheckList.value = ArrayCheckList()
        return arrayCheckList.value!!
    }
}