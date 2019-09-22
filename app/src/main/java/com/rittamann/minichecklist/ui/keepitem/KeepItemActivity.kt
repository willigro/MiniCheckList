package com.rittamann.minichecklist.ui.keepitem

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import com.rittamann.minichecklist.R
import com.rittamann.minichecklist.data.base.Item
import com.rittamann.minichecklist.ui.base.BaseActivity
import com.rittamann.minichecklist.utils.Constants
import com.rittamann.minichecklist.utils.DialogUtil
import kotlinx.android.synthetic.main.activity_keep_item.editTextContent
import kotlinx.android.synthetic.main.toolbar.viewDelete
import java.util.Timer
import kotlin.concurrent.schedule

class KeepItemActivity : BaseActivity() {

    private lateinit var viewModel: KeepItemViewModel
    private var timer: Timer? = null

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
                if (timer == null) {
                    initTimer()
                } else {
                    timer!!.cancel()
                    initTimer()
                }

                timer!!.schedule(TIME_TO_UPDATE) {
                    runOnUiThread {
                        viewModel.update(p0.toString())
                    }
                }
            }
        })

        viewDelete.setOnClickListener {
            val dialog = DialogUtil().initConfirm(this@KeepItemActivity, getString(R.string.do_you_wish_remove_item))
            dialog.showConfirm(View.OnClickListener {
                viewModel.deleteItem()
                dialog.dismiss()
            })
        }
    }

    private fun initTimer() {
        timer = Timer("Timer to update content", false)
    }

    private fun initObserver() {
        viewModel.getItemAttached()
            .observe(this, Observer { item ->
                item?.also {
                    editTextContent.setText(it.content)
                    editTextContent.setSelection(it.content.length)
                }
            })

        viewModel.getDeleteItemResult().observe(this, Observer { deleted ->
            if (deleted!!) {
                Toast.makeText(
                    this@KeepItemActivity,
                    getString(R.string.item_deleted),
                    Toast.LENGTH_LONG
                ).show()
                finish()
            }
        })
    }

    companion object {
        const val TIME_TO_UPDATE: Long = 500
    }
}
