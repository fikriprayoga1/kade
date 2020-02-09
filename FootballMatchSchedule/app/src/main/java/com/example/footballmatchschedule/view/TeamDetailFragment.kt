package com.example.footballmatchschedule.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.footballmatchschedule.R
import com.example.footballmatchschedule.model.RetrofitResponse
import com.example.footballmatchschedule.model.apiresponse.Player
import com.example.footballmatchschedule.model.apiresponse.PlayerDetail
import com.example.footballmatchschedule.model.database.TeamDatabase
import com.example.footballmatchschedule.util.helper.ResponseListener
import com.example.footballmatchschedule.util.recyclerviewadapter.PlayerRecyclerViewAdapter
import com.example.footballmatchschedule.viewmodel.TeamDetailViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.event_detail_fragment.*
import kotlinx.android.synthetic.main.team_detail_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TeamDetailFragment : Fragment() {
    lateinit var playerAdapter: PlayerRecyclerViewAdapter

    companion object {
        fun newInstance() = TeamDetailFragment()
    }

    private lateinit var viewModel: TeamDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.team_detail_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(TeamDetailViewModel::class.java)

        lifecycleScope.launchWhenStarted {
            if (lifecycle.currentState >= Lifecycle.State.STARTED) {
                (activity as MainActivity).startLoading()

                withContext(Dispatchers.Default) {
                    viewModel.init(
                        (activity as MainActivity).viewModel.getUserRepository(),
                        (activity as MainActivity)
                    )

                }

                val dataSource = (activity as MainActivity).viewModel.getSelectedTeam()

                // favorite_icon
                withContext(Dispatchers.Default) {
                    if (viewModel.hasIdEvent(dataSource)) {
                        if (viewModel.isFavorite(dataSource)) {
                            withContext(Dispatchers.Main) {
                                imageButton_team_detail_fragment_1_1_1.setImageResource(
                                    R.drawable.ic_favorite_red_24dp
                                )
                            }

                        }

                    } else {
                        withContext(Dispatchers.Main) {
                            imageButton_event_detail_fragment_1_1_10.visibility = View.GONE
                        }
                    }

                }

                Picasso.get()
                    .load(dataSource.strTeamBadge)
                    .resize(100, 100)
                    .into(imageView_team_detail_fragment_1_1_2)
                textView_team_detail_fragment_1_1_3.text = dataSource.strTeam
                textView_team_detail_fragment_1_1_4.text = dataSource.intFormedYear
                textView_team_detail_fragment_1_1_5.text = dataSource.strStadium
                justifiedTextView_team_detail_fragment_1_2.text = dataSource.strDescriptionEN

                imageButton_team_detail_fragment_1_1_1.setOnClickListener {
                    favoriteClickListener(dataSource)

                }

                initRecyclerView()
                withContext(Dispatchers.IO) {
                    val teamName = dataSource.strTeam
                    if (teamName != null) {
                        viewModel.requestPlayerList(object :
                            ResponseListener {
                            override fun retrofitResponse(retrofitResponse: RetrofitResponse) {
                                playerListHandler(retrofitResponse)

                            }


                        }, teamName)

                    }

                }

            }

        }

    }

    private fun favoriteClickListener(teamDatabase: TeamDatabase) {
        lifecycleScope.launchWhenStarted {
            if (lifecycle.currentState >= Lifecycle.State.STARTED) {
                (activity as MainActivity).startLoading()

                withContext(Dispatchers.Default) {
                    if (viewModel.isFavorite(teamDatabase)) {
                        viewModel.setFavorite(false, teamDatabase)
                        withContext(Dispatchers.Main) {
                            imageButton_team_detail_fragment_1_1_1.setImageResource(
                                R.drawable.ic_favorite_border_red_24dp
                            )
                        }

                    } else {
                        viewModel.setFavorite(true, teamDatabase)
                        withContext(Dispatchers.Main) {
                            imageButton_team_detail_fragment_1_1_1.setImageResource(
                                R.drawable.ic_favorite_red_24dp
                            )
                        }

                    }

                }

                (activity as MainActivity).stopLoading()

            }

        }

    }

    private fun initRecyclerView() {
        playerAdapter = PlayerRecyclerViewAdapter(
            context!!,
            viewModel.getPlayerObjects(),
            object : PlayerRecyclerViewAdapter.PlayerListener {
                override fun itemDetail(playerDetail: PlayerDetail) {
                    selectedItemListener(playerDetail)

                }

            })
        val mLayoutManager = LinearLayoutManager(context)

        recyclerView_team_detail_fragment_1_3.layoutManager = mLayoutManager
        recyclerView_team_detail_fragment_1_3.itemAnimator = DefaultItemAnimator()
        recyclerView_team_detail_fragment_1_3.addItemDecoration(
            DividerItemDecoration(
                activity,
                LinearLayoutManager.VERTICAL
            )
        )
        recyclerView_team_detail_fragment_1_3.adapter = playerAdapter

    }

    private fun selectedItemListener(playerDetail: PlayerDetail) {
        lifecycleScope.launchWhenStarted {
            if (lifecycle.currentState >= Lifecycle.State.STARTED) {
                (activity as MainActivity).startLoading()

                withContext(Dispatchers.Default) {
                    viewModel.getMainActivity().viewModel.setSelectedPlayer(playerDetail)
                }

                viewModel.getMainActivity()
                    .changeFragment2(R.id.frameLayout_activity_main_1, PlayerDetailFragment())

                (activity as MainActivity).stopLoading()

            }

        }

    }

    private fun playerListHandler(retrofitResponse: RetrofitResponse) {
        lifecycleScope.launchWhenStarted {
            if (lifecycle.currentState >= Lifecycle.State.STARTED) {

                withContext(Dispatchers.Default) {
                    if (retrofitResponse.isSuccess) {
                        val PlayerData = retrofitResponse.responseBody as Player

                        viewModel.initPlayerList(PlayerData.player)
                        withContext(Dispatchers.Main) { playerAdapter.notifyDataSetChanged() }

                    } else {
                        withContext(Dispatchers.Main) {
                            viewModel.getMainActivity()
                                .popUp(retrofitResponse.message)
                        }
                    }

                }

                (activity as MainActivity).stopLoading()

            }

        }

    }

}
