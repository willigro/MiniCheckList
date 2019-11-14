package com.rittamann.minichecklist.data.repository.dao.config

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class HelperDAO(context: Context) : SQLiteOpenHelper(context, "minichecklist_db", null, 2) {

    override fun onCreate(sqLiteDatabase: SQLiteDatabase?) {
        sqLiteDatabase?.apply {
            createTables(this)
        }
    }

    override fun onUpgrade(sqLiteDatabase: SQLiteDatabase?, p1: Int, p2: Int) {
        sqLiteDatabase?.execSQL("ALTER TABLE ${TableNote.TABLE} ADD COLUMN ${TableNote.ID_API} INTEGER;")
    }

    private fun createTables(sqLiteDatabase: SQLiteDatabase) {
        sqLiteDatabase.execSQL(TableNote.create())
    }

    private fun dropTables(sqLiteDatabase: SQLiteDatabase) {

    }
}