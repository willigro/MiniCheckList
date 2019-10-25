package com.rittamann.minichecklist.data.repository.dao

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.rittamann.minichecklist.data.base.Note
import com.rittamann.minichecklist.data.repository.dao.config.ContentValuesDAO
import com.rittamann.minichecklist.data.repository.dao.config.CursorDAO
import com.rittamann.minichecklist.data.repository.dao.config.HelperDAO
import com.rittamann.minichecklist.data.repository.dao.config.ManagerDAO
import com.rittamann.minichecklist.data.repository.dao.config.QueryDAO
import com.rittamann.minichecklist.data.repository.dao.config.TableNote

class NoteDAO(context: Context) {
    private val managerDAO = ManagerDAO(
        HelperDAO(context)
    )

    fun insert(item: Note): Long {
        managerDAO.openWrite()
        return managerDAO.db?.let {
            GenericDAO(
                TableNote.TABLE,
                TableNote.ID
            )
                .insert(it, ContentValuesDAO.item(item), true)
        } ?: 0L
    }

    fun update(item: Note): Boolean {
        managerDAO.openWrite()
        return managerDAO.db?.let {
            GenericDAO(
                TableNote.TABLE,
                TableNote.ID
            )
                .update(it,
                    ContentValuesDAO.item(item), item.id, true)
        } ?: false
    }

    fun delete(item: Note): Boolean {
        managerDAO.openWrite()
        return managerDAO.db?.let {
            GenericDAO(
                TableNote.TABLE,
                TableNote.ID
            )
                .delete(it, item.id, true)
        } ?: false
    }

    fun getAll(ordenation: String = ""): ArrayList<Note> {
        managerDAO.openRead()
        managerDAO.db?.apply {
            return get(
                rawQuery(
                    "SELECT * FROM ${TableNote.TABLE} ${QueryDAO.orderBy(
                        TableNote.ID,
                        ordenation
                    )};"
                    , null
                )
            )
        }
        return ArrayList()
    }

    private fun get(cursor: Cursor?): ArrayList<Note> {
        val list = ArrayList<Note>()
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

    fun setChecked(item: Note): Boolean {
        managerDAO.openWrite()
        return managerDAO.db?.let {
            GenericDAO(
                TableNote.TABLE,
                TableNote.ID
            )
                .update(it, ContentValues().apply {
                put(TableNote.CHECKED, item.checked)
            }, item.id, true)
        } ?: false
    }
}