package com.example.footballmatchschedule.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import com.example.footballmatchschedule.MainActivity
import com.example.footballmatchschedule.R
import com.example.footballmatchschedule.model.RetrofitResponse
import com.example.footballmatchschedule.model.apiresponse.League
import com.example.footballmatchschedule.model.apiresponse.SearchEvent
import com.example.footballmatchschedule.util.helper.FMSHelper
import com.example.footballmatchschedule.util.helper.ResponseListener
import com.example.footballmatchschedule.viewmodel.EventViewModel
import kotlinx.android.synthetic.main.event_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class EventFragment : Fragment() {

    companion object {
        fun newInstance() = EventFragment()
    }

    private lateinit var viewModel: EventViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.event_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(EventViewModel::class.java)

        lifecycleScope.launchWhenStarted {
            if (lifecycle.currentState >= Lifecycle.State.STARTED) {
                (activity as MainActivity).startLoading()

                withContext(Dispatchers.Default) {
                    viewModel.init(
                        FMSHelper.getUserRepository()
                    )

                }

                initListener()

                withContext(Dispatchers.IO) {
                    viewModel.requestLeagueList(object :
                        ResponseListener {
                        override fun retrofitResponse(retrofitResponse: RetrofitResponse) {
                            responseAction(retrofitResponse)

                        }


                    })

                }


                val adapter = viewModel.getAdapter(childFragmentManager)
                viewPager_event_fragment_2_3.adapter = adapter
                tabLayout_event_fragment_2_2.setupWithViewPager(viewPager_event_fragment_2_3)

            }

        }

    }

    private fun initListener() {
        spinner_event_fragment_2_1.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {}

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    spinnerListener(position)
                }


            }

        searchView_event_fragment_1.setOnQueryTextListener(object :
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                textSubmitListener(query)

                return true

            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }


        })

    }

    private fun responseAction(retrofitResponse: RetrofitResponse) {
        lifecycleScope.launchWhenStarted {
            if (lifecycle.currentState >= Lifecycle.State.STARTED) {
                withContext(Dispatchers.Default) {
                    if (retrofitResponse.isSuccess) {
                        val ab = retrofitResponse.responseBody as League
                        val aa = viewModel.addSpinner(ab.leagues, (activity as MainActivity))

                        withContext(Dispatchers.Main) { spinner_event_fragment_2_1.adapter = aa }

                    } else {
                        withContext(Dispatchers.Main) {
                            (activity as MainActivity).popUp(retrofitResponse.message)
                        }
                    }

                }

                (activity as MainActivity).stopLoading()

            }

        }

    }

    private fun spinnerListener(position: Int) {
        lifecycleScope.launchWhenStarted {
            if (lifecycle.currentState >= Lifecycle.State.STARTED) {
                FMSHelper.setLeagueIdHolder(
                    viewModel.getLeagueIdList(
                        position
                    )
                )

            }

        }

    }

    private fun textSubmitListener(query: String?) {
        lifecycleScope.launchWhenStarted {
            if (lifecycle.currentState >= Lifecycle.State.STARTED) {
                (activity as MainActivity).startLoading()

                searchView_event_fragment_1.clearFocus()
                (activity as MainActivity).exitKeyboard()
                viewModel.requestSearchEvent(query, object :
                    ResponseListener {
                    override fun retrofitResponse(retrofitResponse: RetrofitResponse) {
                        responseSearchListener(retrofitResponse)

                    }


                })

            }

        }

    }

    private fun responseSearchListener(retrofitResponse: RetrofitResponse) {
        lifecycleScope.launchWhenStarted {
            if (lifecycle.currentState >= Lifecycle.State.STARTED) {
                withContext(Dispatchers.Default) {
                    if (retrofitResponse.isSuccess) {
                        if (!FMSHelper.getHasFragmentBackstack(
                                "SearchEvent"
                            )
                        ) {
                            withContext(Dispatchers.Main) {
                                (activity as MainActivity).changeFragment2(
                                    R.id.constraintLayout_event_fragment_2,
                                    SearchEventFragment()
                                )

                                val searchTeam =
                                    retrofitResponse.responseBody as SearchEvent
                                FMSHelper.setEventList(searchTeam.event)
                            }
                            FMSHelper.setHasFragmentBackstack(
                                "SearchEvent",
                                true
                            )

                        }

                    } else {
                        withContext(Dispatchers.Main) {
                            (activity as MainActivity).popUp(retrofitResponse.message)
                        }
                    }

                }

                (activity as MainActivity).stopLoading()

            }

        }

    }

}
