package com.kkaysheva.ituniver.java;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ContactsListTest.class,
        ContactTest.class, ContactsMapTest.class, ContactMapTest.class})
public class InstrumentationTestSuite {
}
