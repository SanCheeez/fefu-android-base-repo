package ru.fefu.activitytracker.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.fefu.activitytracker.R
import ru.fefu.activitytracker.interfaces.FlowFragmentInterface

class ChangePasswordFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_change_password, container, false)
    }

    override fun onStart() {
        super.onStart()
        var view = requireView()
        val toolbar =
            view.findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar_change_password)
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        toolbar.title = getString(R.string.change_password)
        toolbar.setNavigationOnClickListener {
            (parentFragment as FlowFragmentInterface).getFlowFragmentManager().popBackStack()
        }
    }
}