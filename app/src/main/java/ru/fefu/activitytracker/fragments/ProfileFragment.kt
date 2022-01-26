package ru.fefu.activitytracker.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.fefu.activitytracker.R

class ProfileFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onHiddenChanged(hidden: Boolean) {
        if (hidden) return
        val profile_tab =
            requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav_view).menu.findItem(
                R.id.navigation_profile
            )
        if (!profile_tab.isChecked) profile_tab.isChecked = true
    }
}