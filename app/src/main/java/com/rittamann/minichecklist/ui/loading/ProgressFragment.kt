package com.rittamann.minichecklist.ui.loading

import android.os.Bundle
import com.rittamann.minichecklist.utils.Constants.PROGRESS_CONTENT_ARGS

import com.rittamann.minichecklist.R
import com.rittamann.minichecklist.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_progress.text

class ProgressFragment : BaseFragment() {

    override fun getLayoutId() = R.layout.fragment_progress
    private var content: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            content = it.getString(PROGRESS_CONTENT_ARGS)
        }
    }

    override fun onStart() {
        super.onStart()
        if (content.isEmpty()) content = getString(R.string.loading)
        text.text = content
    }

    companion object {
        fun newInstance(content: String) =
            ProgressFragment().apply {
                arguments = Bundle().apply {
                    putString(PROGRESS_CONTENT_ARGS, content)
                }
            }
    }
}
