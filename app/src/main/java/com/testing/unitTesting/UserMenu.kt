package com.testing.unitTesting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.testing.unitTesting.LogCalls.LogCalls_Debug
import com.testing.unitTesting.Navigation_Drawer.Drawer
import com.testing.unitTesting.R
import com.testing.unitTesting.RecylerViewClicked.RecyclerViewItemListener
import com.testing.unitTesting.databinding.TrackerUserMenuBinding
import com.testing.unitTesting.tracker.alertSetting.ManageAlerts
import com.testing.unitTesting.tracker.userMenuSetting.adaptor.UserMenuAdaptor
import com.testing.unitTesting.tracker.userMenuSetting.friendSettings.FriendListSettings
import com.testing.unitTesting.tracker.userMenuSetting.viewModel.UserMenuVM

class UserMenu : Fragment(), RecyclerViewItemListener {


    private lateinit var adaptor: UserMenuAdaptor
    private val TAG = UserMenu::class.simpleName

    var binding: TrackerUserMenuBinding? = null
    var mView: View? = null

    lateinit var viewModel: UserMenuVM


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.tracker_user_menu, container, false)

        mView = binding!!.root

        setupViewModel()



        requireActivity().onBackPressedDispatcher.addCallback(
            requireActivity(),
            onBackPressedCallback
        )


        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObservers()
        initRecyclerView()

    }

    private fun initRecyclerView() {
        LogCalls_Debug.d(TAG, "initRecyclerView")

        adaptor = UserMenuAdaptor(requireActivity(), viewModel.getMenuList(),viewModel.getMenuListImg(), this)
        binding!!.rvMenuPlaces.adapter = adaptor
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this)[UserMenuVM::class.java]
        ///////////////////////////////////////////////////

        binding!!.lifecycleOwner = this
        binding!!.viewModel = viewModel
    }

    private fun setupObservers() {

        viewModel.getNavigateToFriendSetting().observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { // Only proceed if the event has never been handled
                (activity as Drawer?)!!.getFragment(FriendListSettings(), -1)
            }
        }

        viewModel.getNavigateToManageAlert().observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { // Only proceed if the event has never been handled
                (activity as Drawer?)!!.getFragment(ManageAlerts(), -1)
            }
        }
    }


    override fun onItemClick(get: HashMap<String, Any>, pos: Int) {}
    override fun onItemClickObject(id: Int, any: Any, pos: Int) {

        //  viewModel.onNavigateAddGeofence("0")
        viewModel.onNavigateToNextFrag(pos)

    }


    ////////////// When on Back button pressed fragment navigate to prevoius fragment
    private val onBackPressedCallback: OnBackPressedCallback =
        object : OnBackPressedCallback(true /* Enabled by default */) {
            override fun handleOnBackPressed() {

                // Handle the back button event
                LogCalls_Debug.d(
                    LogCalls_Debug.TAG + UserMenu::class.java.name,
                    "handleOnBackPressed false"
                )
                closeFragment()

            }
        }

    private fun closeFragment() {

        requireActivity().supportFragmentManager.popBackStack()

    }
}


