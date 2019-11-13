package com.kkaysheva.ituniver.java.robots;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static com.kkaysheva.ituniver.IdsResources.CONTACT_MAP_LAYOUT_ID;

public class ContactMapRobot {

    public ContactRobot navigateBack() {
        pressBack();
        return new ContactRobot();
    }

    public void contactMapDisplayed() {
        onView(withId(CONTACT_MAP_LAYOUT_ID)).check(matches(isCompletelyDisplayed()));
    }
}
