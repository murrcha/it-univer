package com.kkaysheva.ituniver.kotlin

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.Manifest.permission.WRITE_CONTACTS
import android.content.OperationApplicationException
import android.os.RemoteException
import android.support.test.InstrumentationRegistry
import android.support.test.rule.ActivityTestRule
import android.support.test.rule.GrantPermissionRule
import android.support.test.runner.AndroidJUnit4
import com.kkaysheva.ituniver.ContactsHelper
import com.kkaysheva.ituniver.kotlin.robots.ContactsListRobot
import com.kkaysheva.ituniver.presentation.main.MainActivity
import org.junit.*
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ContactsListTest {

    companion object {

        @JvmField
        @ClassRule
        val permissionRule: GrantPermissionRule = GrantPermissionRule.grant(ACCESS_FINE_LOCATION, WRITE_CONTACTS)

        @JvmStatic
        @BeforeClass
        @Throws(OperationApplicationException::class, RemoteException::class)
        fun setData() {
            ContactsHelper.clear(InstrumentationRegistry.getContext())
            ContactsHelper.fill(InstrumentationRegistry.getContext())
        }
    }

    @JvmField
    @Rule
    val activityRule = ActivityTestRule(MainActivity::class.java)

    private lateinit var robot: ContactsListRobot

    @Before
    fun setUp() {
        robot = ContactsListRobot()
    }

    @Test
    fun testContactsDisplayed() {
        with(robot) {
            action {
                showContactsList()
            }.check {
                contactsListDisplayedSuccess()
            }
        }
    }

    @Test
    fun testContactsNotDisplayed() {
        with(robot) {
            action {
                searchByText("any text")
            }.check {
                noContactsTextDisplayedSuccess()
                contactsListNotDisplayedSuccess()
            }
        }
    }

    @Test
    fun testSearchContact() {
        with(robot) {
            action {
                searchByText("Alex Fetcher")
            }.check {
                contactsListDisplayedSuccess()
                contactDisplayedSuccess("Alex Fetcher")
            }
        }
    }

    @Test
    fun testRemoveSearchText() {
        with(robot) {
            action {
                searchByText("abcdef")
            }.check {
                contactsListNotDisplayedSuccess()
            }
            action {
                removeSearchText()
            }.check {
                contactsListDisplayedSuccess()
            }
        }
    }

    @Test
    fun testNavigateToContact() {
        with(robot.navigateToContact("Alex Fetcher")) {
            action {
                showContactDetail()
            }.check {
                contactDetailDisplayedSuccess()
            }
        }
    }

    @Test
    fun testNavigateToMap() {
        with(robot.navigateToMap()) {
            action {
                showContactsMap()
            }.check {
                contactsMapDisplayedSuccess()
            }
        }
    }
}
