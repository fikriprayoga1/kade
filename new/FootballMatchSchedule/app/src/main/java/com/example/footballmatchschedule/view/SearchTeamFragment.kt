package com.example.footballmatchschedule.view

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.footballmatchschedule.R
import com.example.footballmatchschedule.viewmodel.SearchTeamViewModel

class SearchTeamFragment : Fragment() {

    companion object {
        fun newInstance() = SearchTeamFragment()
    }

    private lateinit var viewModel: SearchTeamViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.search_team_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(SearchTeamViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onDestroy() {
        (activity as MainActivity).viewModel.setHasFragmentBackstack("SearchTeam", false)
        super.onDestroy()
    }

}
