package com.rittamann.minichecklist.data.base

import java.io.Serializable
import java.util.Calendar

class Note(
    var id: Long = 0L,
    var content: String = "",
    var createCate: Calendar = Calendar.getInstance(),
    var checked: Boolean = false
) : Serializable {
    var idApi: Long = 0L

    fun getDay() = createCate.get(Calendar.DAY_OF_MONTH)

    companion object {

        fun toNoteList(list: List<NoteResponse>?) = arrayListOf<Note>().apply {
            list?.also {
                it.forEach { noteRes ->
                    add(Note().apply {
                        id = noteRes.id
                        content = noteRes.content ?: ""
                        noteRes.create_at?.also {

                        }
                    })
                }
            }
        }
    }
}