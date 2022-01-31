package ru.fefu.activitytracker.fragments

import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import ru.fefu.activitytracker.*
import ru.fefu.activitytracker.DataBase.ActivityItemEntity
import ru.fefu.activitytracker.DataBase.ActivityType
import ru.fefu.activitytracker.DataBase.SerialiseClass
import ru.fefu.activitytracker.Items.ActivityItem
import ru.fefu.activitytracker.interfaces.FlowFragmentInterface
import ru.fefu.activitytracker.stores.MyActivityStore
import ru.fefu.activitytracker.stores.UsersActivityStore
import android.text.format.DateFormat
import java.util.*
import kotlin.math.min
import kotlin.math.roundToLong

class DetalisationFragment() : Fragment() {
    private var is_my: Boolean = false
    private var activityStore = mutableListOf<ActivityItem>()
    private var position: Int = 0
    lateinit var activity_item: ActivityItemEntity

    companion object {
        fun newInstance(position: Int, is_my: Boolean, activity_item: String?) =
            DetalisationFragment().apply {
                arguments = bundleOf(
                    "is_my" to is_my,
                    "position" to position,
                    "activity_item" to activity_item
                )
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        is_my = arguments?.get("is_my") as Boolean
        position = arguments?.get("position") as Int
        if (is_my) activity_item =
            SerialiseClass().itemDecode(arguments?.get("activity_item") as String)
        activityStore = if (is_my) MyActivityStore().getStore()
            .toMutableList() else UsersActivityStore().getStore().toMutableList()
        return inflater.inflate(R.layout.fragment_detalisation, container, false)
    }

    override fun onStart() {
        super.onStart()
        val view = requireView()
        val toolbar =
            view.findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar_detalisation)
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        if (is_my) toolbar.inflateMenu(R.menu.toolbar_detalisation_menu)

        val distance = view.findViewById<TextView>(R.id.distance_detalisation)
        val time = view.findViewById<TextView>(R.id.time_detalisation)
        val user = view.findViewById<TextView>(R.id.user_detalisation)
        val date = view.findViewById<TextView>(R.id.date_detalisation)
        val period = view.findViewById<TextView>(R.id.period_detalisation)
        if (!is_my) {
            toolbar.title = activityStore[position].type
            distance.text = activityStore[position].distance
            time.text = activityStore[position].time
            user.text = activityStore[position].user
            date.text = activityStore[position].date
            period.text = activityStore[position].period
        } else {
            toolbar.title = ActivityType.values()[activity_item.type!!].type
            distance.text = countDistance(SerialiseClass().listDecode(activity_item.coordinates!!))
            time.text = countPeriod(activity_item.date_finish!! - activity_item.date_start!!)
            user.text = null
            date.text = DateFormat.format("dd.MM.yyyy", Date(activity_item.date_start!!)).toString()
            period.text = ("Старт " + DateFormat.format(
                "hh.mm",
                Date(activity_item.date_start!!)
            ).toString() + " | Финиш " + DateFormat.format(
                "hh.mm",
                Date(activity_item.date_finish!!)
            ).toString())
        }

        toolbar.setNavigationOnClickListener {
            (parentFragment as FlowFragmentInterface).getFlowFragmentManager().popBackStack()
        }
    }

    private fun countDistance(list: List<Pair<Double, Double>>): String {
        var distance: Double = 0.0

        for (i in 0..list.size - 2) {
            val start_point = Location("locationA")
            start_point.latitude = list[i].first
            start_point.longitude = list[i].second

            val end_point = Location("locationA")
            end_point.latitude = list[i + 1].first
            end_point.longitude = list[i + 1].second

            distance = start_point.distanceTo(end_point).toDouble() / 1000
        }
        return distance.roundToLong().toString() + " км"
    }

    private fun countPeriod(period: Long): String {
        val calendar: Calendar = Calendar.getInstance()
        calendar.time = Date(period)
        var period_str: String = ""
        val hour = calendar.get(Calendar.HOUR)
        val minute = calendar.get(Calendar.MINUTE)
        if (hour > 0) period_str = period_str + calendar.get(Calendar.HOUR) + " час(ов)"
        if (minute > 0) period_str = period_str + calendar.get(Calendar.MINUTE) + " минут(а)"
        if (period_str == "") period_str = "Меньше минуты"
        return period_str
    }
}
