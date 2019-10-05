package com.rittamann.minichecklist.ui.notelist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rittamann.minichecklist.data.base.Note
import com.rittamann.minichecklist.ui.base.BaseViewModel

class NoteListViewModel(private val noteListModel: NoteListModel) : BaseViewModel() {

    private val listNotes: MutableLiveData<List<Note>> = MutableLiveData()
    private val newNoteResult: MutableLiveData<Note> = MutableLiveData()
    private val checkedNoteResult: MutableLiveData<Boolean> = MutableLiveData()
    private val uncheckedNoteResult: MutableLiveData<Boolean> = MutableLiveData()

    fun getCheckListResult(): LiveData<List<Note>> = listNotes
    fun getNewItemReuslt(): LiveData<Note> = newNoteResult
    fun getCheckResult(): LiveData<Boolean> = checkedNoteResult
    fun getUncheckResult(): LiveData<Boolean> = uncheckedNoteResult

    fun addNote(note: Note) {
        note.also {
            note.id = noteListModel.newNote(it)
            newNoteResult.value = note
        }
    }

    fun checkNote(item: Note) {
        item.apply {
            checked = true
            checkedNoteResult.value = noteListModel.setChecked(this)
        }
    }

    fun uncheckNote(item: Note) {
        item.apply {
            checked = false
            uncheckedNoteResult.value = noteListModel.setChecked(this)
        }
    }

    fun fetchNoteList() {
        listNotes.value = noteListModel.getAll()
    }
}