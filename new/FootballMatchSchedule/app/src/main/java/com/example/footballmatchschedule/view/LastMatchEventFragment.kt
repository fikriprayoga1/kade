package com.example.footballmatchschedule.view

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.footballmatchschedule.R
import com.example.footballmatchschedule.viewmodel.LastMatchEventViewModel

class LastMatchEventFragment : Fragment() {

    companion object {
        fun newInstance() = LastMatchEventFragment()
    }

    private lateinit var viewModel: LastMatchEventViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.last_match_event_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(LastMatchEventViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
