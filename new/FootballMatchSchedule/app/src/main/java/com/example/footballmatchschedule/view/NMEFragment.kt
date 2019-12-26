package com.example.footballmatchschedule.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.footballmatchschedule.R
import com.example.footballmatchschedule.model.RetrofitResponse
import com.example.footballmatchschedule.model.apiresponse.EventDetail
import com.example.footballmatchschedule.model.apiresponse.NME
import com.example.footballmatchschedule.other.ResponseListener
import com.example.footballmatchschedule.other.recyclerviewadapter.NMERecyclerViewAdapter
import com.example.footballmatchschedule.viewmodel.NMEViewModel
import kotlinx.android.synthetic.main.next_match_event_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NMEFragment : Fragment() {
    lateinit var nmeAdapter: NMERecyclerViewAdapter

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

        val thisContext = this
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
                initRecyclerView()
                (activity as MainActivity).viewModel.getLeagueIdHolderListener()
                    .observe(thisContext, Observer {
                        leagueIdHolderListener()

                    })

                val loadingStatus1 =
                    withContext(Dispatchers.Default) {
                        (activity as MainActivity).viewModel.updateLoading(
                            false
                        )
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

    private fun initRecyclerView() {
        nmeAdapter = NMERecyclerViewAdapter(
            context!!,
            viewModel.getNMEObjects(),
            object : NMERecyclerViewAdapter.NMEListener {
                override fun itemDetail(eventDetail: EventDetail) {
                    selectedItemListener(eventDetail)

                }


            })
        val mLayoutManager = LinearLayoutManager(context)

        recyclerView_next_match_event_fragment_1.layoutManager = mLayoutManager
        recyclerView_next_match_event_fragment_1.itemAnimator = DefaultItemAnimator()
        recyclerView_next_match_event_fragment_1.addItemDecoration(
            DividerItemDecoration(
                activity,
                LinearLayoutManager.VERTICAL
            )
        )
        recyclerView_next_match_event_fragment_1.adapter = nmeAdapter

    }

    private fun leagueIdHolderListener() {
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

                withContext(Dispatchers.IO) {
                    val leagueHolder =
                        (activity as MainActivity).viewModel.getLeagueIdHolder()!!
                    viewModel.requestNMEList(
                        object : ResponseListener {
                            override fun retrofitResponse(retrofitResponse: RetrofitResponse) {
                                responseNMEListener(retrofitResponse)
                            }
                        }, leagueHolder
                    )

                }

            }

        }

    }

    private fun responseNMEListener(retrofitResponse: RetrofitResponse) {
        lifecycleScope.launchWhenStarted {
            if (lifecycle.currentState >= Lifecycle.State.STARTED) {
                withContext(Dispatchers.Default) {
                    if (retrofitResponse.isSuccess) {
                        val NMEData =
                            retrofitResponse.responseBody as NME

                        viewModel.initEventList(NMEData.events)
                        withContext(Dispatchers.Main) {
                            nmeAdapter.notifyDataSetChanged()

                        }

                    } else {
                        withContext(Dispatchers.Main) {
                            viewModel.getMainActivity()
                                .popUp(retrofitResponse.message)

                        }
                    }

                }

                val loadingStatus1 =
                    withContext(Dispatchers.Default) {
                        (activity as MainActivity).viewModel.updateLoading(
                            false
                        )
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

    private fun selectedItemListener(eventDetail: EventDetail) {
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
                    viewModel.getMainActivity().viewModel.setSelectedEvent(eventDetail)
                }

                viewModel.getMainActivity()
                    .changeFragment2(R.id.frameLayout_activity_main_1, EventDetailFragment())

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
