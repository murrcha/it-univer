package com.kkaysheva.ituniver.kotlin.robots

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.Espresso.pressBack
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import com.kkaysheva.ituniver.IdsResources.CONTACT_LAYOUT_ID
import com.kkaysheva.ituniver.IdsResources.CONTACT_MAP_BUTTON_ID

class ContactRobot : Robot() {

    fun showContactDetail(): ContactRobot = this

    fun navigateBack(): ContactsListRobot {
        pressBack()
        return ContactsListRobot()
    }

    fun navigateToMap(): ContactMapRobot {
        onView(withId(CONTACT_MAP_BUTTON_ID)).perform(click())
        return ContactMapRobot()
    }

    fun contactDetailDisplayedSuccess() {
        onView(withId(CONTACT_LAYOUT_ID)).check(matches(isCompletelyDisplayed()))
    }
}
