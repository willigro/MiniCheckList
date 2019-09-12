package com.rittamann.minichecklist.data.repository.config

import android.database.Cursor
import com.rittamann.minichecklist.data.base.Item
import com.rittamann.minichecklist.utils.DateUtil

object CursorDAO {

    fun item(cursor: Cursor) = Item()
        .apply {
            id = cursor.getLong(cursor.getColumnIndex(TableItem.ID))
            content = cursor.getString(cursor.getColumnIndex(TableItem.CONTENT))
            createCate = DateUtil.parseDate(cursor.getString(cursor.getColumnIndex(TableItem.CREATE_DATE)))
            position = cursor.getInt(cursor.getColumnIndex(TableItem.POSITION))
            checked = cursor.getInt(cursor.getColumnIndex(TableItem.CHECKED)) > 0
        }
}