package com.rittamann.minichecklist.ui.checklist

import android.content.Context
import com.rittamann.minichecklist.data.base.Item
import com.rittamann.minichecklist.data.repository.CheckListDAO
import com.rittamann.minichecklist.data.repository.config.QueryDAO

class CheckListModel(context: Context) {
    private val checkListDAO = CheckListDAO(context)

    fun setChecked(item: Item) = checkListDAO.setChecked(item)
    fun getAll() = checkListDAO.getAll(QueryDAO.DESC)
    fun newItem(item: Item) = checkListDAO.insert(item)
}
