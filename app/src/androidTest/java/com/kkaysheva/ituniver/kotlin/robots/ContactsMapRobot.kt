package com.kkaysheva.ituniver.kotlin.robots


import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.Espresso.pressBack
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import com.kkaysheva.ituniver.IdsResources.CONTACTS_MAP_LAYOUT_ID

class ContactsMapRobot : Robot() {

    fun showContactsMap(): ContactsMapRobot = this

    fun navigateBack(): ContactsListRobot {
        pressBack()
        return ContactsListRobot()
    }

    fun contactsMapDisplayedSuccess() {
        onView(withId(CONTACTS_MAP_LAYOUT_ID)).check(matches(isCompletelyDisplayed()))
    }
}
