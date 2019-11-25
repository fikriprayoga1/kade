package id.co.blogspot.fikriprayoga.www.footballmatchschedule.frontendcontroller.searchteam

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.Espresso.pressBack
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import id.co.blogspot.fikriprayoga.www.footballmatchschedule.R.id.recyclerView_activity_search_team_1_1
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class InstrumentationTest {
    @Rule
    @JvmField var activityRule = ActivityTestRule(SearchTeamActivity::class.java)

    @Test
    fun testRecyclerViewBehaviour() {
        onView(withId(recyclerView_activity_search_team_1_1))
                .check(matches(isDisplayed()))
        onView(withId(recyclerView_activity_search_team_1_1)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(10))

    }

    @Test
    fun testAppBehaviour() {
        onView(withText("Barcelona"))
                .check(matches(isDisplayed()))
        onView(withText("Barcelona")).perform(click())
    }

}