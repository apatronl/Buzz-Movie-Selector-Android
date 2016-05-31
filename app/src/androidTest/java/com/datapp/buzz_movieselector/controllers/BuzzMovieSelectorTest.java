// Alejandrina Patron Lopez
// GT ID: apl7
// DAT APp (44)

package com.datapp.buzz_movieselector.controllers;

import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import android.database.Cursor;
import android.support.test.espresso.Espresso;
import android.support.test.runner.AndroidJUnit4;

import static android.support.test.espresso.matcher.ViewMatchers.hasErrorText;
import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.support.test.rule.ActivityTestRule;

import com.datapp.buzz_movieselector.R;
import com.datapp.buzz_movieselector.controllers.OpeningScreenActivity;
import com.datapp.buzz_movieselector.controllers.RegisterActivity;
import com.datapp.buzz_movieselector.model.DBHelper;
import com.datapp.buzz_movieselector.model.User;

import java.sql.SQLException;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.core.AllOf.allOf;

@RunWith(AndroidJUnit4.class)
public class BuzzMovieSelectorTest {

    private static final String TEST_USERNAME = "buzz";
    private static final String TEST_PASSWORD = "tech";
    private static final String TEST_MAJOR = "CS";
    private static final String TEST_INTERESTS = "N/A";
    private static final String TEST_EMPTY_USERNAME = "Please enter a username.";
    private static final String TEST_EMPTY_PASSWORD = "Please enter a password.";
    private static final String TEST_EMPTY_MAJOR = "Please select a major";
    private static final String TEST_USERNAME_TAKEN = "Username taken, please choose a different one";
    private static final String TEST_REGISTERED = "Registered";



    @Rule
    public ActivityTestRule<RegisterActivity> mActivityRule = new ActivityTestRule<>(RegisterActivity.class);
    public ActivityTestRule<OpeningScreenActivity> open = new ActivityTestRule<>(OpeningScreenActivity.class);

    @Test
    public void testRegistrationSuccess() {
        // Enter username "buzz"
        onView(withId(R.id.user)).perform(typeText(TEST_USERNAME), closeSoftKeyboard());
        // Enter password "tech"
        onView(withId(R.id.password)).perform(typeText(TEST_PASSWORD), closeSoftKeyboard());
        // Select major CS
        onView(withId(R.id.registerMajor)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is(TEST_MAJOR))).perform(click());
        Espresso.closeSoftKeyboard();
        // Press Register
        onView(withId(R.id.email_sign_in_button)).perform(click());
        // Get database where the new user's data was saved
        DBHelper database = mActivityRule.getActivity().getMydb();
        Cursor cursor = null;
        // Get new user information from database
        try {
            cursor = database.getMajor(TEST_USERNAME);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // User object with that info
        User currUser = new User(cursor);

        assertEquals("Incorrect username in database ", TEST_USERNAME, currUser.getName());
        assertEquals("Incorrect password in database ", TEST_PASSWORD, currUser.getPassword());
        assertEquals("Incorrect major in database ", TEST_MAJOR, currUser.getMajor());
        assertEquals("Incorrect default interests in database ", TEST_INTERESTS, currUser.getInterests());
        onView(withText(TEST_REGISTERED)).inRoot(withDecorView(not(mActivityRule.getActivity().getWindow().getDecorView()))).check(matches(isDisplayed()));
        database.deleteUser(database.getId(TEST_USERNAME));
    }

    @Test
    public void testUsernameTaken() {
        mActivityRule.getActivity().getMydb().insertUser(TEST_USERNAME, TEST_PASSWORD, TEST_MAJOR);
        onView(withId(R.id.user)).perform(typeText(TEST_USERNAME), closeSoftKeyboard());
        // Enter password "tech"
        onView(withId(R.id.password)).perform(typeText(TEST_PASSWORD), closeSoftKeyboard());
        // Select major CS
        onView(withId(R.id.registerMajor)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is(TEST_MAJOR))).perform(click());
        // Press Register
        onView(withId(R.id.email_sign_in_button)).perform(click());
        onView(withText(TEST_USERNAME_TAKEN)).inRoot(withDecorView(not(mActivityRule.getActivity().getWindow().getDecorView()))).check(matches(isDisplayed()));
        DBHelper database = mActivityRule.getActivity().getMydb();
        database.deleteUser(database.getId(TEST_USERNAME));
    }

    @Test
    public void testEmptyUsername() {
        // No username entered

        // Enter password "tech"
        onView(withId(R.id.password)).perform(typeText(TEST_PASSWORD), closeSoftKeyboard());
        // Select major CS
        onView(withId(R.id.registerMajor)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is(TEST_MAJOR))).perform(click());
        // Press Register
        onView(withId(R.id.email_sign_in_button)).perform(click());
        onView(withId(R.id.user)).check(matches(hasErrorText(TEST_EMPTY_USERNAME)));
    }

    @Test
    public void testEmptyPassword() {
        // Enter username "buzz"
        onView(withId(R.id.user)).perform(typeText(TEST_USERNAME), closeSoftKeyboard());

        // No password entered

        // Select major CS
        onView(withId(R.id.registerMajor)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is(TEST_MAJOR))).perform(click());
        // Press Register
        onView(withId(R.id.email_sign_in_button)).perform(click());
        onView(withId(R.id.password)).check(matches(hasErrorText(TEST_EMPTY_PASSWORD)));
    }

    @Test
    public void testEmptyMajor() {
        // Enter username "buzz"
        onView(withId(R.id.user)).perform(typeText(TEST_USERNAME), closeSoftKeyboard());
        // Enter password "tech"
        onView(withId(R.id.password)).perform(typeText(TEST_PASSWORD), closeSoftKeyboard());
        // No major selected
        // Press Register
        onView(withId(R.id.email_sign_in_button)).perform(click());
        onView(withText(TEST_EMPTY_MAJOR)).inRoot(withDecorView(not(mActivityRule.getActivity().getWindow().getDecorView()))).check(matches(isDisplayed()));
    }
}
