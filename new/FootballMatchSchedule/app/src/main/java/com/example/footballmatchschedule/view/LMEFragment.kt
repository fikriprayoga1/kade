package com.example.footballmatchschedule.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.footballmatchschedule.R
import com.example.footballmatchschedule.model.RetrofitResponse
import com.example.footballmatchschedule.model.apiresponse.LME
import com.example.footballmatchschedule.model.apiresponse.LMEDetail
import com.example.footballmatchschedule.other.ResponseListener
import com.example.footballmatchschedule.other.recyclerviewadapter.LMERecyclerViewAdapter
import com.example.footballmatchschedule.viewmodel.LMEViewModel
import kotlinx.android.synthetic.main.last_match_event_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LMEFragment : Fragment() {
    lateinit var lmeAdapter: LMERecyclerViewAdapter

    companion object {
        fun newInstance() = LMEFragment()
    }

    private lateinit var viewModel: LMEViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.last_match_event_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(LMEViewModel::class.java)

        viewModel.init(
            (activity as MainActivity).viewModel.getUserRepository(),
            (activity as MainActivity)
        )

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
        lmeAdapter = LMERecyclerViewAdapter(
            context!!,
            viewModel.getLMEObjects(),
            object : LMERecyclerViewAdapter.LMEListener {
                override fun itemDetail(lmeDetail: LMEDetail) {

                }


            })
        val mLayoutManager = LinearLayoutManager(context)

        recyclerView_last_match_event_fragment_1.layoutManager = mLayoutManager
        recyclerView_last_match_event_fragment_1.itemAnimator = DefaultItemAnimator()
        recyclerView_last_match_event_fragment_1.addItemDecoration(
            DividerItemDecoration(
                activity,
                LinearLayoutManager.VERTICAL
            )
        )
        recyclerView_last_match_event_fragment_1.adapter = lmeAdapter

    }

    private fun showList() {
        viewModel.getUIScope().launch {
            (activity as MainActivity).startLoading(viewModel.getUIScope())

            withContext(Dispatchers.Default) {
                val leagueHolder = (activity as MainActivity).viewModel.getLeagueIdHolder()!!
                viewModel.requestLMEList(leagueHolder, object : ResponseListener {
                    override fun retrofitResponse(retrofitResponse: RetrofitResponse) {
                        viewModel.getUIScope().launch {
                            viewModel.getMainActivity().stopLoading(viewModel.getUIScope())
                            if (retrofitResponse.isSuccess) {
                                val LMEData = retrofitResponse.responseBody as LME
                                val LMEList = LMEData.events

                                if (LMEList != null) {
                                    withContext(Dispatchers.Default) { viewModel.initLMEList(LMEList) }

                                } else {
                                    viewModel.getMainActivity().popUp(retrofitResponse.message)
                                }

                            } else {
                                viewModel.getMainActivity().popUp(retrofitResponse.message)
                            }

                            lmeAdapter.notifyDataSetChanged()

                        }
                    }
                })

            }

        }

    }

}
