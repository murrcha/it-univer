package com.kkaysheva.ituniver.kotlin.robots

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.*
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.v7.widget.RecyclerView
import com.kkaysheva.ituniver.IdsResources.*

class ContactsListRobot : Robot() {

    fun showContactsList(): ContactsListRobot = this

    fun contactsListDisplayedSuccess() {
        onView(withId(RECYCLER_VIEW_ID))
                .check(matches(isCompletelyDisplayed()))
        onView(withId(RECYCLER_VIEW_ID))
                .check(matches(hasMinimumChildCount(1)))
    }

    fun contactsListNotDisplayedSuccess() {
        onView(withId(RECYCLER_VIEW_ID))
                .check(matches(hasChildCount(0)))
    }

    fun contactDisplayedSuccess(contactName: String) {
        onView(withId(ITEM_VIEW_NAME_ID)).check(matches(withText(contactName)))
    }

    fun noContactsTextDisplayedSuccess() {
        onView(withId(NO_CONTACTS_TEXT_VIEW_ID))
                .check(matches(isCompletelyDisplayed()))
    }

    fun searchByText(text: String): ContactsListRobot {
        onView(withId(SEARCH_BUTTON_ID)).perform(click())
        onView(withId(SEARCH_TEXT_FIELD_ID)).perform(typeText(text), closeSoftKeyboard())
        return this
    }

    fun removeSearchText(): ContactsListRobot {
        onView(withId(SEARCH_CLOSE_BUTTON_ID)).perform(click())
        return this
    }

    fun navigateToContact(contactName: String): ContactRobot {
        onView(withId(RECYCLER_VIEW_ID))
                .perform(RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(hasDescendant(withText(contactName)), click()))
        return ContactRobot()
    }

    fun navigateToMap(): ContactsMapRobot {
        onView(withId(CONTACTS_MAP_BUTTON_ID)).perform(click())
        return ContactsMapRobot()
    }
}
