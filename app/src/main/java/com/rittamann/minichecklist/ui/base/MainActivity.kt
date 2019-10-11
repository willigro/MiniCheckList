package com.rittamann.minichecklist.ui.base

import android.content.res.Configuration
import android.os.Bundle
import android.view.MenuItem
import com.rittamann.minichecklist.R
import com.rittamann.minichecklist.data.base.Note
import com.rittamann.minichecklist.ui.keepnote.KeepNoteFragment
import com.rittamann.minichecklist.ui.notelist.NoteListFragment
import com.rittamann.minichecklist.utils.FragmentUtil

/**
 * TODO
 *  Timer broken if clase still are editing
 *  Adjustment in list reload
 *  When clicked in DONE on input the application is broken
 *  Keep the last keep screen opened when rotate the smartphone
 * */
class MainActivity : BaseActivity(), NoteListFragment.NotesListener {

    private var isLandscape = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        isLandscape = resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
        FragmentUtil.clear(this)
        if (isLandscape) {
            supportFragmentManager.beginTransaction()
                .add(R.id.container_left, NoteListFragment(), NoteListFragment::class.java.name)
                .commit()
        } else {
            FragmentUtil.add(this, NoteListFragment())
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            supportFragmentManager.popBackStack()
            arrowBack(false)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun showNote(note: Note) {
        FragmentUtil.clear(this, KeepNoteFragment::class.java.name)
        if (isLandscape) {
            supportFragmentManager.beginTransaction()
                .add(R.id.container_right, KeepNoteFragment.newInstance(note, false), KeepNoteFragment::class.java.name)
                .commit()
        } else {
            FragmentUtil.add(this, KeepNoteFragment.newInstance(note), true)
        }
    }
}
