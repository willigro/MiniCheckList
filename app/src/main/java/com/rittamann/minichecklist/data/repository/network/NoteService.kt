package com.rittamann.minichecklist.data.repository.network

import com.rittamann.minichecklist.data.repository.network.body.NoteBody
import com.rittamann.minichecklist.data.base.NoteResponse
import com.rittamann.minichecklist.data.repository.network.response.RegisterNoteResponse
import retrofit2.Call

interface NoteService {
    fun register(noteBody: NoteBody): Call<RegisterNoteResponse>
    fun update(noteBody: NoteBody): Call<Void>
    fun getAll(): Call<List<NoteResponse>>
}

class NoteServiceImpl(private val api: RestApi) : NoteService {

    override fun register(noteBody: NoteBody) = api.registerNote(noteBody)

    override fun update(noteBody: NoteBody) = api.updateNote(noteBody.idApplication, noteBody)

    override fun getAll() = api.getAllNotes()
}