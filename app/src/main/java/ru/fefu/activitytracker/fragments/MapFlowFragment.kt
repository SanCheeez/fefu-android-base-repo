package ru.fefu.activitytracker.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import ru.fefu.activitytracker.R
import ru.fefu.activitytracker.interfaces.FlowFragmentInterface

class MapFlowFragment : Fragment(), FlowFragmentInterface {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_map_flow, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) childFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container_map, ChooseActivityOnMapFragment())
            commit()
        }
    }

    override fun getFlowFragmentManager(): FragmentManager = childFragmentManager
}