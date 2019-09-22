package com.rittamann.minichecklist.ui.checklist

import android.content.Context
import com.rittamann.minichecklist.data.base.Item
import com.rittamann.minichecklist.data.repository.CheckListDAO

class CheckListModel(context: Context) {
    private val checkListDAO = CheckListDAO(context)

    fun setChecked(item: Item) = checkListDAO.setChecked(item)
    fun getAll() = checkListDAO.getAll()
    fun newItem(item: Item) = checkListDAO.insert(item)
}
