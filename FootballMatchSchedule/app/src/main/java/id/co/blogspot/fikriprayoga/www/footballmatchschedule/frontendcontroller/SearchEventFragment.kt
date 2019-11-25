package id.co.blogspot.fikriprayoga.www.footballmatchschedule.frontendcontroller


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import id.co.blogspot.fikriprayoga.www.footballmatchschedule.R
import id.co.blogspot.fikriprayoga.www.footballmatchschedule.backendcontroller.recyclerviewhandler.SEFAdapter
import kotlinx.android.synthetic.main.fragment_search_event.*
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
class SearchEventFragment : Fragment() {
    lateinit var sefAdapter: SEFAdapter
    var sefObjects: MutableList<SEFAdapter.SEFObject> = ArrayList()
    lateinit var sefObject: SEFAdapter.SEFObject

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_event, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val bundle = this@SearchEventFragment.arguments
        if (bundle != null) {
            val mData = JSONObject(bundle.getString("Data"))
            val mData2 = mData.getJSONArray("event")

            showList(mData2)

        }

    }

    fun showList(data: JSONArray) {
        sefAdapter = SEFAdapter(this.context!!, sefObjects, obj)
        val mLayoutManager = LinearLayoutManager(context)
        recyclerView_fragment_search_event_1.layoutManager = mLayoutManager
        recyclerView_fragment_search_event_1.itemAnimator = DefaultItemAnimator()
        recyclerView_fragment_search_event_1.addItemDecoration(DividerItemDecoration(activity, LinearLayoutManager.VERTICAL))
        recyclerView_fragment_search_event_1.adapter = sefAdapter


        sefObjects.clear()

        for (i in 0 until data.length()) {
            sefObject = SEFAdapter.SEFObject(data.getJSONObject(i))
            sefObjects.add(sefObject)

        }

        sefAdapter.notifyDataSetChanged()

    }

    val obj = object : SEFAdapter.SEFListener {
        override fun item(itemData: Bundle) {
            val mFrag = EventDetailFragment()
            mFrag.arguments = itemData
            (activity as MainActivity).supportFragmentManager
                    .beginTransaction()
                    .addToBackStack(null) // detect addbackstack
                    .add(R.id.frameLayout_activity_main_1, mFrag, "EventDetailFragment")
                    .commitAllowingStateLoss()

        }
    }

}
