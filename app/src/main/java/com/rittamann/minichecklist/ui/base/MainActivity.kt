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
 *  Adjustment in list reload
 *  When clicked in DONE on input the application is broken
 *  Keep the last keep screen opened when rotate the smartphone
 * */
class MainActivity : BaseActivity(), NoteListFragment.NotesListener {

    private var isLandscape = false
    private var noteListFragment: NoteListFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        isLandscape = resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
        FragmentUtil.clear(this)
        if (isLandscape) {
            FragmentUtil.add(this, getNoteListFragment(), R.id.container_left)
        } else {
            FragmentUtil.add(this, getNoteListFragment())
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
            FragmentUtil.add(this, KeepNoteFragment.newInstance(note, false), R.id.container_right)
        } else {
            FragmentUtil.add(this, KeepNoteFragment.newInstance(note), true)
        }
    }

    override fun noteDeleted(note: Note) {
        noteListFragment?.noteDeleted(note)
    }

    private fun getNoteListFragment(): NoteListFragment {
        noteListFragment = NoteListFragment()
        return noteListFragment as NoteListFragment
    }
}
