package id.co.blogspot.fikriprayoga.www.footballmatchschedule.frontendcontroller


import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import id.co.blogspot.fikriprayoga.www.footballmatchschedule.R
import id.co.blogspot.fikriprayoga.www.footballmatchschedule.backendcontroller.recyclerviewhandler.LMEFAdapter
import kotlinx.android.synthetic.main.fragment_last_match_event.*
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
class LastMatchEventFragment : Fragment() {
    lateinit var lmefAdapter: LMEFAdapter
    var lmefObjects: MutableList<LMEFAdapter.LMEFObject> = ArrayList()
    lateinit var lmefObject: LMEFAdapter.LMEFObject

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_last_match_event, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        getWebsites()

    }

    override fun onResume() {
        super.onResume()
        configureReceiver()
    }

    override fun onPause() {
        super.onPause()
        (activity as MainActivity).unregisterReceiver(mYourBroadcastReceiver)
    }

    @SuppressLint("NewApi")
    fun getWebsites() {
        val mData = JSONObject((activity as MainActivity).getLastMatchData())
        val mData2 = mData.getJSONArray("events")

        showList(mData2)

    }

    fun showList(data: JSONArray) {
        lmefAdapter = LMEFAdapter(this.context!!, lmefObjects, obj)
        val mLayoutManager = LinearLayoutManager(context)
        recyclerView_fragment_last_match_event_1.layoutManager = mLayoutManager
        recyclerView_fragment_last_match_event_1.itemAnimator = DefaultItemAnimator()
        recyclerView_fragment_last_match_event_1.addItemDecoration(DividerItemDecoration(activity, LinearLayoutManager.VERTICAL))
        recyclerView_fragment_last_match_event_1.adapter = lmefAdapter


        lmefObjects.clear()

        for (i in 0 until data.length()) {
            lmefObject = LMEFAdapter.LMEFObject(data.getJSONObject(i))
            lmefObjects.add(lmefObject)

        }

        lmefAdapter.notifyDataSetChanged()

    }

    val obj = object : LMEFAdapter.LMEFListener {
        override fun item(itemData: Bundle) {
            (activity as MainActivity).changeFragment2(EventDetailFragment(), "EventDetailFragment", itemData)

        }
    }

    private fun configureReceiver() {
        val filter = IntentFilter()
        filter.addAction("com.example.Broadcast")
        (activity as MainActivity).registerReceiver(mYourBroadcastReceiver, filter)
    }

    private var mYourBroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {

            val mData = JSONObject((activity as MainActivity).getLastMatchData())
            val mData2 = mData.getJSONArray("events")

            lmefObjects.clear()
            for (i in 0 until mData2.length()) {
                lmefObject = LMEFAdapter.LMEFObject(mData2.getJSONObject(i))
                lmefObjects.add(lmefObject)

            }

            lmefAdapter.notifyDataSetChanged()

        }
    }

}
