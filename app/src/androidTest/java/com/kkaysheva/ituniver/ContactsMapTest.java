package com.kkaysheva.ituniver;

import android.content.OperationApplicationException;
import android.os.RemoteException;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.rule.GrantPermissionRule;
import android.support.test.runner.AndroidJUnit4;

import com.kkaysheva.ituniver.presentation.main.MainActivity;
import com.kkaysheva.ituniver.robots.ContactsListRobot;
import com.kkaysheva.ituniver.robots.ContactsMapRobot;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.WRITE_CONTACTS;

@SuppressWarnings("squid:S2699")
@RunWith(AndroidJUnit4.class)
public class ContactsMapTest {

    @ClassRule
    public static GrantPermissionRule permissionRule =
            GrantPermissionRule.grant(ACCESS_FINE_LOCATION, WRITE_CONTACTS);

    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class);

    private ContactsMapRobot robot;

    @BeforeClass
    public static void setData() throws OperationApplicationException, RemoteException {
        ContactsHelper.fill(InstrumentationRegistry.getContext());
    }

    @AfterClass
    public static void removeData() {
        ContactsHelper.clear(InstrumentationRegistry.getContext());
    }

    @Before
    public void setUp() {
        robot = new ContactsListRobot()
                .navigateToMap();
    }

    @After
    public void tearDown() {
        robot = null;
    }

    @Test
    public void testContactsMapDisplayed() {
        robot.contactsMapDisplayed();
    }

    @Test
    public void testBackToContactsList() {
        robot.navigateBack()
                .contactsListDisplayed();
    }
}
