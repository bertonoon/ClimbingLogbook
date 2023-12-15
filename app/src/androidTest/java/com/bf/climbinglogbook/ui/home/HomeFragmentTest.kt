package com.bf.climbinglogbook.ui.home

import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.filters.LargeTest
import com.bf.climbinglogbook.MainActivity
import com.bf.climbinglogbook.R
import com.bf.climbinglogbook.launchFragmentInHiltContainer
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@LargeTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class HomeFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setup() {
        hiltRule.inject()
    }


    @Test
    fun testTest() {
        val navController = TestNavHostController(
            ApplicationProvider.getApplicationContext()
        )

        launchFragmentInHiltContainer<HomeFragment> {
            viewLifecycleOwnerLiveData.observeForever { viewLifecycleOwner ->
                navController.setGraph(R.navigation.mobile_navigation)
                navController.setCurrentDestination(R.id.navigation_home)
                if (viewLifecycleOwner != null) {
                    Navigation.setViewNavController(requireView(), navController)
                }
            }
        }

        onView(withId(R.id.ivGradeCalc)).check(matches(isDisplayed()))
        onView(withId(R.id.ivGradeCalc)).perform(ViewActions.click())
        assertThat(navController.currentDestination?.id).isEqualTo(R.id.navigation_grade_calc)
    }

}