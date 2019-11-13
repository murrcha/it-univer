package com.kkaysheva.ituniver.java.robots;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static com.kkaysheva.ituniver.IdsResources.CONTACT_LAYOUT_ID;
import static com.kkaysheva.ituniver.IdsResources.CONTACT_MAP_BUTTON_ID;

public class ContactRobot {

    public ContactsListRobot navigateBack() {
        pressBack();
        return new ContactsListRobot();
    }

    public ContactMapRobot navigateToMap() {
        onView(withId(CONTACT_MAP_BUTTON_ID)).perform(click());
        return new ContactMapRobot();
    }

    public void contactDetailDisplayed() {
        onView(withId(CONTACT_LAYOUT_ID)).check(matches(isCompletelyDisplayed()));
    }
}
