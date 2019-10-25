package com.rittamann.minichecklist.network

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.InstrumentationRegistry
import com.rittamann.minichecklist.data.base.Note
import com.rittamann.minichecklist.data.base.NoteResponse
import com.rittamann.minichecklist.data.repository.network.RestApi
import org.junit.Assert
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.runners.MethodSorters
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class NoteApiTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private fun context() = InstrumentationRegistry.getTargetContext()
    private lateinit var api: RestApi

    @Before
    fun configure() {
        api = RestApi.invoke(context())
    }

    @Test
    fun fetch_all_note() {
        api.getAllNotes().enqueue(object : Callback<List<NoteResponse>> {
            override fun onFailure(call: Call<List<NoteResponse>>?, t: Throwable?) {
                Assert.fail()
            }

            override fun onResponse(call: Call<List<NoteResponse>>?, response: Response<List<NoteResponse>>?) {
                if (response?.body() == null) Assert.fail()
                else {
                    Assert.assertNotNull(response.body())
                    Assert.assertEquals(1, Note.toNoteList(response.body()))
                }
            }
        })
    }
}