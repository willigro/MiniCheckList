package com.rittamann.minichecklist.ui.keepnote

import android.content.Context
import com.rittamann.minichecklist.data.base.Note
import com.rittamann.minichecklist.data.repository.NoteDAO

class KeepNoteModel(context: Context) {
    private val noteDAO = NoteDAO(context)

    fun update(note: Note) = noteDAO.update(note)
    fun delete(note: Note) = noteDAO.delete(note)
}
