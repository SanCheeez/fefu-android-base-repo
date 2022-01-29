package ru.fefu.activitytracker.fragments

import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.osmdroid.util.GeoPoint
import ru.fefu.activitytracker.DataBase.ActivityItemEntity
import ru.fefu.activitytracker.DataBase.ActivityType
import ru.fefu.activitytracker.DataBase.App
import ru.fefu.activitytracker.DataBase.SerialiseClass
import ru.fefu.activitytracker.R
import ru.fefu.activitytracker.activities.BottomNavActivity
import ru.fefu.activitytracker.activities.MapActivity
import ru.fefu.activitytracker.services.RecordLocationService
import kotlin.math.roundToLong

class StartActivityOnMapFragment : Fragment() {
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
        return inflater.inflate(R.layout.fragment_start_activity_on_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val view_ = requireView()
        val type = view_.findViewById<TextView>(R.id.map_activity_type)
        val record = view_.findViewById<TextView>(R.id.map_activity_record)

        App.INSTANCE.db.activityDao().getLastInTime().observe(viewLifecycleOwner) {
            type.text = ActivityType.values()[it.type!!].type
            if (it.coordinates != null) {
                var coordinates = it.coordinates.let { SerialiseClass().listDecode(it) }
                record.text = countDistance(coordinates)
                (requireActivity() as MapActivity).polyline.addPoint(
                    GeoPoint(
                        coordinates.last().first,
                        coordinates.last().second
                    )
                )
            } else {
                record.text = "0"
            }

            val finish_button =
                view_.findViewById<FloatingActionButton>(R.id.button_finish_activity)
            finish_button.setOnClickListener {
                var activity = requireActivity()
                var intent = Intent(activity, RecordLocationService::class.java).apply {
                    action = RecordLocationService.ACTION_CANCEL
                }
                activity.startService(intent)
                intent = Intent(activity, BottomNavActivity::class.java)
                activity.startActivity(intent)
            }
        }
    }

    private fun countDistance(list: List<Pair<Double, Double>>): String {
        var distance = 0.0

        for (i in 0..list.size - 2) {
            val start_point = Location("locationA")
            start_point.latitude = list[i].first
            start_point.longitude = list[i].second

            val end_point = Location("locationA")
            end_point.latitude = list[i + 1].first
            end_point.longitude = list[i + 1].second

            distance += start_point.distanceTo(end_point).toDouble() / 1000
        }
        return distance.roundToLong().toString() + " км"
    }
}