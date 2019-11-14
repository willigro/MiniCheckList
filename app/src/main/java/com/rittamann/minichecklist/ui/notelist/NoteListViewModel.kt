package com.rittamann.minichecklist.ui.notelist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rittamann.minichecklist.data.base.Note
import com.rittamann.minichecklist.data.repository.network.body.NoteBody
import com.rittamann.minichecklist.data.repository.network.NoteService
import com.rittamann.minichecklist.data.repository.network.response.RegisterNoteResponse
import com.rittamann.minichecklist.ui.base.BaseViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NoteListViewModel(
    private val noteListModel: NoteListModel, private val noteService: NoteService
) : BaseViewModel() {

    private val listNotes: MutableLiveData<List<Note>> = MutableLiveData()
    private val newNoteResult: MutableLiveData<Note> = MutableLiveData()
    private val checkedNoteResult: MutableLiveData<Boolean> = MutableLiveData()
    private val uncheckedNoteResult: MutableLiveData<Boolean> = MutableLiveData()
    private val noteWasRegisteredInApi: MutableLiveData<Note> = MutableLiveData()

    fun getCheckListResult(): LiveData<List<Note>> = listNotes
    fun getNewItemReuslt(): LiveData<Note> = newNoteResult
    fun getCheckResult(): LiveData<Boolean> = checkedNoteResult
    fun getUncheckResult(): LiveData<Boolean> = uncheckedNoteResult
    fun getNoteRegisteredInApiResult(): LiveData<Note> = noteWasRegisteredInApi

    fun addNote(note: Note) {
        note.also {
            note.id = noteListModel.newNote(it)
            sendNoteToServer(note)
            (listNotes.value!! as ArrayList).add(0, note)
            newNoteResult.value = note
        }
    }

    private fun sendNoteToServer(note: Note) {
        noteService.register(NoteBody(note)).enqueue(object : Callback<RegisterNoteResponse> {
            override fun onFailure(call: Call<RegisterNoteResponse>?, t: Throwable?) {
                noteWasRegisteredInApi.value = null
            }

            override fun onResponse(call: Call<RegisterNoteResponse>?, response: Response<RegisterNoteResponse>?) {
                noteWasRegisteredInApi.value = if (response != null && response.code() == 201) {
                    noteListModel.updateIdApi(note.apply {
                        idApi = response.body().registered_id
                    })
                    note
                } else {
                    null
                }
            }
        })
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