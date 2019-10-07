package com.rittamann.minichecklist.ui.notelist

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.rittamann.minichecklist.R
import com.rittamann.minichecklist.data.base.Note
import com.rittamann.minichecklist.ui.base.BaseActivity
import com.rittamann.minichecklist.ui.keepnote.KeepNoteActivity
import com.rittamann.minichecklist.utils.Constants
import kotlinx.android.synthetic.main.activity_main.buttonNew
import kotlinx.android.synthetic.main.activity_main.recyclerView

class NoteListActivity : BaseActivity() {

    private var adapter: RecyclerAdapterNote? = null
    private lateinit var viewModel: NoteListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = NoteListViewModel(NoteListModel(this))
        initViews()
        initObservers()
    }

    override fun onStart() {
        super.onStart()
        viewModel.fetchNoteList()
    }

    private fun initViews() {
        buttonNew.setOnClickListener {
            viewModel.addNote(Note())
        }
    }

    private fun initObservers() {
        viewModel.getCheckListResult().observe(this, Observer { list ->
            list?.also { initRecycler(list) }
        })
        viewModel.getNewItemReuslt().observe(this, Observer { item ->
            item?.also {
                if (it.id > 0) {
                    newItemCreated(it)
                } else {
                    createItemError()
                }
            }
        })
    }

    private fun newItemCreated(item: Note) {
        Intent(this@NoteListActivity, KeepNoteActivity::class.java).apply {
            putExtra(Constants.ITEM_ARGS, item)
            startActivity(this)
        }
    }

    private fun createItemError() {
        Toast.makeText(
            this@NoteListActivity,
            getString(R.string.error_new_item),
            Toast.LENGTH_LONG
        ).show()
    }

    private fun initRecycler(list: List<Note>) {
        if (adapter == null) {
            RecyclerAdapterNote(this, list).apply {
                adapter = this
                recyclerView.adapter = this
                recyclerView.layoutManager = LinearLayoutManager(this@NoteListActivity)
                recyclerView.addItemDecoration(
                    DividerItemDecoration(
                        this@NoteListActivity,
                        DividerItemDecoration.VERTICAL
                    ).apply {
                        setDrawable(
                            ContextCompat.getDrawable(
                                this@NoteListActivity,
                                R.drawable.custom_divider_item_decoration
                            )!!
                        )
                    }
                )
            }
        } else {
            adapter!!.forceUpdate(list)
        }
    }
}
