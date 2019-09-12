package com.rittamann.minichecklist.data.repository.config

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class HelperDAO(context: Context) : SQLiteOpenHelper(context, "minichecklist_db", null, 1) {

    override fun onCreate(sqLiteDatabase: SQLiteDatabase?) {
        sqLiteDatabase?.apply {
            createTables(this)
        }
    }

    override fun onUpgrade(sqLiteDatabase: SQLiteDatabase?, p1: Int, p2: Int) {
    }

    private fun createTables(sqLiteDatabase: SQLiteDatabase) {
        sqLiteDatabase.execSQL(TableItem.create())
    }

    private fun dropTables(sqLiteDatabase: SQLiteDatabase) {

    }
}