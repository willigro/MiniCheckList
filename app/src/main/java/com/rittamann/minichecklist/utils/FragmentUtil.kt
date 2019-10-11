package com.rittamann.minichecklist.utils

import com.rittamann.minichecklist.R
import com.rittamann.minichecklist.ui.base.BaseActivity
import com.rittamann.minichecklist.ui.base.BaseFragment

object FragmentUtil {
    fun add(activity: BaseActivity, fragment: BaseFragment, backStack: Boolean = false) {
        val transaction = activity.supportFragmentManager.beginTransaction()
        transaction.add(R.id.container, fragment)
        if (backStack)
            transaction.addToBackStack("BACK")
        transaction.commit()
    }
}
