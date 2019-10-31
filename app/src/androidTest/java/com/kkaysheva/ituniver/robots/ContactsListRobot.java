package com.kkaysheva.ituniver.robots;

import android.support.test.espresso.contrib.RecyclerViewActions;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasChildCount;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.kkaysheva.ituniver.robots.IdsResources.CONTACTS_MAP_BUTTON_ID;
import static com.kkaysheva.ituniver.robots.IdsResources.ITEM_VIEW_NAME_ID;
import static com.kkaysheva.ituniver.robots.IdsResources.NO_CONTACTS_TEXT_VIEW_ID;
import static com.kkaysheva.ituniver.robots.IdsResources.RECYCLER_VIEW_ID;
import static com.kkaysheva.ituniver.robots.IdsResources.SEARCH_BUTTON_ID;
import static com.kkaysheva.ituniver.robots.IdsResources.SEARCH_CLOSE_BUTTON_ID;
import static com.kkaysheva.ituniver.robots.IdsResources.SEARCH_TEXT_FIELD_ID;

public class ContactsListRobot {

    public ContactsListRobot contactsListDisplayed() {
        onView(withId(RECYCLER_VIEW_ID))
                .check(matches(isCompletelyDisplayed()));
        onView(withId(RECYCLER_VIEW_ID))
                .check(matches(hasMinimumChildCount(1)));
        return this;
    }

    public ContactsListRobot contactsListNotDisplayed() {
        onView(withId(RECYCLER_VIEW_ID))
                .check(matches(hasChildCount(0)));
        return this;
    }

    public void contactDisplayed(String contactName) {
        onView(withId(ITEM_VIEW_NAME_ID)).check(matches(withText(contactName)));
    }

    public ContactsListRobot noContactsTextDisplayed() {
        onView(withId(NO_CONTACTS_TEXT_VIEW_ID))
                .check(matches(isCompletelyDisplayed()));
        return this;
    }

    public ContactsListRobot searchByText(String text) {
        onView(withId(SEARCH_BUTTON_ID)).perform(click());
        onView(withId(SEARCH_TEXT_FIELD_ID)).perform(typeText(text), closeSoftKeyboard());
        return this;
    }

    public ContactsListRobot removeSearchText() {
        onView(withId(SEARCH_CLOSE_BUTTON_ID)).perform(click());
        return this;
    }

    public ContactRobot navigateToContact(String contactName) {
        onView(withId(RECYCLER_VIEW_ID))
                .perform(RecyclerViewActions.actionOnItem(hasDescendant(withText(contactName)), click()));
        return new ContactRobot();
    }

    public ContactsMapRobot navigateToMap() {
        onView(withId(CONTACTS_MAP_BUTTON_ID)).perform(click());
        return new ContactsMapRobot();
    }
}
