package id.co.blogspot.fikriprayoga.www.footballmatchschedule.frontendcontroller


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.co.blogspot.fikriprayoga.www.footballmatchschedule.backendcontroller.TabLayoutAdapter

import id.co.blogspot.fikriprayoga.www.footballmatchschedule.R
import kotlinx.android.synthetic.main.fragment_event.*
import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.support.v4.content.LocalBroadcastManager
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.google.gson.Gson
import id.co.blogspot.fikriprayoga.www.footballmatchschedule.backendcontroller.MyReceiver
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import id.co.blogspot.fikriprayoga.www.footballmatchschedule.backendcontroller.MainHandler
import org.json.JSONArray
import org.json.JSONObject


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class EventFragment : Fragment(), AdapterView.OnItemSelectedListener {
    val spinnerNameList: ArrayList<String> = ArrayList()
    val spinnerIdList: ArrayList<String> = ArrayList()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_event, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val adapter = TabLayoutAdapter(childFragmentManager)
        adapter.addFragment(LastMatchEventFragment(), "Last Match")
        adapter.addFragment(NextMatchEventFragment(), "Next Match")
        viewPager_fragment_event_3_3.adapter = adapter
        tabLayout_fragment_event_3_2.setupWithViewPager(viewPager_fragment_event_3_3)

        imageButton_fragment_event_2.setOnClickListener {
            try {
                // for hiding keyboard when done button pressed
                val imm = activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(activity!!.currentFocus!!.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)

            } catch (e: Exception) {}

            getSearchEvent(editText_fragment_event_1.text.toString())

        }

        spinner_fragment_event_3_1.onItemSelectedListener = this
        val q = JSONObject((activity as MainActivity).getLeagueListData()).getJSONArray("leagues")
        for (w in 0..(q.length()-1)) {
            spinnerIdList.add(q.getJSONObject(w).getString("idLeague"))
            spinnerNameList.add(q.getJSONObject(w).getString("strLeague"))

        }

        val aa = ArrayAdapter(context, android.R.layout.simple_spinner_item, spinnerNameList)
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner_fragment_event_3_1.setAdapter(aa)

    }

    override fun onNothingSelected(parent: AdapterView<*>?) {}

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if (!(spinnerNameList.get(position) == (activity as MainActivity).getFirstLeagueName())) {
            getLastMatch(spinnerIdList[position].toInt())
            (activity as MainActivity).setFirstLeagueName(spinnerNameList.get(position))

        }

    }

    fun broadcastIntent()
    {
        val intent = Intent()
        intent.action = "com.example.Broadcast"
        intent.flags = Intent.FLAG_INCLUDE_STOPPED_PACKAGES
        (activity as MainActivity).sendBroadcast(intent)
    }

    private fun getLastMatch(id: Int) {
        (activity as MainActivity).startLoading()
        val api = MainHandler().init()
        val call = api!!.readLastMatch(id)

        call.enqueue(object : Callback<Any> {
            override fun onFailure(call: Call<Any>, t: Throwable) {
                (activity as MainActivity).stopLoading()
                (activity as MainActivity).popUp(t.toString())
                Log.e(getTag(), t.toString())

            }

            override fun onResponse(call: Call<Any>, response: Response<Any>) {
                (activity as MainActivity).stopLoading()

                if (response.body() != null) {
                    (activity as MainActivity).setLastMatchData(Gson().toJson(response.body()))
                    getNextMatch(id)

                } else {
                    (activity as MainActivity).popUp("Response is null")

                }

            }

        })

    }

    private fun getNextMatch(id: Int) {
        (activity as MainActivity).startLoading()
        val api = MainHandler().init()
        val call = api!!.readNextMatch(id)

        call.enqueue(object : Callback<Any> {
            override fun onFailure(call: Call<Any>, t: Throwable) {
                (activity as MainActivity).stopLoading()
                (activity as MainActivity).popUp(t.toString())
                Log.e(getTag(), t.toString())

            }

            override fun onResponse(call: Call<Any>, response: Response<Any>) {
                (activity as MainActivity).stopLoading()

                if (response.body() != null) {
                    (activity as MainActivity).setNextMatchData(Gson().toJson(response.body()))
                    broadcastIntent()

                } else {
                    (activity as MainActivity).popUp("Response is null")

                }

            }

        })

    }

    private fun getSearchEvent(keyword: String) {
        (activity as MainActivity).startLoading()
        val api = MainHandler().init()
        val call = api!!.readSearchEvent(keyword)

        call.enqueue(object : Callback<Any> {
            override fun onFailure(call: Call<Any>, t: Throwable) {
                (activity as MainActivity).stopLoading()
                (activity as MainActivity).popUp(t.toString())
                Log.e(getTag(), t.toString())

            }

            override fun onResponse(call: Call<Any>, response: Response<Any>) {
                (activity as MainActivity).stopLoading()

                if (response.body() != null) {
                    val mDat = Bundle()
                    mDat.putString("Data", Gson().toJson(response.body()))
                    val mFrag = SearchEventFragment()
                    mFrag.arguments = mDat
                    (activity as MainActivity).supportFragmentManager
                            .beginTransaction()
                            .addToBackStack(null) // detect addbackstack
                            .add(R.id.constraintLayout_fragment_event_3, mFrag, "SearchEventFragment")
                            .commitAllowingStateLoss()

                } else {
                    (activity as MainActivity).popUp("Response is null")

                }

            }

        })

    }

}
