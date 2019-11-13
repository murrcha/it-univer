package com.kkaysheva.ituniver.kotlin

import android.Manifest
import android.content.OperationApplicationException
import android.os.RemoteException
import android.support.test.InstrumentationRegistry
import android.support.test.rule.ActivityTestRule
import android.support.test.rule.GrantPermissionRule
import android.support.test.runner.AndroidJUnit4
import com.kkaysheva.ituniver.ContactsHelper
import com.kkaysheva.ituniver.kotlin.robots.ContactMapRobot
import com.kkaysheva.ituniver.kotlin.robots.ContactsListRobot
import com.kkaysheva.ituniver.presentation.main.MainActivity
import org.junit.*
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ContactMapTest {

    companion object {

        @JvmField
        @ClassRule
        val permissionRule: GrantPermissionRule = GrantPermissionRule.grant(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.WRITE_CONTACTS)

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

    private lateinit var robot: ContactMapRobot

    @Before
    fun setUp() {
        robot = ContactsListRobot()
                .navigateToContact("Alex Fetcher")
                .navigateToMap();
    }

    @Test
    fun testContactMapDisplayed() {
        with(robot) {
            action {
                showContactMap()
            }.check {
                contactMapDisplayedSuccess()
            }
        }
    }

    @Test
    fun testBackToContactDetail() {
        with(robot.navigateBack()) {
            action {
                showContactDetail()
            }.check {
                contactDetailDisplayedSuccess()
            }
        }
    }
}
