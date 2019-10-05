package com.rittamann.minichecklist.ui.keepnote

import androidx.lifecycle.MutableLiveData
import com.rittamann.minichecklist.data.base.Note
import com.rittamann.minichecklist.ui.base.BaseViewModel

class KeepNoteViewModel(private val keepNoteModel: KeepNoteModel) : BaseViewModel() {

    private val noteAttached: MutableLiveData<Note> = MutableLiveData()
    private val updateResult: MutableLiveData<Note> = MutableLiveData()
    private val deleteResult: MutableLiveData<Boolean> = MutableLiveData()

    fun getNoteAttached() = noteAttached
    fun getUpdateNoteResult() = updateResult
    fun getDeleteNoteResult() = deleteResult

    fun attachNote(item: Note) {
        noteAttached.value = item
    }

    fun update(content: String) {
        noteAttached.value!!.apply {
            this.content = content
            keepNoteModel.update(this).also {
                updateResult.value = if (it) this else null
            }
        }
    }

    fun deleteNote() {
        deleteResult.value = keepNoteModel.delete(noteAttached.value!!)
    }
}