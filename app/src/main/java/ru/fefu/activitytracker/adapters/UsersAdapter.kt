package ru.fefu.activitytracker.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.fefu.activitytracker.Items.ActivityItem
import ru.fefu.activitytracker.R

class UsersAdapter(activities: List<ActivityItem>, is_my_: Boolean) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class RecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val distance: TextView = itemView.findViewById(R.id.activity_last_date)
        private val time: TextView = itemView.findViewById(R.id.activity_time)
        private val type: TextView = itemView.findViewById(R.id.activity_type)
        private val user: TextView = itemView.findViewById(R.id.activity_user)
        private val date: TextView = itemView.findViewById(R.id.activity_date)

        init {
            itemView.setOnClickListener() {
                val position = absoluteAdapterPosition
                itemClickListener.invoke(position)
            }
        }

        fun bind(activity_item: ActivityItem) {
            distance.text = activity_item.distance
            time.text = activity_item.time
            type.text = activity_item.type
            user.text = activity_item.user
            date.text = activity_item.date
        }
    }

    inner class RecyclerViewHolderForDate(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val time: TextView = itemView.findViewById(R.id.activity_last_date)
        fun bind(activity_item: ActivityItem) {
            time.text = activity_item.time
        }
    }

    private var itemClickListener: (Int) -> Unit = {}
    private val is_my = is_my_
    private val mutable_activities = activities.toMutableList()

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
            (holder as RecyclerViewHolderForDate).bind(mutable_activities[position])
        } else {
            (holder as RecyclerViewHolder).bind(mutable_activities[position])
        }
    }

    override fun getItemCount(): Int = mutable_activities.size

    override fun getItemViewType(position: Int): Int {
        if (position == 0)
            return 1
        else
            return 0
    }

    fun setItemClickListener(listener: (Int) -> Unit) {
        itemClickListener = listener
    }
}