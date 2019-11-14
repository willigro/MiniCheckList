package com.rittamann.minichecklist.keep

import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import androidx.test.rule.ActivityTestRule
import com.rittamann.minichecklist.R
import com.rittamann.minichecklist.data.base.Note
import com.rittamann.minichecklist.ui.keepnote.KeepNoteActivity
import com.rittamann.minichecklist.utils.Constants
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.runners.MethodSorters

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class KeepNoteTest {

    @get:Rule
    var mActivityPicture = ActivityTestRule(KeepNoteActivity::class.java, true, false)

    @Test
    fun open_keep_with_empty_note() {
        getInstrumentation().waitForIdleSync()
        val targetContext = getInstrumentation().targetContext
        Intent(targetContext, KeepNoteActivity::class.java).apply {
            putExtra(Constants.ITEM_ARGS, Note())
            mActivityPicture.launchActivity(this)
        }

//        onView(withId(R.id.)).perform(click())
    }

    @Test
    fun edit_note() {

    }

    @Test
    fun state_editing() {

    }

    @Test
    fun state_saving() {

    }

    @Test
    fun edit_mode() {

    }

    @Test
    fun read_mode() {

    }

    @Test
    fun delete_note() {

    }

    @Test
    fun option_hyphen() {

    }
}