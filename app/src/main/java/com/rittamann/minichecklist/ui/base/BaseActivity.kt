package com.rittamann.minichecklist.ui.base

import androidx.appcompat.app.AppCompatActivity

open class BaseActivity : AppCompatActivity() {

    fun arrowBack(b: Boolean) = supportActionBar!!.setDisplayHomeAsUpEnabled(b)
}