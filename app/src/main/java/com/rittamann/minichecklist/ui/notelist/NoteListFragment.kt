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
import com.rittamann.minichecklist.ui.base.BaseFragment
import com.rittamann.minichecklist.ui.keepnote.KeepNoteActivity
import com.rittamann.minichecklist.utils.Constants
import com.rittamann.minichecklist.utils.RequestCode
import com.rittamann.minichecklist.utils.ResultCode
import kotlinx.android.synthetic.main.fragment_note_list.buttonNew
import kotlinx.android.synthetic.main.fragment_note_list.recyclerView

class NoteListFragment : BaseFragment() {

    override fun getLayoutId() = R.layout.fragment_note_list
    private var adapter: RecyclerAdapterNote? = null
    private lateinit var viewModel: NoteListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = NoteListViewModel(NoteListModel(activity!!))
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViews()
        initObservers()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RequestCode.KEEP_NOTE) {
            data?.also {
                when (resultCode) {
                    ResultCode.DELETED_NOTE -> noteDeleted(it.getSerializableExtra(Constants.ITEM_ARGS) as Note)
                    ResultCode.UPDATED_NOTE -> noteUpdated(it.getSerializableExtra(Constants.ITEM_ARGS) as Note)
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.fetchNoteList()
        initProgress()
    }

    private fun initViews() {
        buttonNew.setOnClickListener {
            viewModel.addNote(Note())
        }
    }

    private fun initObservers() {
        viewModel.getCheckListResult().observe(this, Observer { list ->
            list?.also { initRecycler(list) }
            finishProgress()
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
        recyclerView.smoothScrollToPosition(0)
        adapter?.newNote(item)
        Intent(activity!!, KeepNoteActivity::class.java).apply {
            putExtra(Constants.ITEM_ARGS, item)
            startActivity(this)
        }
    }

    fun noteDeleted(note: Note) {
        adapter?.noteDeleted(note)
    }

    fun noteUpdated(note: Note) {
        adapter?.noteUpdated(note)
    }

    private fun createItemError() {
        Toast.makeText(
            activity!!,
            getString(R.string.error_new_item),
            Toast.LENGTH_LONG
        ).show()
    }

    private fun initRecycler(list: List<Note>) {
        if (adapter == null) {
            RecyclerAdapterNote(activity!! as BaseActivity, list).apply {
                adapter = this
                recyclerView.adapter = this
                recyclerView.layoutManager = LinearLayoutManager(activity!!)
                recyclerView.addItemDecoration(
                    DividerItemDecoration(
                        activity!!,
                        DividerItemDecoration.VERTICAL
                    ).apply {
                        setDrawable(
                            ContextCompat.getDrawable(
                                activity!!,
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
