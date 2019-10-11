package com.rittamann.minichecklist.utils

import com.rittamann.minichecklist.R
import com.rittamann.minichecklist.ui.base.BaseActivity
import com.rittamann.minichecklist.ui.base.BaseFragment


object FragmentUtil {
    fun add(activity: BaseActivity, fragment: BaseFragment, backStack: Boolean = false) {
        val transaction = activity.supportFragmentManager.beginTransaction()
        transaction.add(R.id.container, fragment, fragment::class.java.name)
        if (backStack)
            transaction.addToBackStack("BACK")
        transaction.commit()
    }

    fun clear(activity: BaseActivity, name: String? = null) {
        val fm = activity.supportFragmentManager
        if (name == null)
            for (fragment in fm.fragments) {
                fm.beginTransaction().remove(fragment).commit()
                fm.popBackStackImmediate()
            }
        else {
            fm.findFragmentByTag(name)?.also {
                fm.beginTransaction().remove(it).commit()
            }
        }
    }
}
