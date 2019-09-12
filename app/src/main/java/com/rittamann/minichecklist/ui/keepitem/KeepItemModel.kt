package com.rittamann.minichecklist.ui.keepitem

import android.content.Context
import com.rittamann.minichecklist.data.base.Item
import com.rittamann.minichecklist.data.repository.CheckListDAO

class KeepItemModel(context: Context) {
    private val checkListDAO = CheckListDAO(context)

    fun insert(item: Item) = checkListDAO.insert(item)
    fun update(item: Item) = checkListDAO.update(item)
}
