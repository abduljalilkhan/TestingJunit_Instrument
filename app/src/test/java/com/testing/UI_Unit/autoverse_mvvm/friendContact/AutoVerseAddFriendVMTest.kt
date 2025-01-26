package com.testing.unitTesting.autoverse_mvvm.friendContact

import android.content.Context
import android.content.SharedPreferences
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.testing.unitTesting.AutoVerseAddFriendVM
import com.testing.unitTesting.Prefrences.Prefs_Operation
import com.testing.unitTesting.Prefrences.Prefs_OperationKotlin
import com.testing.unitTesting.autoverse_mvvm.autoVerseApi.AutoVerseApi
import com.testing.unitTesting.autoverse_mvvm.autoVerseApi.AutoVerseRepo
import com.testing.unitTesting.autoverse_mvvm.autoVerseApi.AutoVerseRepoImpl
import com.testing.unitTesting.mvvmData.networkApi.ResultApi
import com.testing.unitTesting.tracker.addFriendContactList.dataModel.InviteURLResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever
import org.robolectric.annotation.Config
import retrofit2.Response

@ExperimentalCoroutinesApi
@Config(sdk = [34]) // Specify the Android SDK version
class AutoVerseAddFriendVMTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = TestCoroutineDispatcher()
    private lateinit var mockApi: AutoVerseApi
    private lateinit var mockRepo: AutoVerseRepo
    private lateinit var viewModel: AutoVerseAddFriendVM
    private lateinit var mockPrefs: SharedPreferences
    private lateinit var mockContext: Context

    @Before
    fun setUp() {
        // Initialize mockContext first
        mockContext = mock(Context::class.java)
        mockPrefs = mock(SharedPreferences::class.java)
        whenever(mockContext.getSharedPreferences(any(), any())).thenReturn(mockPrefs)

        // Initialize Prefs_OperationKotlin
        Prefs_OperationKotlin.init(mockContext)
        Prefs_Operation.init_SingleTon(mockContext)

        // Initialize your API and ViewModel
        mockApi = mock(AutoVerseApi::class.java)
        mockRepo = AutoVerseRepo(mockApi)
        val mockRepoImpl = AutoVerseRepoImpl(mockRepo)
        viewModel = AutoVerseAddFriendVM(mockRepoImpl)

        // Initialize Firebase using a dummy activity
//        val activity = Robolectric.buildActivity(DummyActivity::class.java).create().get()
//        FirebaseApp.initializeApp(activity)

        Dispatchers.setMain(testDispatcher)
    }
    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `test initial LiveData values`() {
        assertEquals(false, viewModel.isRecyclerViewShow.value)
        assertEquals("", viewModel.strInviteURl.value)
    }

    @Test
    fun `test setting contact permission`() {
        viewModel.setContactPermissionGranted()
        assertNotNull(viewModel.getContactPermissionGranted().value)
        assertTrue(viewModel.getContactPermissionGranted().value?.getContentIfNotHandled() == true)
    }

    @Test
    fun `test invite link API call`() = testDispatcher.runBlockingTest {
        val mockResponseBody = InviteURLResponse("mockInviteLink", 1, "Success")
        val mockResponse: Response<InviteURLResponse> = Response.success(mockResponseBody)

        whenever(mockApi.inviteLinkResponse(any())).thenReturn(mockResponse)

        viewModel.onInviteLinkApi("1234567890", "John Doe")

        advanceUntilIdle()  // Ensure all coroutines complete

        val inviteLinkResponse = viewModel.getInviteLinkResponse().value
        assertNotNull(inviteLinkResponse)  // Ensure it's not null

        assertEquals(ResultApi.StatusApi.SUCCESS, inviteLinkResponse?.statusApi)

        // Validate the data
        assertEquals(mockResponseBody, inviteLinkResponse?.data)
    }

    @Test
    fun `test sharing link`() {
        viewModel.setShareLink()
        assertNotNull(viewModel.getShareLink().value)
        assertTrue(viewModel.getShareLink().value?.getContentIfNotHandled() == true)
    }

    @Test
    fun `test WhatsApp share link`() {
        viewModel.setWhatsAppShare()
        assertNotNull(viewModel.getWhatsAppShare().value)
        assertTrue(viewModel.getWhatsAppShare().value?.getContentIfNotHandled() == true)
    }

    // Additional tests for Instagram sharing and other functionalities can be added here
}