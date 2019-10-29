package com.rittamann.minichecklist.ui.base

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.rittamann.minichecklist.ui.loading.ProgressFragment
import com.rittamann.minichecklist.utils.FragmentUtil

open class BaseActivity : AppCompatActivity() {

    private val loadingList = arrayListOf<Fragment>()

    fun arrowBack(b: Boolean) = supportActionBar!!.setDisplayHomeAsUpEnabled(b)

    private fun containsLoading() = loadingList.isNotEmpty()

    fun initProgress(content: String = "") {
        if (containsLoading().not()) {
            val frag = ProgressFragment.newInstance(content)
            FragmentUtil.add(this, frag)
            loadingList.add(frag)
        }
    }

    fun finishProgress() {
        loadingList.takeIf { it.isNotEmpty() }.let { list ->
            val fragment = list?.get(0)
            FragmentUtil.dismiss(fragment, supportFragmentManager)
            loadingList.remove(fragment)
        }
    }
}