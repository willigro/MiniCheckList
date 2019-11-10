package com.rittamann.minichecklist.ui.keepnote

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.rittamann.minichecklist.R
import com.rittamann.minichecklist.data.base.Note
import com.rittamann.minichecklist.ui.base.BaseActivity
import com.rittamann.minichecklist.utils.Constants
import com.rittamann.minichecklist.utils.DialogUtil
import com.rittamann.minichecklist.utils.ResultCode
import kotlinx.android.synthetic.main.activity_keep_note.editTextContent
import kotlinx.android.synthetic.main.toolbar_keep.optionHifen
import kotlinx.android.synthetic.main.toolbar_keep.optionMode
import kotlinx.android.synthetic.main.toolbar_keep.txtStatus
import kotlinx.android.synthetic.main.toolbar_keep.viewDelete
import java.util.Timer
import kotlin.concurrent.schedule

class KeepNoteActivity : BaseActivity() {

    private lateinit var viewModel: KeepNoteViewModel
    private var activeHifen = false
    private var timer: Timer? = null
    private var colorSaved = 0
    private var colorEditing = 0
    private var colorSelectedOptionBackground = 0
    private var colorUnselectedOptionBackground = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_keep_note)
        initColors()
        viewModel = KeepNoteViewModel(KeepNoteModel(this))
        initView()
        initObserver()
        intent?.also {
            viewModel.attachNote(it.getSerializableExtra(Constants.ITEM_ARGS)!! as Note)
        }
    }

    private fun initColors() {
        colorSaved = ContextCompat.getColor(this, R.color.textColor)
        colorEditing = ContextCompat.getColor(this, R.color.textColorLight)
        colorSelectedOptionBackground = ContextCompat.getColor(this, R.color.button_new_note)
        colorUnselectedOptionBackground = ContextCompat.getColor(this, R.color.textColorLight)
    }

    @SuppressLint("SetTextI18n")
    private fun initView() {
        editTextContent.addTextChangedListener(textWatcher)

        viewDelete.setOnClickListener {
            val dialog = DialogUtil().initConfirm(this, getString(R.string.do_you_wish_remove_item))
            dialog.showConfirm(View.OnClickListener {
                viewModel.deleteNote()
                dialog.dismiss()
            })
        }

        optionHifen.setOnClickListener {
            activeHifen = if (activeHifen) {
                optionHifen.setBackgroundColor(colorUnselectedOptionBackground)
                false
            } else {
                optionHifen.setBackgroundColor(colorSelectedOptionBackground)
                true
            }
        }

        editTextContent?.setOnEditorActionListener { textView: TextView, actionId: Int, keyEvent: KeyEvent? ->
            if (activeHifen && keyEvent != null && keyEvent.keyCode == KeyEvent.KEYCODE_ENTER && keyEvent.action == KeyEvent.ACTION_DOWN) {
                editTextContent.setText("${editTextContent.text}\n- ")
                editTextContent.setSelection(editTextContent.text.length)
                true
            } else
                false
        }

        optionMode.setOnClickListener {
            if (optionMode.text == getString(R.string.mode_write)) {
                optionMode.text = getString(R.string.mode_read)
                editTextContent.setRawInputType(InputType.TYPE_NULL)
            } else {
                optionMode.text = getString(R.string.mode_write)
                editTextContent.setRawInputType(InputType.TYPE_CLASS_TEXT)
            }
        }
    }

    private fun initTimer() {
        timer = Timer("Timer to update content", false)
        txtStatus.text = getString(R.string.status_reading)
        txtStatus.setTextColor(colorEditing)
    }

    private fun initObserver() {
        viewModel.getNoteAttached()
            .observe(this, Observer { item ->
                item?.also {
                    editTextContent.setText(it.content)
                    editTextContent.setSelection(it.content.length)
                }
            })

        viewModel.getDeleteNoteResult().observe(this, Observer { note ->
            note?.also {
                Toast.makeText(
                    this,
                    getString(R.string.item_deleted),
                    Toast.LENGTH_LONG
                ).show()
                Intent().apply {
                    putExtra(Constants.ITEM_ARGS, it)
                    setResult(ResultCode.DELETED_NOTE, this)
                    finish()
                }
            }
        })

        viewModel.getUpdateNoteResult().observe(this, Observer { note ->
            note?.also {
                txtStatus.text = getString(R.string.status_done)
                txtStatus.setTextColor(colorSaved)
            }
        })
    }

    override fun onBackPressed() {
        Intent().apply {
            putExtra(Constants.ITEM_ARGS, viewModel.getNoteAttached().value)
            setResult(ResultCode.UPDATED_NOTE, this)
            finish()
        }
    }

    private val textWatcher = object : TextWatcher {
        override fun afterTextChanged(p0: Editable?) {
        }

        override fun beforeTextChanged(value: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        @SuppressLint("SetTextI18n")
        override fun onTextChanged(value: CharSequence?, p1: Int, p2: Int, p3: Int) {
            if (timer == null) {
                initTimer()
            } else {
                timer!!.cancel()
                initTimer()
            }

            timer?.schedule(TIME_TO_UPDATE) {
                runOnUiThread {
                    viewModel.update(value.toString())
                }
            }
        }
    }

    companion object {
        const val TIME_TO_UPDATE: Long = 500
    }
}
