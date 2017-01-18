package cz.muni.fi.pv256.movio2.uco_396546_themoviedb;

import android.support.test.espresso.PerformException;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.util.HumanReadables;
import android.support.test.espresso.util.TreeIterables;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;

import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

import cz.muni.fi.pv256.movio2.uco_396546_themoviedb.view.MainActivity;

/**
 * Created by Huvart on 16/01/2017.
 */

//test created manualy
@RunWith(AndroidJUnit4.class)
public class MainActivityEspressoTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void clickOnHamburgerMenu() throws Exception{
        onView(withId(R.id.left_drawer)).check(ViewAssertions.matches(not(isDisplayed())));
        onView(withContentDescription("drawer_open")).perform(click());

        onView(withId(R.id.left_drawer)).check(ViewAssertions.matches(isDisplayed()));
    }

    @Test
    public void clickOnFavouriteMenu() throws Exception{
        onView(withContentDescription("drawer_open")).perform(click());
        onView(withText("Favourite")).perform(click());

        onView(withId(R.id.error_text)).check(ViewAssertions.matches(isDisplayed()));
    }

    @Test
    public void clickOnReloadMenu() throws Exception{
        onView(withContentDescription("drawer_open")).perform(click());
        onView(withText("Reload")).perform(click());
        //view just reload
    }
}
