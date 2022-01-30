package ru.fefu.activitytracker.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toolbar
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import ru.fefu.activitytracker.*

class DetalisationFragment() : Fragment() {
    private var is_my: Boolean = false
    private var activityStore = mutableListOf<ActivityItem>()
    private var position: Int = 0

    companion object {
        fun newInstance(position: Int, is_my: Boolean) = DetalisationFragment().apply {
            arguments = bundleOf("is_my" to is_my, "position" to position)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        is_my = arguments?.get("is_my") as Boolean
        position = arguments?.get("position") as Int
        activityStore = if (is_my) MyActivityStore().getStore()
            .toMutableList() else UsersActivityStore().getStore().toMutableList()
        return inflater.inflate(R.layout.fragment_detalisation, container, false)
    }

    override fun onStart() {
        super.onStart()
        val view = requireView()
        val toolbar = view.findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        toolbar.title = activityStore[position].type
        if (is_my) toolbar.inflateMenu(R.menu.toolbar_detalisation_menu)

        val distance = view.findViewById<TextView>(R.id.distance_detalisation)
        val time = view.findViewById<TextView>(R.id.time_detalisation)
        val user = view.findViewById<TextView>(R.id.user_detalisation)
        val date = view.findViewById<TextView>(R.id.date_detalisation)
        val period = view.findViewById<TextView>(R.id.period_detalisation)
        distance.text = activityStore[position].distance
        time.text = activityStore[position].time
        user.text = activityStore[position].user
        date.text = activityStore[position].date
        period.text = activityStore[position].period

        toolbar.setNavigationOnClickListener {
            (parentFragment as FlowFragmentInterface).getFlowFragmentManager().popBackStack()
        }
    }
}
