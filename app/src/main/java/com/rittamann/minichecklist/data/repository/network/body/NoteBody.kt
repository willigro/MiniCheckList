package com.rittamann.minichecklist.data.repository.network.body

import com.rittamann.minichecklist.data.base.Note
import com.rittamann.minichecklist.utils.DateUtil

class NoteBody(note: Note) {
    var idApplication: Long = 0L
    var content: String = ""
    var createdDate: String
    var checked: Boolean = false

    init {
        idApplication = note.id
        content = note.content
        createdDate = DateUtil.dbFormat(note.createCate)
        checked = note.checked
    }
}