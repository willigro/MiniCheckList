package com.rittamann.minichecklist.data.base

import java.io.Serializable
import java.util.*

class Item(
    var id: Long = 0L,
    var content: String = "",
    var createCate: Calendar = Calendar.getInstance(),
    var checked: Boolean = false
) : Serializable {
    fun getDay() = createCate.get(Calendar.DAY_OF_MONTH)
}