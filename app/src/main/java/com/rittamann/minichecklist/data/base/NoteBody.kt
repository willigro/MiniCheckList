package com.rittamann.minichecklist.data.base

import com.rittamann.minichecklist.utils.DateUtil

class NoteBody(note: Note) {
    var id: Long = 0L
    var content: String = ""
    var createCate: String
    var checked: Boolean = false

    init {
        id = note.id
        content = note.content
        createCate = DateUtil.dbFormat(note.createCate)
        checked = note.checked
    }
}