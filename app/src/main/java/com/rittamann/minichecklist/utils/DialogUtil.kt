package com.rittamann.minichecklist.utils

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import com.rittamann.minichecklist.R

class DialogUtil {

    lateinit var dialogView: View
    private lateinit var context: Context
    private var dialog: AlertDialog? = null
    private val defaultListener = View.OnClickListener { dialog?.dismiss() }

    @SuppressLint("InflateParams")
    fun initConfirm(context: Context, message: String, viewId: Int): DialogUtil {
        this.dialogView = LayoutInflater.from(context).inflate(viewId, null)
        this.context = context
        this.dialogView.findViewById<TextView>(R.id.txt_message).text = message
        dialogView.findViewById<AppCompatButton>(R.id.btn_cancel).setOnClickListener(defaultListener)
        return this@DialogUtil
    }

    @SuppressLint("InflateParams")
    fun initConfirm(context: Context, message: String): DialogUtil {
        this.dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_confirm, null)
        this.context = context
        this.dialogView.findViewById<TextView>(R.id.txt_message).text = message
        dialogView.findViewById<AppCompatButton>(R.id.btn_cancel).setOnClickListener(defaultListener)
        return this@DialogUtil
    }

    fun dismiss() {
        dialog!!.dismiss()
    }

    fun isShowing(): Boolean {
        if (dialog == null)
            return false
        return dialog!!.isShowing
    }

    fun showConfirm(onClickListener: View.OnClickListener, onCancelClick: View.OnClickListener? = null) {
        onCancelClick?.apply {
            dialogView.findViewById<AppCompatButton>(R.id.btn_cancel).setOnClickListener(this)
        }
        dialogView.findViewById<AppCompatButton>(R.id.btn_confirm).setOnClickListener(onClickListener)
        show()
    }

    private fun show() {
        val builder = createBuilder()

        dialog = builder.create()
        dialog?.also {
            it.setCanceledOnTouchOutside(false)
            it.window?.setType(WindowManager.LayoutParams.TYPE_APPLICATION_PANEL)
            it.show()
        }
    }

    private fun createBuilder(): AlertDialog.Builder {
        val builder = AlertDialog.Builder(context, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT)
        builder.setView(dialogView)
        builder.setCancelable(false)
        return builder
    }
}