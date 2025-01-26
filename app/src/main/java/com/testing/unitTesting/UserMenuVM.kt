package com.testing.unitTesting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.testing.unitTesting.DashBoard.Dashboard_Constants
import com.testing.unitTesting.Prefrences.Prefs_Operation
import com.testing.unitTesting.Prefrences.Prefs_OperationKotlin
import com.testing.unitTesting.R
import com.testing.unitTesting.login_Stuffs.Login_Contstant
import com.testing.unitTesting.mvvmData.networkApi.ResultApi
import com.testing.unitTesting.shoppingBossMVVM.EventLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class UserMenuVM : ViewModel() {

    //boolean navigation for one time action perform
    private val _navigateFriendSetting = MutableLiveData<EventLiveData<Boolean>>()
    private val _navigateManageAlert = MutableLiveData<EventLiveData<Boolean>>()

    // private var listMenu: MutableList<String> = ArrayList()
    // private var listMenuImg: MutableList<Int> = ArrayList()
    var jk=MutableLiveData<MutableList<Int>>()

    // private val _listMenu = MutableLiveData<MutableList<String>>()
//    private val _listMenu = MutableLiveData<MutableList<String>>().apply {
//        value = mutableListOf() // Initialize with an empty list
//    }
//    private val _listMenuImg = MutableLiveData<MutableList<Int>>().apply {
//        value = mutableListOf() // Initialize with an empty list
//    }
    private val _listMenu = MutableLiveData<MutableList<String>>(mutableListOf())
    private val _listMenuImg = MutableLiveData<MutableList<Int>>(mutableListOf())

    // private val _listMenuImg = MutableLiveData<MutableList<Int>>()

    private var customerImage = MutableLiveData("http//:")
    private var userName = MutableLiveData("")
    private var userPhoneNo = MutableLiveData("")

    val commentState = MutableStateFlow(ResultApi.loading(null))

    init {
        getData()


    }

    fun getData() {
        // Launch a coroutine to load the user name
        //viewModelScope.launch(Dispatchers.Main) {
        getCustomerData()
        createMenuList()
        //  }
    }

    //get customer lat,lng and image which is saved in dashboard response
    fun getCustomerData() {
        userName.value = Prefs_OperationKotlin.readString(Login_Contstant.CUSTOMER_NAME, "")

        customerImage.value = Prefs_OperationKotlin.readString(Dashboard_Constants.CUSTOMER_IMAGE, "http//:")
        userPhoneNo.value = Prefs_OperationKotlin.readString(Dashboard_Constants.PHONE_NO, "")

    }

    fun createMenuList() {

        //name list
        _listMenu.value =
            mutableListOf(
                "Manage Alerts",
                "Privacy & Friends",
                "",
                "Contacts"

            )

        //array of icon
        _listMenuImg.value = ArrayList(
            mutableListOf(
                R.drawable.bell_icon,
                R.drawable.lock_blackoutlined,
                R.drawable.support,
                R.drawable.support
            )
        )
        // jk.value=listMenuImg

    }

    fun getMenuList(): LiveData<MutableList<String>> {
        return _listMenu
    }
    fun getMenuListImg():LiveData<MutableList<Int>> {
        return _listMenuImg
    }
    fun getCustomerImage(): LiveData<String> {
        return customerImage
    }

    fun getUserName(): LiveData<String> {
        return userName
    }

    fun getPhoneNumber(): LiveData<String> {
        return userPhoneNo
    }

    //observe for AddRemoveFav  response
    //Live data observing in fragment. Purpose not to expose mutable (changeable) data in fragments
    fun getNavigateToFriendSetting(): LiveData<EventLiveData<Boolean>> {
        return _navigateFriendSetting
    }


    //observe for AddRemoveFav  response
    //Live data observing in fragment. Purpose not to expose mutable (changeable) data in fragments
    fun getNavigateToManageAlert(): LiveData<EventLiveData<Boolean>> {
        return _navigateManageAlert
    }

    fun onNavigateToNextFrag(pos: Int) {
        when (pos) {
            0 -> {
                _navigateManageAlert.value = EventLiveData(true)
            }

            1 -> {
                _navigateFriendSetting.value = EventLiveData(true)
            }
        }
    }

}

