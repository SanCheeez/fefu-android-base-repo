package ru.fefu.activitytracker.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import ru.fefu.activitytracker.DataBase.ActivityType
import ru.fefu.activitytracker.Items.ActivityItem
import ru.fefu.activitytracker.Items.ActivityMapItem
import ru.fefu.activitytracker.R

class RecyclerMapAdapter(activities: List<ActivityMapItem>, context_: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var context = context_
    private var itemClickListener: (Int) -> Unit = {}
    private val mutable_activities = activities.toMutableList()

    inner class RecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val type: TextView = itemView.findViewById(R.id.type_activity_for_map)

        init {
            itemView.setOnClickListener() {
                val position = absoluteAdapterPosition
                itemClickListener.invoke(position)
            }
        }

        fun bind(activity_item_map: ActivityMapItem) {
            type.text = ActivityType.values()[activity_item_map.type].type

            itemView.background = if (activity_item_map.isSelected) ContextCompat.getDrawable(
                context,
                R.drawable.border_type
            )
            else ContextCompat.getDrawable(context, R.color.white)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val veiw =
            LayoutInflater.from(parent.context).inflate(R.layout.item_activity_map, parent, false)
        return RecyclerViewHolder(veiw)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as RecyclerViewHolder).bind(mutable_activities[position])
    }

    override fun getItemCount(): Int = mutable_activities.size

    fun setItemClickListener(listener: (Int) -> Unit) {
        itemClickListener = listener
    }

    fun ChangeSelection(new_position: Int, prev_position: Int) {
        if (prev_position != -1) {
            mutable_activities[prev_position].isSelected = false
            notifyItemChanged(prev_position)
        }
        if (new_position != -1) {
            mutable_activities[new_position].isSelected = true
            notifyItemChanged(new_position)
        }

    }

    fun GetSelectedItem(): Int {
        for (i in mutable_activities.indices)
            if (mutable_activities[i].isSelected)
                return i
        return 0
    }
}