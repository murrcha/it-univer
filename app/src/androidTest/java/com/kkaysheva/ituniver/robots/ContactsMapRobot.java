package com.kkaysheva.ituniver.robots;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static com.kkaysheva.ituniver.robots.IdsResources.CONTACTS_MAP_LAYOUT_ID;

public class ContactsMapRobot {

    public ContactsListRobot navigateBack() {
        pressBack();
        return new ContactsListRobot();
    }

    public void contactsMapDisplayed() {
        onView(withId(CONTACTS_MAP_LAYOUT_ID)).check(matches(isCompletelyDisplayed()));
    }
}
