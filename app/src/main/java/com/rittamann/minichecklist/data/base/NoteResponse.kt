package com.rittamann.minichecklist.data.base

import com.google.gson.annotations.SerializedName

class NoteResponse(
    @SerializedName("id") val id: Long = 0L,
    @SerializedName("content") val content: String? = "",
    @SerializedName("create_at") val create_at: String? = ""
)