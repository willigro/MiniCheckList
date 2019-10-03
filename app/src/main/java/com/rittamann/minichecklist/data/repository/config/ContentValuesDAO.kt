package com.rittamann.minichecklist.data.repository.config

import android.content.ContentValues
import com.rittamann.minichecklist.data.base.Item
import com.rittamann.minichecklist.utils.DateUtil

object ContentValuesDAO {

    fun item(item: Item) = ContentValues().apply {
        put(TableItem.CONTENT, item.content)
        put(TableItem.CHECKED, if (item.checked) 1 else 0)
        put(TableItem.CREATE_DATE, DateUtil.dbFormat(item.createCate))
    }
}