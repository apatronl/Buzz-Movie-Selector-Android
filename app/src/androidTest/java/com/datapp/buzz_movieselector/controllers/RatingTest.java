package com.datapp.buzz_movieselector.controllers;
import com.datapp.buzz_movieselector.model.Rating;
import com.datapp.buzz_movieselector.model.RatingTable;
import com.datapp.buzz_movieselector.model.Movie;
import com.datapp.buzz_movieselector.model.User;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import static junit.framework.TestCase.assertEquals;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;
import static org.mockito.Mockito.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import android.content.SharedPreferences;


import static org.junit.Assert.assertNull;

/**
 * Created by sstil on 4/5/2016.
 * Tests cases for when a user tries making a new Rating.
 */

@RunWith(MockitoJUnitRunner.class)
public class RatingTest {

    private static final int MOCK_RATING = 5;
    private static final String MOCK_COMMENT = "Awesome movie!";

    /**
     * When a valid comment and rating are entered, a rating should be inserted.
     */
    @Test
    public void testSuccessfulRating() {
        RatingTable rt = mock(RatingTable.class);
        int movieYear = 2016;
        ArrayList<Rating> movieRating = null;
        Movie movie = new Movie("771363115", "Batman v Superman: Dawn of Justice", movieYear,"http://resizing.flixster.com/ie1vj_rolPLF4dTOLl1SvPw9MpU=/54x80/v1.bTsxMTcxNDAxMztqOzE2OTM4OzIwNDg7NDA1MDs2MDAw", movieRating);
        User user = new User("tay", "lor", "CS", "testing");
        when(rt.insertRating(movie,user, MOCK_COMMENT, MOCK_RATING)).thenReturn(true);
        assertEquals(true, rt.insertRating(movie,user, MOCK_COMMENT, MOCK_RATING));
    }

    /**
     * When a valid comment is entered, but no rating is selected,
     * a rating should NOT be inserted.
     */
    public void testNoRatingSelected() {
        RatingTable rt = mock(RatingTable.class);
        int movieYear = 2016;
        ArrayList<Rating> movieRating = null;
        Movie movie = new Movie("771363115", "Batman v Superman: Dawn of Justice", movieYear,"http://resizing.flixster.com/ie1vj_rolPLF4dTOLl1SvPw9MpU=/54x80/v1.bTsxMTcxNDAxMztqOzE2OTM4OzIwNDg7NDA1MDs2MDAw", movieRating);
        User user = new User("tay", "lor", "CS", "testing");
        when(rt.insertRating(movie,user, MOCK_COMMENT, MOCK_RATING)).thenReturn(true);
        assertEquals(false, rt.insertRating(movie,user, MOCK_COMMENT, -1));
    }

    /**
     * When a valid rating is chosen, but no comment is entered,
     * a rating should NOT be inserted.
     */
    public void testNoCommentEntered() {
        RatingTable rt = mock(RatingTable.class);
        int movieYear = 2016;
        ArrayList<Rating> movieRating = null;
        Movie movie = new Movie("771363115", "Batman v Superman: Dawn of Justice", movieYear,"http://resizing.flixster.com/ie1vj_rolPLF4dTOLl1SvPw9MpU=/54x80/v1.bTsxMTcxNDAxMztqOzE2OTM4OzIwNDg7NDA1MDs2MDAw", movieRating);
        User user = new User("tay", "lor", "CS", "testing");
        when(rt.insertRating(movie,user, MOCK_COMMENT, MOCK_RATING)).thenReturn(true);
        assertEquals(false, rt.insertRating(movie,user, null, MOCK_RATING));
    }

    /**
     * When neither a valid rating is chosen, nor a comment is entered,
     * a rating should NOT be inserted.
     */
    public void testNoCommentOrRating() {
        RatingTable rt = mock(RatingTable.class);
        int movieYear = 2016;
        ArrayList<Rating> movieRating = null;
        Movie movie = new Movie("771363115", "Batman v Superman: Dawn of Justice", movieYear,"http://resizing.flixster.com/ie1vj_rolPLF4dTOLl1SvPw9MpU=/54x80/v1.bTsxMTcxNDAxMztqOzE2OTM4OzIwNDg7NDA1MDs2MDAw", movieRating);
        User user = new User("tay", "lor", "CS", "testing");
        when(rt.insertRating(movie,user, MOCK_COMMENT, MOCK_RATING)).thenReturn(true);
        assertEquals(false, rt.insertRating(movie,user, null, -1));
    }

}
