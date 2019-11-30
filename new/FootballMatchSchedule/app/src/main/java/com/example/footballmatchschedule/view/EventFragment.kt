package com.example.footballmatchschedule.view

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.lifecycle.Observer

import com.example.footballmatchschedule.R
import com.example.footballmatchschedule.model.retrofitresponse.RequestLeague
import com.example.footballmatchschedule.viewmodel.EventViewModel
import kotlinx.android.synthetic.main.event_fragment.*
import kotlinx.coroutines.*

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

    }

    override fun onResume() {
        super.onResume()
        viewModel.init((activity as MainActivity).viewModel.getUserRepository())
        spinner_event_fragment_3_1.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
//                    (activity as MainActivity).viewModel.setLeagueIdHolder(viewModel.getLeagueIdList(position))

                }


            }

        val adapter = viewModel.getAdapter(childFragmentManager)
        viewPager_event_fragment_3_3.adapter = adapter
        tabLayout_event_fragment_3_2.setupWithViewPager(viewPager_event_fragment_3_3)

        viewModel.getLeagueList()?.observe(this, Observer {
            if (it != null) {

                viewModel.getUIScope().launch {
                    val aa = withContext(Dispatchers.Default) {
                        viewModel.addSpinner(it, context!!)

                    }

                    spinner_event_fragment_3_1.adapter = aa

                }


            }

        })

        if (!viewModel.hasCache()) {
            (activity as MainActivity).startLoading(null)
            viewModel.requestLeagueList((activity as MainActivity), object :
                com.example.footballmatchschedule.other.callback.RequestLeagueCallback {
                override fun requestLeagueList(
                    mainActivity: MainActivity,
                    requestLeague: RequestLeague
                ) {
                    mainActivity.stopLoading(null)
                    if (!requestLeague.isSuccess) {
                        mainActivity.popUp(requestLeague.message)

                    }

                }


            })

        }

    }

    override fun onPause() {
        viewModel.getJob().cancel()
        super.onPause()
    }

}
