package com.rittamann.minichecklist.data.repository.config

object QueryDAO{
    const val ASC = " ASC "
    const val DESC = " DESC "

    fun orderBy(col: String, ordenation: String) : String{
        return if (col.isEmpty() || ordenation.isEmpty()) "" else " ORDER BY $col $ordenation"
    }
}