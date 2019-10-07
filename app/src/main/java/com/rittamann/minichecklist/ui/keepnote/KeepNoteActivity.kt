package com.rittamann.minichecklist.ui.keepnote

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.MenuItem
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
import kotlinx.android.synthetic.main.activity_keep_note.editTextContent
import kotlinx.android.synthetic.main.toolbar_keep.optionHifen
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
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        viewModel = KeepNoteViewModel(KeepNoteModel(this))
        initView()
        initObserver()
        viewModel.attachNote(intent!!.extras!!.getSerializable(Constants.ITEM_ARGS)!! as Note)
    }

    private fun initColors() {
        colorSaved = ContextCompat.getColor(this, R.color.textColor)
        colorEditing = ContextCompat.getColor(this, R.color.textColorLight)
        colorSelectedOptionBackground = ContextCompat.getColor(this@KeepNoteActivity, R.color.button_new_note)
        colorUnselectedOptionBackground = ContextCompat.getColor(this@KeepNoteActivity, R.color.textColorLight)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    @SuppressLint("SetTextI18n")
    private fun initView() {
        editTextContent.addTextChangedListener(textWatcher)

        viewDelete.setOnClickListener {
            val dialog = DialogUtil().initConfirm(this@KeepNoteActivity, getString(R.string.do_you_wish_remove_item))
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

        editTextContent.setOnEditorActionListener { textView: TextView, actionId: Int, keyEvent: KeyEvent ->
            if (activeHifen && keyEvent.keyCode == KeyEvent.KEYCODE_ENTER) {
                editTextContent.setText("${editTextContent.text}\n- ")
                editTextContent.setSelection(editTextContent.text.length)
                true
            } else
                false
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

        viewModel.getDeleteNoteResult().observe(this, Observer { deleted ->
            if (deleted!!) {
                Toast.makeText(
                    this@KeepNoteActivity,
                    getString(R.string.item_deleted),
                    Toast.LENGTH_LONG
                ).show()
                finish()
            }
        })

        viewModel.getUpdateNoteResult().observe(this, Observer { updated ->
            updated?.also {
                txtStatus.text = getString(R.string.status_done)
                txtStatus.setTextColor(colorSaved)
            }
        })
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

            timer!!.schedule(TIME_TO_UPDATE) {
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
