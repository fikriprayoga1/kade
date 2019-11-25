package id.co.blogspot.fikriprayoga.www.footballmatchschedule.frontendcontroller


import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat.getSystemService
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import id.co.blogspot.fikriprayoga.www.footballmatchschedule.R
import id.co.blogspot.fikriprayoga.www.footballmatchschedule.backendcontroller.recyclerviewhandler.NMEFAdapter
import kotlinx.android.synthetic.main.fragment_next_match_event.*
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
class NextMatchEventFragment : Fragment() {
    lateinit var nmefAdapter: NMEFAdapter
    var nmefObjects: MutableList<NMEFAdapter.NMEFObject> = ArrayList()
    lateinit var nmefObject: NMEFAdapter.NMEFObject


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_next_match_event, container, false)
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

    fun getWebsites() {
        val mData = JSONObject((activity as MainActivity).getNextMatchData())
        val mData2 = mData.getJSONArray("events")

        showList(mData2)

    }

    fun showList(data: JSONArray) {
        nmefAdapter = NMEFAdapter(this.context!!, nmefObjects, obj)
        val mLayoutManager = LinearLayoutManager(context)
        recyclerView_fragment_next_match_event_1.layoutManager = mLayoutManager
        recyclerView_fragment_next_match_event_1.itemAnimator = DefaultItemAnimator()
        recyclerView_fragment_next_match_event_1.addItemDecoration(DividerItemDecoration(activity, LinearLayoutManager.VERTICAL))
        recyclerView_fragment_next_match_event_1.adapter = nmefAdapter


        nmefObjects.clear()

        for (i in 0 until data.length()) {
            nmefObject = NMEFAdapter.NMEFObject(data.getJSONObject(i))
            nmefObjects.add(nmefObject)

        }

        nmefAdapter.notifyDataSetChanged()

    }

    val obj = object : NMEFAdapter.NMEFListener {
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
            val mData = JSONObject((activity as MainActivity).getNextMatchData())
            val mData2 = mData.getJSONArray("events")

            nmefObjects.clear()

            for (i in 0 until mData2.length()) {
                nmefObject = NMEFAdapter.NMEFObject(mData2.getJSONObject(i))
                nmefObjects.add(nmefObject)

            }

            nmefAdapter.notifyDataSetChanged()

        }
    }

    private fun nextMatchAlarm() {
        //val alarmManager = (activity as MainActivity).getSystemService(Context.ALARM_SERVICE) as AlarmManager

        //alarmManager.set(AlarmManager.RTC_WAKEUP, )

        //alarmManager.cancel(PendingIntent.getBroadcast())

    }

}
