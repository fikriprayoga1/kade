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
import com.example.footballmatchschedule.model.database.LMEDatabase
import com.example.footballmatchschedule.other.recyclerviewadapter.LMERecyclerViewAdapter
import com.example.footballmatchschedule.viewmodel.LMEViewModel
import kotlinx.android.synthetic.main.last_match_event_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LMEFragment : Fragment() {
    lateinit var lmeAdapter: LMERecyclerViewAdapter
    private var lmeObjects: MutableList<LMERecyclerViewAdapter.LMEObject> = ArrayList()

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

    }

    override fun onResume() {
        super.onResume()
        viewModel.init((activity as MainActivity).viewModel.getUserRepository())
        showList()

        (activity as MainActivity).viewModel.getLeagueIdHolder().observe(this, Observer {
//            viewModel.getUIScope().launch {
//                lmeObjects = withContext(Dispatchers.Default) { viewModel.addList(it) }
//                lmeAdapter.notifyDataSetChanged()
//
//            }

        })

        viewModel.getLMEList()?.observe(this, Observer {
            if (it != null) {
                val leagueIdHolder = (activity as MainActivity).viewModel.getLeagueIdHolder2()
                if (leagueIdHolder != null) {
//                    viewModel.getUIScope().launch {
//                        lmeObjects = withContext(Dispatchers.Default) { viewModel.addList2(leagueIdHolder, it) }
//                        lmeAdapter.notifyDataSetChanged()
//                    }

                }


            }

        })

        if (!viewModel.hasCache()) { (activity as MainActivity).startLoading(null) }


    }

    override fun onPause() {
        viewModel.getJob().cancel()
        super.onPause()
    }

    private fun showList() {
        lmeAdapter = LMERecyclerViewAdapter(
            context!!,
            lmeObjects, object : LMERecyclerViewAdapter.LMEListener {
            override fun itemDetail(lmeDatabase: LMEDatabase) {

            }
        })
        val mLayoutManager = LinearLayoutManager(context)
        recyclerView_last_match_event_fragment_1.layoutManager = mLayoutManager
        recyclerView_last_match_event_fragment_1.itemAnimator = DefaultItemAnimator()
        recyclerView_last_match_event_fragment_1.addItemDecoration(DividerItemDecoration(activity, LinearLayoutManager.VERTICAL))
        recyclerView_last_match_event_fragment_1.adapter = lmeAdapter

    }

}
