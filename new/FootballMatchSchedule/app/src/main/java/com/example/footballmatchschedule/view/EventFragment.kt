package com.example.footballmatchschedule.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.footballmatchschedule.R
import com.example.footballmatchschedule.model.RetrofitResponse
import com.example.footballmatchschedule.model.apiresponse.League
import com.example.footballmatchschedule.model.apiresponse.SearchEvent
import com.example.footballmatchschedule.model.apiresponse.SearchTeam
import com.example.footballmatchschedule.other.ResponseListener
import com.example.footballmatchschedule.viewmodel.EventViewModel
import kotlinx.android.synthetic.main.event_fragment.*
import kotlinx.android.synthetic.main.event_fragment.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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

        viewModel.getUIScope().launch(Dispatchers.Default) {
            viewModel.init(
                (activity as MainActivity).viewModel.getUserRepository(),
                (activity as MainActivity)
            )

            spinner_event_fragment_2_1.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onNothingSelected(parent: AdapterView<*>?) {}

                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {

                        (activity as MainActivity).viewModel.setLeagueIdHolder(
                            viewModel.getLeagueIdList(
                                position
                            )
                        )

                    }


                }

            viewModel.requestLeagueList(object : ResponseListener {
                override fun retrofitResponse(retrofitResponse: RetrofitResponse) {
                    viewModel.getUIScope().launch {
                        if (retrofitResponse.isSuccess) {
                            val aa = withContext(Dispatchers.Default) {
                                val ab = retrofitResponse.responseBody as League
                                viewModel.addSpinner(ab.leagues!!, viewModel.getMainActivity())

                            }

                            spinner_event_fragment_2_1.adapter = aa

                        } else {
                            viewModel.getMainActivity()
                                .popUp(retrofitResponse.message, viewModel.getUIScope())
                        }

                    }

                }


            })

            searchView_event_fragment_1.setOnQueryTextFocusChangeListener { v, hasFocus ->

            }

            searchView_event_fragment_1.setOnQueryTextListener(object :
                android.widget.SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    searchView_event_fragment_1.clearFocus()
                    viewModel.getUIScope().launch(Dispatchers.Default) {
                        viewModel.getMainActivity().exitKeyboard(viewModel.getUIScope())
                        viewModel.getMainActivity().startLoading(viewModel.getUIScope())
                        viewModel.requestSearchEvent(query, object : ResponseListener {
                            override fun retrofitResponse(retrofitResponse: RetrofitResponse) {
                                viewModel.getUIScope().launch(Dispatchers.Default) {
                                    viewModel.getMainActivity().stopLoading(viewModel.getUIScope())
                                    if (retrofitResponse.isSuccess) {
                                        if (!viewModel.getMainActivity().viewModel.getHasFragmentBackstack("SearchEvent")) {
                                            viewModel.getMainActivity().changeFragment2(
                                                R.id.constraintLayout_event_fragment_2,
                                                SearchEventFragment(),
                                                viewModel.getUIScope()
                                            )
                                            viewModel.getMainActivity().viewModel.setHasFragmentBackstack("SearchEvent", true)

                                        }

                                        val searchTeam = retrofitResponse.responseBody as SearchEvent
                                        withContext(Dispatchers.Main) {
                                            viewModel.getMainActivity().viewModel.setSearchEventList(
                                                searchTeam.event
                                            )
                                        }


                                    } else {
                                        viewModel.getMainActivity()
                                            .popUp(retrofitResponse.message, viewModel.getUIScope())
                                    }

                                }

                            }


                        })

                    }

                    return true

                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return true
                }


            })

        }

        val adapter = viewModel.getAdapter(childFragmentManager)
        viewPager_event_fragment_2_3.adapter = adapter
        tabLayout_event_fragment_2_2.setupWithViewPager(viewPager_event_fragment_2_3)

    }

    override fun onDestroy() {
        viewModel.getJob().cancel()
        super.onDestroy()
    }

}
