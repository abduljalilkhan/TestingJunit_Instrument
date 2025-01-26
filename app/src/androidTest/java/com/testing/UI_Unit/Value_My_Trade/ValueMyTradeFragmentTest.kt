package com.testing.unitTesting.Value_My_Trade

import androidx.fragment.app.testing.FragmentScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.testing.unitTesting.R
import org.hamcrest.CoreMatchers.not
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ValueMyTradeFragmentTest {

    private lateinit var scenario: FragmentScenario<ValueMy_Trade>

    @Before
    fun setUp() {
        // Launch the Fragment
        scenario = FragmentScenario.launchInContainer(ValueMy_Trade::class.java)
    }

    @Test
    fun testRecyclerViewVisibility() {
        // Check if RecyclerView is displayed
        onView(withId(R.id.recyclerview)).check(matches(isDisplayed()))
    }

    @Test
    fun testNoDataTextViewVisibility() {
        // Check if the "No Service" TextView is displayed when there is no data
        onView(withId(R.id.tvNo_Data)).check(matches(withText("No Service")))
        onView(withId(R.id.tvNo_Data)).check(matches(isDisplayed()))
    }

    @Test
    fun testRecyclerViewVisibilityWhenListIsEmpty() {
        // Simulate an empty list
        // Create an empty ArrayList<HashMap<String, String>>
        val emptyList = ArrayList<String>()

        // Simulate the empty list in the fragment
        scenario.onFragment { fragment ->
            fragment.updateList(emptyList) // Pass the correctly typed empty list
        }

        // Check if the "No Service" TextView is displayed
        onView(withId(R.id.tvNo_Data)).check(matches(isDisplayed()))
        // Check if the RecyclerView is not displayed
        onView(withId(R.id.recyclerview)).check(matches(not(isDisplayed())))
    }

    @Test
    fun testRecyclerViewVisibilityWhenListHasData() {
        // Simulate a non-empty list
        val mockData = listOf("Item 1", "Item 2") // Replace with your actual item type
        scenario.onFragment { fragment ->
            fragment.updateList(mockData as java.util.ArrayList<String>?) // Update the list with mock data
        }

        // Check if the RecyclerView is displayed
        onView(withId(R.id.recyclerview)).check(matches(isDisplayed()))
        // Check if the "No Service" TextView is not displayed
        onView(withId(R.id.tvNo_Data)).check(matches(not(isDisplayed())))
    }



    @Test
    fun testButtonClick() {
        // Click the button and verify its behavior
        onView(withId(R.id.btn_trade_DifferntCar)).perform(click())
        // Add assertions here to verify the expected outcome of the button click
    }
}