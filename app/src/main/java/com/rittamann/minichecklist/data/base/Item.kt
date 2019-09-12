package com.rittamann.minichecklist.data.base

import java.util.*

class Item(
    var id: Long = 0L,
    var name: String = "",
    var createCate: Calendar = Calendar.getInstance(),
    var checked: Boolean = false,
    var position: Int = 0
) {
    fun getDay() = createCate.get(Calendar.DAY_OF_MONTH)
    fun validName() = name.trim().isEmpty()
    fun validPosition() = position < 0
}