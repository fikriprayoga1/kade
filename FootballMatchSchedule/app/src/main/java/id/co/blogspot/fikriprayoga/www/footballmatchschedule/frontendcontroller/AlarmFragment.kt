package id.co.blogspot.fikriprayoga.www.footballmatchschedule.frontendcontroller


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import id.co.blogspot.fikriprayoga.www.footballmatchschedule.backendcontroller.recyclerviewhandler.AFAdapter.AFObject
import id.co.blogspot.fikriprayoga.www.footballmatchschedule.R
import id.co.blogspot.fikriprayoga.www.footballmatchschedule.backendcontroller.recyclerviewhandler.AFAdapter
import org.json.JSONArray
import kotlinx.android.synthetic.main.fragment_alarm.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class AlarmFragment : Fragment() {
    lateinit var afAdapter: AFAdapter
    var afObjects: MutableList<AFAdapter.AFObject> = ArrayList()
    lateinit var afObject: AFAdapter.AFObject

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_alarm, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        getData()

    }

    private fun getData() {
        showList((activity as MainActivity).readAlarm())

    }

    private fun showList(data: JSONArray) {
        afAdapter = AFAdapter(this.context!!, afObjects, obj)
        val mLayoutManager = LinearLayoutManager(context)
        recyclerView_fragment_alarm_1.layoutManager = mLayoutManager
        recyclerView_fragment_alarm_1.itemAnimator = DefaultItemAnimator()
        recyclerView_fragment_alarm_1.addItemDecoration(DividerItemDecoration(activity, LinearLayoutManager.VERTICAL))
        recyclerView_fragment_alarm_1.adapter = afAdapter


        afObjects.clear()

        for (i in 0 until data.length()) {
            afObject = AFObject(data.getJSONObject(i))
            afObjects.add(afObject)

        }

        afAdapter.notifyDataSetChanged()

    }

    val obj = object : AFAdapter.AFListener {
        override fun item(itemData: Bundle) {
            (activity as MainActivity).changeFragment2(EventDetailFragment(), "EventDetailFragment", itemData)

        }
    }

}
