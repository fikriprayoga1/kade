package com.example.footballmatchschedule.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.footballmatchschedule.R
import com.example.footballmatchschedule.model.RetrofitResponse
import com.example.footballmatchschedule.model.apiresponse.League
import com.example.footballmatchschedule.other.ResponseListener
import com.example.footballmatchschedule.viewmodel.EventViewModel
import kotlinx.android.synthetic.main.event_fragment.*
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

    }

    override fun onResume() {
        super.onResume()
        viewModel.init(
            (activity as MainActivity).viewModel.getUserRepository(),
            (activity as MainActivity)
        )
        spinner_event_fragment_3_1.onItemSelectedListener =
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

        val adapter = viewModel.getAdapter(childFragmentManager)
        viewPager_event_fragment_3_3.adapter = adapter
        tabLayout_event_fragment_3_2.setupWithViewPager(viewPager_event_fragment_3_3)

        viewModel.requestLeagueList(object : ResponseListener {
            override fun retrofitResponse(retrofitResponse: RetrofitResponse) {
                viewModel.getUIScope().launch {
                    if (retrofitResponse.isSuccess) {
                        val aa = withContext(Dispatchers.Default) {
                            val ab = retrofitResponse.responseBody as League
                            viewModel.addSpinner(ab.leagues!!, viewModel.getMainActivity())

                        }

                        spinner_event_fragment_3_1.adapter = aa

                    } else {
                        viewModel.getMainActivity().popUp(retrofitResponse.message)
                    }

                }

            }


        })


    }

    override fun onPause() {
        viewModel.getJob().cancel()
        super.onPause()
    }

}
