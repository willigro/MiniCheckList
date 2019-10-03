package com.rittamann.minichecklist.data.repository.config

import android.database.sqlite.SQLiteDatabase

class ManagerDAO(private val helperDAO: HelperDAO) {

    var db: SQLiteDatabase? = null

    fun openWrite(): SQLiteDatabase? {
        db = helperDAO.writableDatabase
        return db
    }

    fun openRead(): SQLiteDatabase? {
        db = helperDAO.readableDatabase
        return db
    }

    fun close() {
        db?.apply {
            close()
        }
    }
}

object Table {
    const val CREATE = "CREATE TABLE IF NOT EXISTS"
}

object TableItem {
    const val TABLE = "tb_item"
    const val ID = "id_item"
    const val CONTENT = "content_item"
    const val CREATE_DATE = "create_date_item"
    const val CHECKED = "checked_item"

    fun create() = "${Table.CREATE} $TABLE (" +
            "$ID INTEGER PRIMARY KEY AUTOINCREMENT," +
            "$CREATE_DATE TEXT NULL, " +
            "$CONTENT TEXT NULL, " +
            "$CHECKED INTEGER DEFAULT 0);"
}
