package com.rittamann.minichecklist.data.repository

import android.content.Context
import android.database.Cursor
import com.rittamann.minichecklist.data.base.Item
import com.rittamann.minichecklist.data.repository.config.ContentValuesDAO
import com.rittamann.minichecklist.data.repository.config.CursorDAO
import com.rittamann.minichecklist.data.repository.config.HelperDAO
import com.rittamann.minichecklist.data.repository.config.ManagerDAO
import com.rittamann.minichecklist.data.repository.config.QueryDAO
import com.rittamann.minichecklist.data.repository.config.TableItem

class CheckListDAO(context: Context) {
    private val managerDAO = ManagerDAO(HelperDAO(context))

    fun insert(item: Item): Long {
        managerDAO.openWrite()
        return managerDAO.db?.let {
            GenericDAO(TableItem.TABLE, TableItem.ID).insert(it, ContentValuesDAO.item(item), true)
        } ?: 0L
    }

    fun update(item: Item): Boolean {
        managerDAO.openWrite()
        return managerDAO.db?.let {
            GenericDAO(TableItem.TABLE, TableItem.ID).update(it, ContentValuesDAO.item(item), item.id, true)
        } ?: false
    }

    fun delete(item: Item): Boolean {
        managerDAO.openWrite()
        return managerDAO.db?.let {
            GenericDAO(TableItem.TABLE, TableItem.ID).delete(it, item.id, true)
        } ?: false
    }

    fun getAll(ordenation: String = ""): ArrayList<Item> {
        managerDAO.openRead()
        managerDAO.db?.apply {
            return get(
                rawQuery(
                    "SELECT * FROM ${TableItem.TABLE} ${QueryDAO.orderBy(TableItem.ID, ordenation)};"
                    , null
                )
            )
        }
        return ArrayList()
    }

    private fun get(cursor: Cursor?): ArrayList<Item> {
        val list = ArrayList<Item>()
        cursor?.apply {
            if (count > 0) {
                while (moveToNext()) {
                    list.add(CursorDAO.item(this))
                }
                close()
            }
        }
        managerDAO.close()
        return list
    }
}