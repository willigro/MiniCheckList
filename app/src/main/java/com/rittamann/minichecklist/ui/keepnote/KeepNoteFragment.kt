package com.rittamann.minichecklist.ui.keepnote

import android.annotation.SuppressLint
import android.content.Context
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
import com.rittamann.minichecklist.ui.base.BaseFragment
import com.rittamann.minichecklist.ui.notelist.NoteListFragment
import com.rittamann.minichecklist.utils.Constants
import com.rittamann.minichecklist.utils.DialogUtil
import kotlinx.android.synthetic.main.fragment_keep_note.editTextContent
import kotlinx.android.synthetic.main.toolbar_keep.optionHifen
import kotlinx.android.synthetic.main.toolbar_keep.optionMode
import kotlinx.android.synthetic.main.toolbar_keep.txtStatus
import kotlinx.android.synthetic.main.toolbar_keep.viewDelete
import java.util.Timer
import kotlin.concurrent.schedule

class KeepNoteFragment : BaseFragment() {

    override fun getLayoutId(): Int = R.layout.fragment_keep_note
    private lateinit var viewModel: KeepNoteViewModel
    private var listener: NoteListFragment.NotesListener? = null
    private var activeHifen = false
    private var timer: Timer? = null
    private var colorSaved = 0
    private var colorEditing = 0
    private var colorSelectedOptionBackground = 0
    private var colorUnselectedOptionBackground = 0
    private var showArrowBack = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initColors()
        viewModel = KeepNoteViewModel(KeepNoteModel(activity!!))
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
        initObserver()
        arguments?.also {
            viewModel.attachNote(it.getSerializable(Constants.ITEM_ARGS)!! as Note)
            showArrowBack = it.getBoolean(Constants.SHOW_BACK_ARROW_ARGS)
        }
        (activity!! as BaseActivity).arrowBack(showArrowBack)
    }

    private fun initColors() {
        colorSaved = ContextCompat.getColor(activity!!, R.color.textColor)
        colorEditing = ContextCompat.getColor(activity!!, R.color.textColorLight)
        colorSelectedOptionBackground = ContextCompat.getColor(activity!!, R.color.button_new_note)
        colorUnselectedOptionBackground = ContextCompat.getColor(activity!!, R.color.textColorLight)
    }

    @SuppressLint("SetTextI18n")
    private fun initView() {
        editTextContent.addTextChangedListener(textWatcher)

        viewDelete.setOnClickListener {
            val dialog = DialogUtil().initConfirm(activity!!, getString(R.string.do_you_wish_remove_item))
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
                    activity!!,
                    getString(R.string.item_deleted),
                    Toast.LENGTH_LONG
                ).show()
                listener?.noteDeleted(it)
                finishFrag()
            }
        })

        viewModel.getUpdateNoteResult().observe(this, Observer { note ->
            note?.also {
                txtStatus.text = getString(R.string.status_done)
                txtStatus.setTextColor(colorSaved)
                listener?.noteUpdated(it)
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

            timer?.schedule(TIME_TO_UPDATE) {
                activity?.runOnUiThread {
                    viewModel.update(value.toString())
                }
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is NoteListFragment.NotesListener) {
            listener = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    companion object {
        const val TIME_TO_UPDATE: Long = 500

        fun newInstance(note: Note, showBackArrow: Boolean = true) = KeepNoteFragment().apply {
            arguments = Bundle().apply {
                putSerializable(Constants.ITEM_ARGS, note)
                putSerializable(Constants.SHOW_BACK_ARROW_ARGS, showBackArrow)
            }
        }
    }
}
