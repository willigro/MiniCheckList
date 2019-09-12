package com.rittamann.minichecklist.ui.checklist

import com.rittamann.minichecklist.data.base.Item
import com.rittamann.minichecklist.exceptions.ItemNameEmptyException
import com.rittamann.minichecklist.exceptions.ItemPositionLessZeroException

class ArrayCheckList : ArrayList<Item>() {

    fun addItem(item: Item?) = item?.let {
        when {
            item.validName() -> throw ItemNameEmptyException()
            item.validPosition() -> throw ItemPositionLessZeroException()
            else -> {
                item.position = adjustPosition(item.position)
                super.add(item)
            }
        }
    } ?: false

    private fun adjustPosition(position: Int): Int {
        this.forEach { if (position == it.position) return size }
        return position
    }
}