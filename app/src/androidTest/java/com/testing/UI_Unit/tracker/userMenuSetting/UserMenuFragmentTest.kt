package com.testing.unitTesting.userMenuSetting

import android.content.Context
import android.content.SharedPreferences
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.testing.FragmentScenario
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.testing.unitTesting.DashBoard.Dashboard_Constants
import com.testing.unitTesting.Navigation_Drawer.TestDrawerActivity
import com.testing.unitTesting.Prefrences.Prefs_Operation
import com.testing.unitTesting.Prefrences.Prefs_OperationKotlin
import com.testing.unitTesting.R
import com.testing.unitTesting.RecylerViewClicked.RecyclerViewItemListener
import com.testing.unitTesting.TestDrawerActivity
import com.testing.unitTesting.UserMenuVM
import com.testing.unitTesting.login_Stuffs.Login_Contstant
import com.testing.unitTesting.tracker.userMenuSetting.UserMenu
import com.testing.unitTesting.tracker.userMenuSetting.adaptor.UserMenuAdaptor
import com.testing.unitTesting.tracker.userMenuSetting.viewModel.UserMenuVM
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever


@RunWith(AndroidJUnit4::class)
class UserMenuFragmentTest {
  //  @get:Rule
    //var activityRule = ActivityTestRule(TestDrawerActivity::class.java)
  @get:Rule
  var instantTaskExecutorRule = InstantTaskExecutorRule()
   // private lateinit var scenario: FragmentScenario<UserMenu>
    private lateinit var scenario: ActivityScenario<TestDrawerActivity>

    private lateinit var viewModel: UserMenuVM
    private lateinit var mockListener: RecyclerViewItemListener

    private lateinit var mockPrefs: SharedPreferences
    private lateinit var mockContext: Context
    @Before
    fun setUp() {

        // Initialize mockContext first
        mockContext = Mockito.mock(Context::class.java)
        mockPrefs = Mockito.mock(SharedPreferences::class.java)
        whenever(mockContext.getSharedPreferences(any(), any())).thenReturn(mockPrefs)
        // Set up the behavior of readPrefs
        whenever(mockPrefs.getString(Login_Contstant.CUSTOMER_NAME, "")).thenReturn("Test User")
        whenever(mockPrefs.getString(Dashboard_Constants.PHONE_NO, "")).thenReturn("1234567890")
        // Mock the GUEST_ENABLESWITCHLOGIN value
        whenever(mockPrefs.getString(Dashboard_Constants.GUEST_ENABLESWITCHLOGIN, "0")).thenReturn("0") // or "1" based on your test case

        // Initialize Prefs_OperationKotlin
        Prefs_OperationKotlin.init(mockContext)
        Prefs_Operation.init_SingleTon(mockContext)



        // Launch the TestDrawerActivity
        scenario = ActivityScenario.launch(TestDrawerActivity::class.java)

//        // Set up ActivityScenario with the TestDrawerActivity
//        ActivityScenario.launch(TestDrawerActivity::class.java).use { scenario ->
//            // Launch the UserMenu Fragment within the TestDrawerActivity
//            scenario.onActivity { activity ->
//                // Replace the fragment in the activity
//                activity.supportFragmentManager.beginTransaction()
//                    .replace(R.id.frameLay_Fragment, UserMenu())
//                    .commitNow()
//            }
            // Create a mock listener
            mockListener = mock()
            // Launch the UserMenu Fragment
            //scenario = FragmentScenario.launchInContainer(UserMenu::class.java)

            // Mock the ViewModel
            viewModel = UserMenuVM()
            setupViewModelData()

        // Set the mock ViewModel in the Fragment
        scenario.onActivity { activity ->
            val fragment = UserMenu()
            // Optionally, set the ViewModel here
            fragment.viewModel = viewModel
            activity.supportFragmentManager.beginTransaction()
                .replace(R.id.frameLay_Fragment, fragment)
                .commitNow()
        }
        }

    private fun setupViewModelData() {
        // Set up mock data for the ViewModel
        val menuList =mutableListOf("Manage Alerts", "Privacy & Friends", "jkh", "Contacts")
        // Create a MutableLiveData to return from the ViewModel
        val mockMenuListLiveData = MutableLiveData<MutableList<String>>()
        mockMenuListLiveData.value = menuList


        val menuImages = MutableLiveData(mutableListOf(
            R.drawable.bell_icon,
            R.drawable.lock_blackoutlined,
            R.drawable.support,
            R.drawable.support
        ))
      //  doReturn(mockMenuListLiveData).`when`(viewModel).getMenuList()
        // Simulate setting the data in the ViewModel
      //  whenever(viewModel.getMenuList()).thenReturn(mockMenuListLiveData)
       // whenever(viewModel.getMenuListImg()).thenReturn(menuImages)
    }

    @Test
    fun testUserMenuDisplaysCorrectData() {
        // Check if the menu text is displayed
        onView(withId(R.id.tvMenu)).check(matches(isDisplayed()))
        onView(withId(R.id.tvMenu)).check(matches(withText(R.string.menu)))

        viewModel.getUserName().observeForever { userName ->
            onView(withId(R.id.tvMenuName)).check(matches(withText(userName)))
        }
        // Trigger the loading of user name (if it’s not already done)
        viewModel.getData()  // Ensure this is called if not automatically triggered
        // Check if the phone number is displayed
        onView(withId(R.id.tvMenuLocStatus)).check(matches(isDisplayed()))
        viewModel.getPhoneNumber().observeForever { phoneNumber ->
            onView(withId(R.id.tvMenuLocStatus)).check(matches(withText(phoneNumber)))
        }
        // Check if the RecyclerView is displayed
        onView(withId(R.id.rvMenuPlaces)).check(matches(isDisplayed()))

        // Optionally, you can check if the RecyclerView has items
        // This requires a method to count items in the RecyclerView, which can be implemented
        // on the RecyclerView's adapter or using a custom matcher.

        // Simulate a click on the RecyclerView item (assuming there's at least one item)
        // Example: Assume you want to click the first item
        onView(withId(R.id.rvMenuPlaces)).perform(RecyclerViewActions.actionOnItemAtPosition<UserMenuAdaptor.MyViewHolder>(0, click()))


        // Verify if the navigation occurs
        // This might involve checking if a new fragment appears or if a specific method in the ViewModel is called.


        // Verify that the navigation event for Manage Alerts is triggered
        viewModel.getNavigateToManageAlert().observeForever { event ->
            if (event.getContentIfNotHandled() == true) {
                // Check that the navigation occurs as expected
                // This could involve checking the fragment stack or activity state
            }
        }

    }



    @Test
    fun testBackPressClosesFragment() {
        // Simulate pressing the back button
        onView(isRoot()).perform(ViewActions.pressBack())

        // Verify that the fragment is closed (you could check the previous fragment or activity state)
        // This might require custom logic depending on your navigation setup.
    }
}