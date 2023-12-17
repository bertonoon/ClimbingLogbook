package com.bf.climbinglogbook

import androidx.navigation.NavController
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.filters.LargeTest
import androidx.test.internal.runner.junit4.statement.UiThreadStatement.runOnUiThread
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@LargeTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class MainActivityTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    private lateinit var navController: TestNavHostController

    @Before
    fun setup() {
        hiltRule.inject()

        val navController = TestNavHostController(
            ApplicationProvider.getApplicationContext()
        )

        runOnUiThread {
            navController.setGraph(R.navigation.mobile_navigation)
            navController.setCurrentDestination(R.id.navigation_home)
        }
    }

    @Test
    fun navigationBottomNavBarIsDisplayed() {
        Espresso.onView(withId(R.id.nav_view)).check(matches(isDisplayed()))
    }
    @Test
    fun navigationBottomNavBarIconLogbookIsDisplayed() {
        Espresso.onView(withId(R.id.navigation_logbook)).check(matches(isDisplayed()))
    }
    @Test
    fun navigationBottomNavBarIconHomeIsDisplayed() {
        Espresso.onView(withId(R.id.navigation_home)).check(matches(isDisplayed()))
    }
    @Test
    fun navigationBottomNavBarIconStatisticsIsDisplayed() {
        Espresso.onView(withId(R.id.navigation_statistics)).check(matches(isDisplayed()))
    }
}