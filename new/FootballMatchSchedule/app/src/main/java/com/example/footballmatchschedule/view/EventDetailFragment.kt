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
import com.example.footballmatchschedule.R
import com.example.footballmatchschedule.model.RetrofitResponse
import com.example.footballmatchschedule.model.apiresponse.EventDetail
import com.example.footballmatchschedule.model.apiresponse.TeamDetail
import com.example.footballmatchschedule.other.ResponseListener
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
                val loadingStatus0 =
                    withContext(Dispatchers.Default) {
                        (activity as MainActivity).viewModel.updateLoading(
                            true
                        )
                    }
                (activity as MainActivity).updateLoading(
                    loadingStatus0,
                    this.javaClass.name,
                    Thread.currentThread().stackTrace[2].lineNumber,
                    "start"
                )
                withContext(Dispatchers.Default) {
                    viewModel.init(
                        (activity as MainActivity).viewModel.getUserRepository(),
                        (activity as MainActivity)
                    )

                }

                val dataSource = (activity as MainActivity).viewModel.getSelectedEvent()

                textView_event_detail_fragment_1_1_1.text =
                    withContext(Dispatchers.Default) { viewModel.getDate(dataSource.dateEvent) }
                viewModel.requestTeamLogo(object : ResponseListener {
                    override fun retrofitResponse(retrofitResponse: RetrofitResponse) {
                        imageResponseHandler(retrofitResponse, true)

                    }

                }, dataSource.idHomeTeam)
                viewModel.requestTeamLogo(object : ResponseListener {
                    override fun retrofitResponse(retrofitResponse: RetrofitResponse) {
                        imageResponseHandler(retrofitResponse, false)

                    }

                }, dataSource.idAwayTeam)
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

                withContext(Dispatchers.Default) {
                    if (viewModel.isAlarm(dataSource.idEvent)) {
                        withContext(Dispatchers.Main) {
                            imageButton_event_detail_fragment_1_1_2.setImageResource(R.drawable.ic_notifications_accent_24dp)

                        }

                    }

                }

                withContext(Dispatchers.Default) {
                    if (viewModel.isFavorite(dataSource.idEvent)) {
                        withContext(Dispatchers.Main) {
                            imageButton_event_detail_fragment_1_1_10.setImageResource(
                                R.drawable.ic_favorite_red_24dp
                            )
                        }

                    }

                }

                imageButton_event_detail_fragment_1_1_2.setOnClickListener {
                    alarmClickListener(dataSource)

                }

                imageButton_event_detail_fragment_1_1_10.setOnClickListener {
                    favoriteClickListener(dataSource)

                }

                val loadingStatus1 = withContext(Dispatchers.Default) {
                    (activity as MainActivity).viewModel.updateLoading(false)
                }
                (activity as MainActivity).updateLoading(
                    loadingStatus1,
                    this.javaClass.name,
                    Thread.currentThread().stackTrace[2].lineNumber,
                    "stop"
                )

            }

        }

    }

    private fun imageResponseHandler(retrofitResponse: RetrofitResponse, isHome: Boolean) {
        lifecycleScope.launchWhenStarted {
            if (lifecycle.currentState >= Lifecycle.State.STARTED) {
                val loadingStatus0 =
                    withContext(Dispatchers.Default) {
                        (activity as MainActivity).viewModel.updateLoading(
                            true
                        )
                    }
                (activity as MainActivity).updateLoading(
                    loadingStatus0,
                    this.javaClass.name,
                    Thread.currentThread().stackTrace[2].lineNumber,
                    "start"
                )

                withContext(Dispatchers.Default) {
                    val teamDetailData = retrofitResponse.responseBody as TeamDetail
                    val imageLink = teamDetailData.strTeamBadge
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

                val loadingStatus1 = withContext(Dispatchers.Default) {
                    (activity as MainActivity).viewModel.updateLoading(false)
                }
                (activity as MainActivity).updateLoading(
                    loadingStatus1,
                    this.javaClass.name,
                    Thread.currentThread().stackTrace[2].lineNumber,
                    "stop"
                )

            }

        }

    }

    private fun alarmClickListener(eventDetail: EventDetail) {
        lifecycleScope.launchWhenStarted {
            if (lifecycle.currentState >= Lifecycle.State.STARTED) {
                val loadingStatus0 =
                    withContext(Dispatchers.Default) {
                        (activity as MainActivity).viewModel.updateLoading(
                            true
                        )
                    }
                (activity as MainActivity).updateLoading(
                    loadingStatus0,
                    this.javaClass.name,
                    Thread.currentThread().stackTrace[2].lineNumber,
                    "start"
                )

                withContext(Dispatchers.Default) {
                    if (viewModel.isAlarm(eventDetail.idEvent)) {
                        viewModel.setAlarm(false, eventDetail)
                        withContext(Dispatchers.Main) {
                            imageButton_event_detail_fragment_1_1_2.setImageResource(
                                R.drawable.ic_notifications_none_accent_24dp
                            )
                        }

                    } else {
                        viewModel.setAlarm(true, eventDetail)
                        withContext(Dispatchers.Main) {
                            imageButton_event_detail_fragment_1_1_2.setImageResource(
                                R.drawable.ic_notifications_accent_24dp
                            )
                        }

                    }

                }


                val loadingStatus1 = withContext(Dispatchers.Default) {
                    (activity as MainActivity).viewModel.updateLoading(false)
                }
                (activity as MainActivity).updateLoading(
                    loadingStatus1,
                    this.javaClass.name,
                    Thread.currentThread().stackTrace[2].lineNumber,
                    "stop"
                )

            }

        }

    }

    private fun favoriteClickListener(eventDetail: EventDetail) {
        lifecycleScope.launchWhenStarted {
            if (lifecycle.currentState >= Lifecycle.State.STARTED) {
                val loadingStatus0 =
                    withContext(Dispatchers.Default) {
                        (activity as MainActivity).viewModel.updateLoading(
                            true
                        )
                    }
                (activity as MainActivity).updateLoading(
                    loadingStatus0,
                    this.javaClass.name,
                    Thread.currentThread().stackTrace[2].lineNumber,
                    "start"
                )

                withContext(Dispatchers.Default) {
                    if (viewModel.isFavorite(eventDetail.idEvent)) {
                        viewModel.setFavorite(false, eventDetail)
                        withContext(Dispatchers.Main) {
                            imageButton_event_detail_fragment_1_1_10.setImageResource(
                                R.drawable.ic_favorite_border_red_24dp
                            )
                        }

                    } else {
                        viewModel.setFavorite(true, eventDetail)
                        withContext(Dispatchers.Main) {
                            imageButton_event_detail_fragment_1_1_10.setImageResource(
                                R.drawable.ic_favorite_red_24dp
                            )
                        }

                    }

                }


                val loadingStatus1 = withContext(Dispatchers.Default) {
                    (activity as MainActivity).viewModel.updateLoading(false)
                }
                (activity as MainActivity).updateLoading(
                    loadingStatus1,
                    this.javaClass.name,
                    Thread.currentThread().stackTrace[2].lineNumber,
                    "stop"
                )

            }

        }

    }

}
