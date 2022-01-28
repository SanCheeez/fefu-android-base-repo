package ru.fefu.activitytracker.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import ru.fefu.activitytracker.DataBase.ActivityType
import ru.fefu.activitytracker.R
import ru.fefu.activitytracker.stores.MapActivityStore

class StartActivityOnMapFragment : Fragment() {
    private var position: Int = 0
    private val activityStore = MapActivityStore().getStore().toMutableList()

    companion object {
        fun newInstance(position: Int) = StartActivityOnMapFragment().apply {
            arguments = bundleOf("position" to position)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        position = arguments?.get("position") as Int
        return inflater.inflate(R.layout.fragment_start_activity_on_map, container, false)
    }

    override fun onStart() {
        super.onStart()
        val view = requireView()
        val type = view.findViewById<TextView>(R.id.map_activity_type)
        val record = view.findViewById<TextView>(R.id.map_activity_record)
        type.text = ActivityType.values()[activityStore[position].type].type
        record.text = activityStore[position].record
    }
}