package ru.fefu.activitytracker.fragments

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import ru.fefu.activitytracker.R
import ru.fefu.activitytracker.adapters.RecyclerMapAdapter
import ru.fefu.activitytracker.interfaces.FlowFragmentInterface
import ru.fefu.activitytracker.stores.MapActivityStore

class ChooseActivityOnMapFragment : Fragment() {
    private val activityStore = MapActivityStore()
    private var chosen_item = 0
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_choose_activity_on_map, container, false)
    }

    override fun onStart() {
        super.onStart()
        val recyclerAdapter = RecyclerMapAdapter(activityStore.getStore(), requireActivity())
        val view = requireView()
        chosen_item = recyclerAdapter.GetSelectedItem()
        recyclerAdapter.ChangeSelection(chosen_item, -1)
        val recyclerView = view.findViewById<RecyclerView>(R.id.activity_list_map)
        val startButton = view.findViewById<MaterialButton>(R.id.start_map_activity)
        recyclerView.adapter = recyclerAdapter
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        startButton.setOnClickListener {
            val fragment_manager =
                (parentFragment as FlowFragmentInterface).getFlowFragmentManager()
            fragment_manager.beginTransaction().apply {
                replace(
                    R.id.fragment_container_map,
                    StartActivityOnMapFragment.newInstance(chosen_item)
                )
                addToBackStack(null)
                commit()
            }
        }
        recyclerAdapter.setItemClickListener {
            recyclerAdapter.ChangeSelection(it, chosen_item)
            chosen_item = it
        }
    }
}