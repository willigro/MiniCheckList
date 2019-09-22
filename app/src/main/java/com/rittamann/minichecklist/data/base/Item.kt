package com.rittamann.minichecklist.data.base

import com.rittamann.minichecklist.exceptions.ItemPositionLessZeroException
import java.io.Serializable
import java.util.*

class Item(
    var id: Long = 0L,
    var content: String = "",
    var createCate: Calendar = Calendar.getInstance(),
    var checked: Boolean = false,
    var position: Int = 0
) : Serializable {
    fun getDay() = createCate.get(Calendar.DAY_OF_MONTH)
    private fun validPosition() = position >= 0

    fun valid(): Boolean {
        if (validPosition().not())
            throw ItemPositionLessZeroException()
        return true
    }
}