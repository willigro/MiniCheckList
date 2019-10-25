package com.rittamann.minichecklist.data.repository.dao

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase

class GenericDAO(var table: String = "", var col: String = "") {

    fun insert(db: SQLiteDatabase, contentValues: ContentValues, close: Boolean = false): Long {
        db.beginTransaction()
        var id: Long = 0
        try {
            id = db.insert(table, null, contentValues)
            if (id != 0L) {
                db.setTransactionSuccessful()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            db.endTransaction()
            if (close) db.close()
        }
        return id
    }

    fun delete(db: SQLiteDatabase, id: Long, close: Boolean = false): Boolean {
        val res = db.delete(table, "$col = ? ", arrayOf(id.toString())) > 0
        if (close) close(db)
        return res
    }

    fun update(db: SQLiteDatabase, contentValues: ContentValues, id: Long, close: Boolean = false): Boolean {
        val res = db.update(table, contentValues, "$col = ? ", arrayOf(id.toString())) > 0
        if (close) close(db)
        return res
    }

    fun close(db: SQLiteDatabase) {
        if (db.inTransaction()) db.endTransaction()
        db.close()
    }
}