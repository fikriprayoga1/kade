package com.example.footballmatchschedule.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import com.example.footballmatchschedule.MainActivity
import com.example.footballmatchschedule.R
import com.example.footballmatchschedule.model.RetrofitResponse
import com.example.footballmatchschedule.model.apiresponse.Team
import com.example.footballmatchschedule.model.database.EventDatabase
import com.example.footballmatchschedule.util.helper.FMSHelper
import com.example.footballmatchschedule.util.helper.ResponseListener
import com.example.footballmatchschedule.viewmodel.EventDetailViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.event_detail_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class EventDetailFragment : Fragment() {

    companion object {
        fun newInstance() = EventDetailFragment()
    }

    private lateinit var viewModel: EventDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.event_detail_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(EventDetailViewModel::class.java)

        lifecycleScope.launchWhenStarted {
            if (lifecycle.currentState >= Lifecycle.State.STARTED) {
                (activity as MainActivity).startLoading()

                withContext(Dispatchers.Default) {
                    viewModel.init(
                        FMSHelper.getUserRepository()
                    )

                }

                val dataSource = FMSHelper.getSelectedEvent()

                // alarm_icon
                withContext(Dispatchers.Default) {
                    if (viewModel.isNextDateAndHasIdEvent(dataSource)) {
                        if (viewModel.isAlarm(dataSource)) {
                            withContext(Dispatchers.Main) {
                                imageButton_event_detail_fragment_1_1_2.setImageResource(R.drawable.ic_notifications_accent_24dp)

                            }

                        }


                    } else {
                        withContext(Dispatchers.Main) {
                            imageButton_event_detail_fragment_1_1_2.visibility = View.GONE
                        }

                    }

                }

                // favorite_icon
                withContext(Dispatchers.Default) {
                    if (viewModel.hasIdEvent(dataSource)) {
                        if (viewModel.isFavorite(dataSource)) {
                            withContext(Dispatchers.Main) {
                                imageButton_event_detail_fragment_1_1_10.setImageResource(
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

                textView_event_detail_fragment_1_1_1.text = withContext(Dispatchers.Default) {
                    viewModel.getDate(dataSource.dateEvent)

                }
                withContext(Dispatchers.IO) {
                    viewModel.requestTeamLogo(object :
                        ResponseListener {
                        override fun retrofitResponse(retrofitResponse: RetrofitResponse) {
                            imageResponseHandler(retrofitResponse, true)

                        }

                    }, dataSource.idHomeTeam)
                    viewModel.requestTeamLogo(object :
                        ResponseListener {
                        override fun retrofitResponse(retrofitResponse: RetrofitResponse) {
                            imageResponseHandler(retrofitResponse, false)

                        }

                    }, dataSource.idAwayTeam)

                }
                textView_event_detail_fragment_1_1_5.text = dataSource.intHomeScore
                textView_event_detail_fragment_1_1_7.text = dataSource.intAwayScore
                textView_event_detail_fragment_1_1_4.text = dataSource.strHomeTeam
                textView_event_detail_fragment_1_1_9.text = dataSource.strAwayTeam
                textView_event_detail_fragment_1_3.text = dataSource.intHomeShots
                textView_event_detail_fragment_1_5.text = dataSource.intAwayShots
                textView_fragment_event_detail_1_6_2.text =
                    withContext(Dispatchers.Default) { viewModel.getNameList1(dataSource.strHomeGoalDetails) }
                textView_fragment_event_detail_1_6_4.text =
                    withContext(Dispatchers.Default) { viewModel.getNameList1(dataSource.strAwayGoalDetails) }
                textView_fragment_event_detail_1_7_2.text =
                    withContext(Dispatchers.Default) { viewModel.getGoalKeeperName(dataSource.strHomeLineupGoalkeeper) }
                textView_fragment_event_detail_1_7_4.text =
                    withContext(Dispatchers.Default) { viewModel.getGoalKeeperName(dataSource.strAwayLineupGoalkeeper) }
                textView_fragment_event_detail_1_8_2.text =
                    withContext(Dispatchers.Default) { viewModel.getNameList2(dataSource.strHomeLineupDefense) }
                textView_fragment_event_detail_1_8_4.text =
                    withContext(Dispatchers.Default) { viewModel.getNameList2(dataSource.strAwayLineupDefense) }
                textView_fragment_event_detail_1_9_2.text =
                    withContext(Dispatchers.Default) { viewModel.getNameList2(dataSource.strHomeLineupMidfield) }
                textView_fragment_event_detail_1_9_4.text =
                    withContext(Dispatchers.Default) { viewModel.getNameList2(dataSource.strAwayLineupMidfield) }
                textView_fragment_event_detail_1_10_2.text =
                    withContext(Dispatchers.Default) { viewModel.getNameList2(dataSource.strHomeLineupForward) }
                textView_fragment_event_detail_1_10_4.text =
                    withContext(Dispatchers.Default) { viewModel.getNameList2(dataSource.strAwayLineupForward) }
                textView_fragment_event_detail_1_11_2.text =
                    withContext(Dispatchers.Default) { viewModel.getNameList2(dataSource.strHomeLineupSubstitutes) }
                textView_fragment_event_detail_1_11_4.text =
                    withContext(Dispatchers.Default) { viewModel.getNameList2(dataSource.strAwayLineupSubstitutes) }

                // alarm_button
                imageButton_event_detail_fragment_1_1_2.setOnClickListener {
                    alarmClickListener(dataSource)

                }

                // favorite_button
                imageButton_event_detail_fragment_1_1_10.setOnClickListener {
                    favoriteClickListener(dataSource)

                }

                (activity as MainActivity).stopLoading()

            }

        }

    }

    private fun imageResponseHandler(retrofitResponse: RetrofitResponse, isHome: Boolean) {
        lifecycleScope.launchWhenStarted {
            if (lifecycle.currentState >= Lifecycle.State.STARTED) {
                (activity as MainActivity).startLoading()

                withContext(Dispatchers.Default) {
                    val team = retrofitResponse.responseBody as Team
                    val teamDetailDataList = team.teams
                    if (teamDetailDataList != null) {
                        val imageLink = teamDetailDataList[0].strTeamBadge
                        if (imageLink != null) {
                            val imageView: ImageView = if (isHome) {
                                imageView_event_detail_fragment_1_1_3
                            } else {
                                imageView_event_detail_fragment_1_1_8

                            }
                            withContext(Dispatchers.Main) {
                                Picasso.get()
                                    .load(imageLink)
                                    .resize(100, 100)
                                    .into(imageView)
                            }

                        }

                    }

                }

                (activity as MainActivity).stopLoading()

            }

        }

    }

    private fun alarmClickListener(eventDatabase: EventDatabase) {
        lifecycleScope.launchWhenStarted {
            if (lifecycle.currentState >= Lifecycle.State.STARTED) {
                (activity as MainActivity).startLoading()

                withContext(Dispatchers.Default) {
                    if (viewModel.isNextDate(eventDatabase)) {
                        if (viewModel.isAlarm(eventDatabase)) {

                            viewModel.setAlarm(false, eventDatabase, context!!)
                            withContext(Dispatchers.Main) {
                                imageButton_event_detail_fragment_1_1_2.setImageResource(
                                    R.drawable.ic_notifications_none_accent_24dp
                                )
                            }

                        } else {
                            viewModel.setAlarm(true, eventDatabase, context!!)
                            withContext(Dispatchers.Main) {
                                imageButton_event_detail_fragment_1_1_2.setImageResource(
                                    R.drawable.ic_notifications_accent_24dp
                                )
                            }

                        }

                    } else {
                        (activity as MainActivity).popUp("Event date is expired")
                    }

                }


                (activity as MainActivity).stopLoading()

            }

        }

    }

    private fun favoriteClickListener(eventDatabase: EventDatabase) {
        lifecycleScope.launchWhenStarted {
            if (lifecycle.currentState >= Lifecycle.State.STARTED) {
                (activity as MainActivity).startLoading()

                withContext(Dispatchers.Default) {
                    if (viewModel.isFavorite(eventDatabase)) {
                        viewModel.setFavorite(false, eventDatabase)
                        withContext(Dispatchers.Main) {
                            imageButton_event_detail_fragment_1_1_10.setImageResource(
                                R.drawable.ic_favorite_border_red_24dp
                            )
                        }

                    } else {
                        viewModel.setFavorite(true, eventDatabase)
                        withContext(Dispatchers.Main) {
                            imageButton_event_detail_fragment_1_1_10.setImageResource(
                                R.drawable.ic_favorite_red_24dp
                            )
                        }

                    }

                }

                (activity as MainActivity).stopLoading()

            }

        }

    }

}
