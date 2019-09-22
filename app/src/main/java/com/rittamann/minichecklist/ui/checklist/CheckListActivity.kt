package com.rittamann.minichecklist.ui.checklist

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.rittamann.minichecklist.R
import com.rittamann.minichecklist.data.base.Item
import com.rittamann.minichecklist.ui.base.BaseActivity
import com.rittamann.minichecklist.ui.keepitem.KeepItemActivity
import com.rittamann.minichecklist.utils.Constants
import kotlinx.android.synthetic.main.activity_main.buttonNew
import kotlinx.android.synthetic.main.activity_main.recyclerView

class CheckListActivity : BaseActivity() {

    private var adapter: RecyclerAdapterItem? = null
    private lateinit var viewModel: CheckListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = CheckListViewModel(CheckListModel(this))
        initViews()
        initObservers()
    }

    override fun onStart() {
        super.onStart()
        viewModel.fetchCheckList()
    }

    private fun initViews() {
        buttonNew.setOnClickListener {
            viewModel.addItem(Item())
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

    private fun newItemCreated(item: Item) {
        Intent(this@CheckListActivity, KeepItemActivity::class.java).apply {
            putExtra(Constants.ITEM_ARGS, item)
            startActivity(this)
        }
    }

    private fun createItemError() {
        Toast.makeText(
            this@CheckListActivity,
            getString(R.string.error_new_item),
            Toast.LENGTH_LONG
        ).show()
    }

    private fun initRecycler(list: List<Item>) {
        if (adapter == null) {
            RecyclerAdapterItem(this, list).apply {
                adapter = this
                recyclerView.adapter = this
                recyclerView.layoutManager = LinearLayoutManager(this@CheckListActivity)
                recyclerView.addItemDecoration(
                    DividerItemDecoration(
                        this@CheckListActivity,
                        DividerItemDecoration.VERTICAL
                    )
                )
            }
        } else {
            adapter!!.forceUpdate(list)
        }
    }
}
