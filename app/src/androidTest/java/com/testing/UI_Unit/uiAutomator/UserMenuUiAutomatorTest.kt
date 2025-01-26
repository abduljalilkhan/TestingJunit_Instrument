package com.testing.unitTesting.uiAutomator

import android.util.Log
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiObject
import androidx.test.uiautomator.UiObjectNotFoundException
import androidx.test.uiautomator.UiSelector
import androidx.test.uiautomator.UiDevice.getInstance
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.uiautomator.By
import androidx.test.uiautomator.Until
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UserMenuUiAutomatorTest {

    private lateinit var device: UiDevice

    @Before
    fun setUp() {
        device = getInstance(InstrumentationRegistry.getInstrumentation())

        // Start the app
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val intent = context.packageManager.getLaunchIntentForPackage("com.testing.unitTesting")
        context.startActivity(intent)

        // Wait for the UserMenu fragment to be displayed
        device.wait(Until.hasObject(By.pkg("com.testing.unitTesting").depth(0)), 5000*10)
    }

    @Test
    fun testUserMenuDisplaysCorrectData() {
        // Wait for the RecyclerView to be displayed
        device.wait(Until.hasObject(By.res("com.testing.unitTesting", "rvMenuPlaces")), 5000*10)

        // Check if the RecyclerView is displayed
        val recyclerView: UiObject = device.findObject(UiSelector().resourceId("com.testing.unitTesting:id/rvMenuPlaces"))
        assert(recyclerView.exists()) { "RecyclerView is not displayed" }

        // Check for user name in the TextView
        val userName: UiObject = device.findObject(UiSelector().resourceId("com.testing.unitTesting:id/tvMenuName"))
        assert(userName.exists()) { "User name TextView is not displayed" }
        assert(userName.text == "RICHARD") { "User name is incorrect" }

        // Check for user phone number in the TextView
        val userPhone: UiObject = device.findObject(UiSelector().resourceId("com.testing.unitTesting:id/tvMenuLocStatus"))
        assert(userPhone.exists()) { "User phone number TextView is not displayed" }
        assert(userPhone.text == "(251)-210-1267") { "Phone number is incorrect" }

        // Check for the user image
        val userImage: UiObject = device.findObject(UiSelector().resourceId("com.testing.unitTesting:id/imgMenuUserMapPin"))
        assert(userImage.exists()) { "User image is not displayed" }

        // Click on the first item in the RecyclerView (if applicable)
        val firstItem: UiObject = device.findObject(UiSelector().resourceId("com.testing.unitTesting:id/rvMenuPlaces").childSelector(UiSelector().index(0)))
        try {
            if (firstItem.exists()) {
                firstItem.click()
            }
        } catch (e: UiObjectNotFoundException) {
            e.printStackTrace()
        }

        // Verify that the expected action occurred (e.g., another fragment opened)
        val nextFragmentView: UiObject = device.findObject(UiSelector().text("Expected Title After Click"))
        assert(nextFragmentView.exists()) { "Expected view after clicking the item is not displayed" }
    }

    @Test
    fun testBackPressClosesFragment() {
        // Simulate a back press
        device.pressBack()

        // Verify that the UserMenu fragment is no longer displayed
        val recyclerView: UiObject = device.findObject(UiSelector().resourceId("com.testing.unitTesting:id/rvMenuPlaces"))
        assert(!recyclerView.exists()) { "RecyclerView should not be displayed after back press" }
    }
}