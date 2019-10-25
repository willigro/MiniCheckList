package com.rittamann.minichecklist.data.repository.network

import com.rittamann.minichecklist.data.base.NoteBody
import com.rittamann.minichecklist.data.base.NoteResponse
import retrofit2.Call

interface NoteService {
    fun register(noteBody: NoteBody): Call<Void>
    fun update(noteBody: NoteBody): Call<Void>
    fun getAll(): Call<List<NoteResponse>>
}

class NoteServiceImpl(private val api: RestApi) : NoteService {

    override fun register(noteBody: NoteBody) = api.registerNote(noteBody)

    override fun update(noteBody: NoteBody) = api.updateNote(noteBody.id, noteBody)

    override fun getAll() = api.getAllNotes()
}