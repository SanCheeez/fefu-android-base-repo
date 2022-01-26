package ru.fefu.activitytracker.adapters

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import ru.fefu.activitytracker.fragments.ActivityFragment
import ru.fefu.activitytracker.fragments.MyFragment
import ru.fefu.activitytracker.fragments.UsersFragment

class ActivityAdapter(fragment: ActivityFragment) :
    FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                MyFragment()
            }
            1 -> {
                UsersFragment()
            }
            else -> {
                Fragment()
            }
        }
    }

}