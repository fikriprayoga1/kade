package com.example.footballmatchschedule.view

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.footballmatchschedule.R
import com.example.footballmatchschedule.model.RetrofitResponse
import com.example.footballmatchschedule.model.apiresponse.NME
import com.example.footballmatchschedule.model.apiresponse.NMEDetail
import com.example.footballmatchschedule.other.ResponseListener
import com.example.footballmatchschedule.other.recyclerviewadapter.NMERecyclerViewAdapter
import com.example.footballmatchschedule.viewmodel.NMEViewModel
import kotlinx.android.synthetic.main.next_match_event_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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

        viewModel.getUIScope().launch(Dispatchers.Default) {
            viewModel.init(
                (activity as MainActivity).viewModel.getUserRepository(),
                (activity as MainActivity)
            )

        }

        initRecyclerView()

        (activity as MainActivity).viewModel.getLeagueIdHolderListener()
            .observe(this, Observer {
                showList()

            })

    }

    override fun onDestroy() {
        viewModel.getJob().cancel()
        super.onDestroy()
    }

    private fun initRecyclerView() {
        nmeAdapter = NMERecyclerViewAdapter(
            context!!,
            viewModel.getNMEObjects(),
            object : NMERecyclerViewAdapter.NMEListener {
                override fun itemDetail(nmeDetail: NMEDetail) {

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

    private fun showList() {
        viewModel.getUIScope().launch(Dispatchers.Default) {
            (activity as MainActivity).startLoading(viewModel.getUIScope())
            val leagueHolder = (activity as MainActivity).viewModel.getLeagueIdHolder()!!
            viewModel.requestNMEList(leagueHolder, object : ResponseListener {
                override fun retrofitResponse(retrofitResponse: RetrofitResponse) {
                    viewModel.getUIScope().launch {
                        withContext(Dispatchers.Default) {
                            viewModel.getMainActivity().stopLoading(viewModel.getUIScope())
                            if (retrofitResponse.isSuccess) {
                                val NMEData = retrofitResponse.responseBody as NME
                                val NMEList = NMEData.events

                                if (NMEList != null) {
                                    viewModel.initNMEList(NMEList)

                                } else {
                                    viewModel.getMainActivity().popUp(retrofitResponse.message, viewModel.getUIScope())
                                }

                            } else { viewModel.getMainActivity().popUp(retrofitResponse.message, viewModel.getUIScope()) }

                        }

                        nmeAdapter.notifyDataSetChanged()

                    }
                }
            })

        }

    }

}
