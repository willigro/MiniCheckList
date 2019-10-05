package com.rittamann.minichecklist.ui.notelist

import android.content.Context
import com.rittamann.minichecklist.data.base.Note
import com.rittamann.minichecklist.data.repository.NoteDAO
import com.rittamann.minichecklist.data.repository.config.QueryDAO

class NoteListModel(context: Context) {
    private val noteDAO = NoteDAO(context)

    fun setChecked(note: Note) = noteDAO.setChecked(note)
    fun getAll() = noteDAO.getAll(QueryDAO.DESC)
    fun newNote(note: Note) = noteDAO.insert(note)
}
