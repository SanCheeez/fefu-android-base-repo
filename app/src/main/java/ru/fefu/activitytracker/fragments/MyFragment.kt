package ru.fefu.activitytracker.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.fefu.activitytracker.stores.MyActivityStore
import ru.fefu.activitytracker.R
import ru.fefu.activitytracker.adapters.RecyclerAdapter
import ru.fefu.activitytracker.interfaces.FlowFragmentInterface

class MyFragment : Fragment() {
    private val activityStore = MyActivityStore()
    private val recyclerAdapter = RecyclerAdapter(activityStore.getStore(), true)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        recyclerAdapter.setItemClickListener {
            var fragmentManager = (parentFragment as FlowFragmentInterface).getFlowFragmentManager()
            fragmentManager.beginTransaction().apply {
                replace(R.id.container, DetalisationFragment.newInstance(it, true))
                addToBackStack(null)
                commit()
            }
        }
        return inflater.inflate(R.layout.fragment_tab_my, container, false)
    }

    override fun onStart() {
        super.onStart()
        val recyclerView = requireView().findViewById<RecyclerView>(R.id.rv_date_activity)
        recyclerView.adapter = recyclerAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }
}