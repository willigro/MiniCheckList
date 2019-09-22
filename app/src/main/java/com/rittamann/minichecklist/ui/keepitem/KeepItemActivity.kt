package com.rittamann.minichecklist.ui.keepitem

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.Observer
import com.rittamann.minichecklist.R
import com.rittamann.minichecklist.data.base.Item
import com.rittamann.minichecklist.ui.base.BaseActivity
import com.rittamann.minichecklist.utils.Constants
import kotlinx.android.synthetic.main.activity_keep_item.editTextContent

class KeepItemActivity : BaseActivity() {

    private lateinit var viewModel: KeepItemViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_keep_item)
        viewModel = KeepItemViewModel(KeepItemModel(this))
        initView()
        initObserver()
        viewModel.attachItem(intent!!.extras!!.getSerializable(Constants.ITEM_ARGS)!! as Item)
    }

    private fun initView() {
        editTextContent.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.update(p0.toString())
            }
        })
    }

    private fun initObserver() {
        viewModel.getItemAttached()
            .observe(this, Observer { item ->
                item?.also { editTextContent.setText(item.content) }
            })
    }
}
