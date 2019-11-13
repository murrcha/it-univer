package com.kkaysheva.ituniver.kotlin.robots

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.Espresso.pressBack
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import com.kkaysheva.ituniver.IdsResources.CONTACT_MAP_LAYOUT_ID

class ContactMapRobot : Robot() {

    fun showContactMap(): ContactMapRobot = this

    fun navigateBack(): ContactRobot {
        pressBack()
        return ContactRobot()
    }

    fun contactMapDisplayedSuccess() {
        onView(withId(CONTACT_MAP_LAYOUT_ID)).check(matches(isCompletelyDisplayed()))
    }
}
