package com.kkaysheva.ituniver.kotlin

import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(ContactsListTest::class, ContactTest::class, ContactsMapTest::class, ContactMapTest::class)
class InstrumentationTestSuite {
}