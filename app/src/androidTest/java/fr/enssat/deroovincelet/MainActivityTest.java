package fr.enssat.deroovincelet;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.VideoView;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.InstrumentationRegistry;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.web.assertion.WebViewAssertions.webMatches;
import static androidx.test.espresso.web.model.Atoms.getCurrentUrl;
import static androidx.test.espresso.web.sugar.Web.onWebView;
import static fr.enssat.deroovincelet.MainActivity.videoDuration;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.Matchers.allOf;
import static org.junit.Assert.assertEquals;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void mainActivityTest() {
        Context appContext = InstrumentationRegistry.getTargetContext();
        Assert.assertEquals("fr.enssat.deroovincelet", appContext.getPackageName());
    }

    /*
    * Teste si les valeurs des boutons sont correctes
    * */
    @Test
    public void buttonsHaveTheirRightValues(){
        // Bouton INTRO
        ViewInteraction button1 = onView(allOf(withId(R.id.button1)));
        button1.check(matches(withText("INTRO")));

        // Bouton TITLE
        ViewInteraction button2 = onView(allOf(withId(R.id.button2)));
        button2.check(matches(withText("TITLE")));

        // Bouton BUTTERFLIES
        ViewInteraction button3 = onView(allOf(withId(R.id.button3)));
        button3.check(matches(withText("BUTTERFLIES")));

        // Bouton ASSAULT
        ViewInteraction button4 = onView(allOf(withId(R.id.button4)));
        button4.check(matches(withText("ASSAULT")));

        // Bouton PAYBACK
        ViewInteraction button5 = onView(allOf(withId(R.id.button5)));
        button5.check(matches(withText("PAYBACK")));

        // Bouton CAST
        ViewInteraction button6 = onView(allOf(withId(R.id.button6)));
        button6.check(matches(withText("CAST")));
    }

    /*
    * Teste si le clic du boutton Title entraine la modification de la WebView vers la page wikipédia "Title"
    * */
    @Test
    public void webViewIsTheRightUrlWhenClickIsPerformed(){
        ViewInteraction button = onView(withId(R.id.button2));
        button.perform(click());
        onWebView().check(webMatches(getCurrentUrl(), containsString("Title")));
    }

    /*
     * Teste si le clic du boutton Title entraine la modification de la durée de la vidéo à 25000
     * */
    @Test
    public void webViewIsTheRightUrlWhenVideoIsClicked(){
        ViewInteraction button = onView(withId(R.id.button3));
        button.perform(click());
        assertEquals(76000, videoDuration);
    }

    private static Matcher<View> childAtPosition(final Matcher<View> parentMatcher, final int position) {

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
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
