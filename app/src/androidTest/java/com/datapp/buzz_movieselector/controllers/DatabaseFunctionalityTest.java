package com.datapp.buzz_movieselector.controllers;
// Daniel Barrundia
// GT ID: dbarrundia3
// DAT APp (44)

import android.support.test.runner.AndroidJUnit4;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.support.test.rule.ActivityTestRule;
import com.datapp.buzz_movieselector.model.DBHelper;
import java.sql.SQLException;

@RunWith(AndroidJUnit4.class)
public class DatabaseFunctionalityTest {

    private static final String TEST_USERNAME = "XXX";
    private static final String TEST_WRONG_USERNAME = "xxx";
    private static final String TEST_PASSWORD = "YYY";
    private static final String TEST_WRONG_PASSWORD = "yyy";
    private static final String TEST_MAJOR = "CS";


    @Rule
    public ActivityTestRule<RegisterActivity> mActivityRule = new ActivityTestRule<>(RegisterActivity.class);
    public ActivityTestRule<OpeningScreenActivity> open = new ActivityTestRule<>(OpeningScreenActivity.class);

    @Before
    public void setUp() {
        DBHelper database = new DBHelper(mActivityRule.getActivity());
        database.open();
        database.insertUser(TEST_USERNAME, TEST_PASSWORD,TEST_MAJOR);
    }
    @Test
    public void testValidateLogin() {
        DBHelper database = new DBHelper(mActivityRule.getActivity());
        database.open();
        assertEquals(1, database.validateLogin(TEST_USERNAME, TEST_PASSWORD));
        database.close();
    }
    @Test
    public void testWrongPassword() {
        DBHelper database = new DBHelper(mActivityRule.getActivity());
        database.open();
        assertEquals(0, database.validateLogin(TEST_USERNAME, TEST_WRONG_PASSWORD));
        database.close();
    }
    @Test
    public void testWrongUsernameandPassword() {
        DBHelper database = new DBHelper(mActivityRule.getActivity());
        database.open();
        assertEquals(-1, database.validateLogin(TEST_WRONG_USERNAME, TEST_WRONG_PASSWORD));
        database.close();
    }
    @Test
    public void testAvailableUser() {
        DBHelper database = new DBHelper(mActivityRule.getActivity());
        database.open();
        assertTrue(database.availableUsername(TEST_WRONG_USERNAME));
        database.close();
    }
    @Test
    public void testNotAvailableUser() {
        DBHelper database = new DBHelper(mActivityRule.getActivity());
        database.open();
        assertFalse(database.availableUsername(TEST_USERNAME));
        database.close();
    }
    @Test
    public void addDuplicateUsername() {
        DBHelper database = new DBHelper(mActivityRule.getActivity());
        database.open();
        boolean success = database.insertUser(TEST_USERNAME, TEST_PASSWORD,TEST_MAJOR);
        assertFalse(success);
    }
    @Test
    public void testActiveUser() {
        DBHelper database = new DBHelper(mActivityRule.getActivity());
        database.open();
        database.activateUser(TEST_USERNAME);
        boolean success = database.isActive(TEST_USERNAME);
        assertTrue(success);
    }
    @Test
    public void testBannedUser() {
        DBHelper database = new DBHelper(mActivityRule.getActivity());
        database.open();
        database.banUser(TEST_USERNAME);
        boolean success = database.isBanned(TEST_USERNAME);
        assertTrue(success);
    }
    @Test
    public void testLockededUser() {
        DBHelper database = new DBHelper(mActivityRule.getActivity());
        database.open();
        database.lockUser(TEST_USERNAME);
        boolean success = database.isLocked(TEST_USERNAME);
        assertTrue(success);
    }
}

