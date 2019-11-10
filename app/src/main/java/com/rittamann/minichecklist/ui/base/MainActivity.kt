package com.rittamann.minichecklist.ui.base

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.rittamann.minichecklist.R
import com.rittamann.minichecklist.ui.notelist.NoteListFragment
import com.rittamann.minichecklist.utils.DialogUtil
import com.rittamann.minichecklist.utils.FragmentUtil
import com.rittamann.minichecklist.utils.SharedPreferencesUtil
import kotlinx.android.synthetic.main.activity_main.cconfigHost

class MainActivity : BaseActivity() {

    private var dialog: DialogUtil? = null
    private var noteListFragment: NoteListFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        FragmentUtil.add(this, getNoteListFragment())

        cconfigHost.setOnClickListener {
            openConfigHost()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        noteListFragment?.onActivityResult(requestCode, resultCode, data)
    }

    private fun openConfigHost() {
        if (dialog == null || dialog!!.isShowing().not()) {
            dialog = DialogUtil().initConfirm(this@MainActivity, "Configure o HOST", R.layout.dialog_host)
            val edt = dialog!!.dialogView.findViewById<EditText>(R.id.edtHost)
            edt.setText(SharedPreferencesUtil.getHost(this@MainActivity))
            dialog!!.showConfirm(View.OnClickListener {
                SharedPreferencesUtil.setHost(this@MainActivity, edt.text.toString())
                dialog!!.dismiss()
                Toast.makeText(this@MainActivity, "Host configurado", Toast.LENGTH_LONG).show()
            })
        }
    }

    private fun getNoteListFragment(): NoteListFragment {
        noteListFragment = NoteListFragment()
        return noteListFragment as NoteListFragment
    }
}
