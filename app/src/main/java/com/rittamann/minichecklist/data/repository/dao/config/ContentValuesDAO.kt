package com.rittamann.minichecklist.data.repository.dao.config

import android.content.ContentValues
import com.rittamann.minichecklist.data.base.Note
import com.rittamann.minichecklist.utils.DateUtil

object ContentValuesDAO {

    fun item(item: Note) = ContentValues().apply {
        put(TableNote.CONTENT, item.content)
        put(TableNote.CHECKED, if (item.checked) 1 else 0)
        put(TableNote.CREATE_DATE, DateUtil.dbFormat(item.createCate))
    }
}