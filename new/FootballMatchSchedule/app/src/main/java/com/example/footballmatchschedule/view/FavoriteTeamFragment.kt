package com.example.footballmatchschedule.view

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.footballmatchschedule.R
import com.example.footballmatchschedule.viewmodel.FavoriteTeamViewModel

class FavoriteTeamFragment : Fragment() {

    companion object {
        fun newInstance() = FavoriteTeamFragment()
    }

    private lateinit var viewModel: FavoriteTeamViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.favorite_team_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(FavoriteTeamViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
