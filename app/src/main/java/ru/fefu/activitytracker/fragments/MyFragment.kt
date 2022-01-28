package ru.fefu.activitytracker.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.fefu.activitytracker.DataBase.*
import ru.fefu.activitytracker.R
import ru.fefu.activitytracker.adapters.RecyclerAdapter
import ru.fefu.activitytracker.interfaces.FlowFragmentInterface
import java.util.*

class MyFragment : Fragment() {
    private var activityStore: MutableList<DBActivityItem> = mutableListOf()
    private var recyclerAdapter = RecyclerAdapter(activityStore)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tab_my, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        App.INSTANCE.db.activityDao().getAll().observe(viewLifecycleOwner) {
            activityStore.clear()
            var prev_date: Long? = null
            for (activity in it) {
                val item = Activity(
                    id = activity.id,
                    type = activity.type,
                    date_start = activity.date_start,
                    date_finish = activity.date_finish,
                    coordinates = activity.coordinates,
                )
                if (writeDate(activity.date_start?.let { it1 -> Date(it1) }) ==
                    writeDate(prev_date?.let { it1 -> Date(it1) })
                ) {
                    activityStore.add(item)
                } else {
                    activityStore.add(
                        Date_(
                            date = writeDate(activity.date_start?.let { it1 -> Date(it1) })
                        )
                    )
                    activityStore.add(item)
                    prev_date = activity.date_start
                }
            }
            recyclerAdapter.submitList(activityStore)
        }
        val recyclerView = requireView().findViewById<RecyclerView>(R.id.rv_date_activity)
        recyclerView.adapter = recyclerAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerAdapter.setItemClickListener {
            var fragment_manager =
                (parentFragment as FlowFragmentInterface).getFlowFragmentManager()
            fragment_manager.beginTransaction().apply {
                replace(
                    R.id.container,
                    DetalisationFragment.newInstance(
                        it,
                        true,
                        SerialiseClass().itemEncode(activityStore[it] as Activity)
                    )
                )
                addToBackStack(null)
                commit()
            }
        }
    }

    private fun writeDate(date: Date?): String? {
        val months = listOf(
            "Январь",
            "Февраль",
            "Март",
            "Апрель",
            "Май",
            "Июнь",
            "Июль",
            "Август",
            "Сентябрь",
            "Октябрь",
            "Ноябрь",
            "Декабрь"
        )
        val calendar: Calendar = Calendar.getInstance()
        if (date == null)
            return null
        calendar.time = date;
        return months[calendar.get(Calendar.MONTH)] + " " + calendar.get(Calendar.YEAR) + " года"
    }
}