package com.example.footballmatchschedule.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.footballmatchschedule.R
import com.example.footballmatchschedule.viewmodel.SearchEventViewModel

class SearchEventFragment : Fragment() {

    companion object {
        fun newInstance() = SearchEventFragment()
    }

    private lateinit var viewModel: SearchEventViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.search_event_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(SearchEventViewModel::class.java)

    }

    override fun onDestroy() {
        (activity as MainActivity).viewModel.setHasFragmentBackstack("SearchEvent", false)
        super.onDestroy()
    }

}
