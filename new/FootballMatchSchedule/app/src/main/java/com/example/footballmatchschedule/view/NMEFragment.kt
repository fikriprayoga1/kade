package com.example.footballmatchschedule.view

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.footballmatchschedule.R
import com.example.footballmatchschedule.viewmodel.NMEViewModel

class NMEFragment : Fragment() {

    companion object {
        fun newInstance() = NMEFragment()
    }

    private lateinit var viewModel: NMEViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.next_match_event_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(NMEViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
