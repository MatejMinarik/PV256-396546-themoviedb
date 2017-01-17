package cz.muni.fi.pv256.movio2.uco_396546_themoviedb.view;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.*;
import static android.support.test.espresso.assertion.ViewAssertions.*;
import static android.support.test.espresso.matcher.ViewMatchers.*;

import cz.muni.fi.pv256.movio2.uco_396546_themoviedb.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void mainActivityTest() {
        ViewInteraction appCompatImageButton = onView(
allOf(withContentDescription("drawer_open"),
withParent(allOf(withId(R.id.action_bar),
withParent(withId(R.id.action_bar_container)))),
isDisplayed()));
        appCompatImageButton.perform(click());
        
        ViewInteraction relativeLayout = onView(
allOf(childAtPosition(
allOf(withId(R.id.left_drawer),
withParent(withId(R.id.drawer_layout))),
2),
isDisplayed()));
        relativeLayout.perform(click());
        
        ViewInteraction imageView = onView(
allOf(withId(R.id.list_item_icon),
withParent(withId(R.id.list_item)),
isDisplayed()));
        imageView.perform(click());
        
        ViewInteraction floatingActionButton = onView(
withId(R.id.floatingActionButton));
        floatingActionButton.perform(scrollTo(), click());
        
        ViewInteraction appCompatImageButton2 = onView(
allOf(withContentDescription("Navigate up"),
withParent(allOf(withId(R.id.action_bar),
withParent(withId(R.id.action_bar_container)))),
isDisplayed()));
        appCompatImageButton2.perform(click());
        
        ViewInteraction appCompatImageButton3 = onView(
allOf(withContentDescription("drawer_open"),
withParent(allOf(withId(R.id.action_bar),
withParent(withId(R.id.action_bar_container)))),
isDisplayed()));
        appCompatImageButton3.perform(click());
        
        ViewInteraction relativeLayout2 = onView(
allOf(childAtPosition(
allOf(withId(R.id.left_drawer),
withParent(withId(R.id.drawer_layout))),
1),
isDisplayed()));
        relativeLayout2.perform(click());
        
        ViewInteraction frameLayout = onView(
allOf(childAtPosition(
childAtPosition(
withId(R.id.saved_movies_recycler_view),
0),
0),
isDisplayed()));
        frameLayout.check(matches(isDisplayed()));
        
        ViewInteraction appCompatImageButton4 = onView(
allOf(withContentDescription("drawer_open"),
withParent(allOf(withId(R.id.action_bar),
withParent(withId(R.id.action_bar_container)))),
isDisplayed()));
        appCompatImageButton4.perform(click());
        
        ViewInteraction appCompatImageView = onView(
allOf(withId(R.id.list_item_icon), isDisplayed()));
        appCompatImageView.perform(click());
        
        ViewInteraction floatingActionButton2 = onView(
withId(R.id.floatingActionButton));
        floatingActionButton2.perform(scrollTo(), click());
        
        pressBack();
        
        ViewInteraction appCompatImageButton5 = onView(
allOf(withContentDescription("drawer_open"),
withParent(allOf(withId(R.id.action_bar),
withParent(withId(R.id.action_bar_container)))),
isDisplayed()));
        appCompatImageButton5.perform(click());
        
        ViewInteraction relativeLayout3 = onView(
allOf(childAtPosition(
allOf(withId(R.id.left_drawer),
withParent(withId(R.id.drawer_layout))),
2),
isDisplayed()));
        relativeLayout3.perform(click());
        
        ViewInteraction textView = onView(
allOf(withId(R.id.error_text), withText("NO FAVOURITE MOVIES"),
childAtPosition(
childAtPosition(
withId(R.id.movie_list_fragment),
0),
0),
isDisplayed()));
        textView.check(matches(withText("NO FAVOURITE MOVIES")));
        
        ViewInteraction appCompatImageButton6 = onView(
allOf(withContentDescription("drawer_close"),
withParent(allOf(withId(R.id.action_bar),
withParent(withId(R.id.action_bar_container)))),
isDisplayed()));
        appCompatImageButton6.perform(click());
        
        ViewInteraction appCompatImageButton7 = onView(
allOf(withContentDescription("drawer_open"),
withParent(allOf(withId(R.id.action_bar),
withParent(withId(R.id.action_bar_container)))),
isDisplayed()));
        appCompatImageButton7.perform(click());
        
        ViewInteraction relativeLayout4 = onView(
allOf(childAtPosition(
allOf(withId(R.id.left_drawer),
withParent(withId(R.id.drawer_layout))),
0),
isDisplayed()));
        relativeLayout4.perform(click());
        
        ViewInteraction textView2 = onView(
allOf(withText("uco_396546_themoviedb"),
childAtPosition(
allOf(withId(R.id.action_bar),
childAtPosition(
withId(R.id.action_bar_container),
0)),
1),
isDisplayed()));
        textView2.check(matches(withText("uco_396546_themoviedb")));
        
        }

        private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup)parent).getChildAt(position));
            }
        };
    }
    }
