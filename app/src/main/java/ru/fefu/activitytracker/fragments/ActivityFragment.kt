package ru.fefu.activitytracker.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import ru.fefu.activitytracker.adapters.ActivityAdapter
import ru.fefu.activitytracker.R

class ActivityFragment : Fragment(), FlowFragmentInterface {
    private lateinit var activity_TabLayout: TabLayout
    private lateinit var activity_ViewPager2: ViewPager2
    private lateinit var activity_Adapter: ActivityAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_activity, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity_Adapter = ActivityAdapter(this)
        activity_ViewPager2 = view.findViewById(R.id.viewPager2)
        activity_ViewPager2.adapter = activity_Adapter
        activity_TabLayout = view.findViewById(R.id.tabLayout)

        TabLayoutMediator(activity_TabLayout, activity_ViewPager2) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = "Моя"
                }
                1 -> {
                    tab.text = "Пользователей"
                }
            }
        }.attach()
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (hidden) return
        val activity_tab =
            requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav_view).menu.findItem(
                R.id.navigation_activity
            )
        if (!activity_tab.isChecked) activity_tab.isChecked = true
    }

    public fun setupNavigation() {
        findNavController().navigate(R.id.action_activityFragment_to_profileFragment)
    }

    override fun getFlowFragmentManager(): FragmentManager =
        (parentFragment as FlowFragmentInterface).getFlowFragmentManager()
}