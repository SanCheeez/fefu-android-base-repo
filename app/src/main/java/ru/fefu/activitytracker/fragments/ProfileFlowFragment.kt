package ru.fefu.activitytracker.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.fefu.activitytracker.R
import ru.fefu.activitytracker.interfaces.FlowFragmentInterface

class ProfileFlowFragment : Fragment(), FlowFragmentInterface {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val profile =
            requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav_view).menu.findItem(
                R.id.navigation_profile
            )
        profile.isChecked = true
        return inflater.inflate(R.layout.fragment_flow_profile, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) childFragmentManager.beginTransaction().apply {
            replace(R.id.container, ProfileEnterFragment())
            commit()
        }
    }

    override fun onHiddenChanged(hidden: Boolean) {
        if (hidden) return
        val profile_tab =
            requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav_view).menu.findItem(
                R.id.navigation_profile
            )
        if (!profile_tab.isChecked) profile_tab.isChecked = true
    }

    override fun getFlowFragmentManager(): FragmentManager = childFragmentManager
}