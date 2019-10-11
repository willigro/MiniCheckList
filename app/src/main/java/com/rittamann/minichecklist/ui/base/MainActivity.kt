package com.rittamann.minichecklist.ui.base

import android.os.Bundle
import android.view.MenuItem
import com.rittamann.minichecklist.R
import com.rittamann.minichecklist.ui.notelist.NoteListFragment
import com.rittamann.minichecklist.utils.FragmentUtil

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        FragmentUtil.add(this, NoteListFragment())
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            supportFragmentManager.popBackStack()
            arrowBack(false)
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
