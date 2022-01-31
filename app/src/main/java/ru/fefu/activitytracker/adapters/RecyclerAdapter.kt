package ru.fefu.activitytracker.adapters

import android.location.Location
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.fefu.activitytracker.Items.ActivityItem
import ru.fefu.activitytracker.R
import android.text.format.DateFormat
import ru.fefu.activitytracker.DataBase.*
import java.util.*
import kotlin.math.roundToLong

class RecyclerAdapter(activities: List<DBActivityItem>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    inner class RecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val distance: TextView = itemView.findViewById(R.id.activity_last_date)
        private val time: TextView = itemView.findViewById(R.id.activity_time)
        private val type: TextView = itemView.findViewById(R.id.activity_type)
        private val date: TextView = itemView.findViewById(R.id.activity_date)

        init {
            itemView.setOnClickListener() {
                val position = absoluteAdapterPosition
                itemClickListener.invoke(position)
            }
        }

        fun bind(activity_item: Activity) {
            distance.text = countDistance(SerialiseClass().listDecode(activity_item.coordinates!!))
            time.text = countPeriod(activity_item.date_finish!! - activity_item.date_start!!)
            type.text = ActivityType.values()[activity_item.type!!].type
            date.text = DateFormat.format("dd.MM.yyyy", Date(activity_item.date_start)).toString()
        }
    }

    inner class RecyclerViewHolderForDate(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val time: TextView = itemView.findViewById(R.id.activity_last_date)
        fun bind(activity_item: Date_) {
            time.text = activity_item.date
        }
    }

    private var itemClickListener: (Int) -> Unit = {}
    private var mutable_activities = activities.toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == 0) {
            val veiw = LayoutInflater.from(parent.context)
                .inflate(R.layout.number_activity_list, parent, false)
            return RecyclerViewHolder(veiw)
        }
        val veiw = LayoutInflater.from(parent.context)
            .inflate(R.layout.number_activity_list_last_date, parent, false)
        return RecyclerViewHolderForDate(veiw)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (getItemViewType(position) == 1) {
            (holder as RecyclerViewHolderForDate).bind(mutable_activities[position] as Date_)
        } else {
            (holder as RecyclerViewHolder).bind(mutable_activities[position] as Activity)
        }
    }

    override fun getItemCount(): Int = mutable_activities.size

    override fun getItemViewType(position: Int): Int {
        return when (mutable_activities[position]) {
            is Activity -> 0
            is Date_ -> 1
        }
    }

    fun setItemClickListener(listener: (Int) -> Unit) {
        itemClickListener = listener
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
        var period_str: String = ""
        val calendar: Calendar = Calendar.getInstance()
        calendar.time = Date(period)
        val hour = calendar.get(Calendar.HOUR)
        val minute = calendar.get(Calendar.MINUTE)
        if (hour > 0)
            period_str = period_str + calendar.get(Calendar.HOUR) + " час(ов)"
        if (minute > 0)
            period_str = period_str + calendar.get(Calendar.MINUTE) + " минут(а)"
        if (period_str == "") period_str = "Меньше минуты"
        return period_str
    }

    fun submitList(list: MutableList<DBActivityItem>) {
        mutable_activities = list
        notifyDataSetChanged()
    }
}