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
import id.co.blogspot.fikriprayoga.www.footballmatchschedule.backendcontroller.recyclerviewhandler.FTAdapter
import id.co.blogspot.fikriprayoga.www.footballmatchschedule.backendcontroller.recyclerviewhandler.FavoriteEventAdapter
import kotlinx.android.synthetic.main.fragment_favorite_event.*
import kotlinx.android.synthetic.main.fragment_favorite_team.*
import org.json.JSONArray

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class FavoriteTeamFragment : Fragment() {
    lateinit var ftAdapter: FTAdapter
    var ftObjects: MutableList<FTAdapter.FTObject> = ArrayList()
    lateinit var ftObject: FTAdapter.FTObject

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite_team, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        getData()

    }

    private fun getData() {
        showList((activity as MainActivity).readFavoriteTeam())

    }

    private fun showList(data: JSONArray) {
        ftAdapter = FTAdapter(this.context!!, ftObjects, obj)
        val mLayoutManager = LinearLayoutManager(context)
        recyclerView_fragment_favorite_team_1.layoutManager = mLayoutManager
        recyclerView_fragment_favorite_team_1.itemAnimator = DefaultItemAnimator()
        recyclerView_fragment_favorite_team_1.addItemDecoration(DividerItemDecoration(activity, LinearLayoutManager.VERTICAL))
        recyclerView_fragment_favorite_team_1.adapter = ftAdapter


        ftObjects.clear()

        for (i in 0 until data.length()) {
            ftObject = FTAdapter.FTObject(data.getJSONObject(i))
            ftObjects.add(ftObject)

        }

        ftAdapter.notifyDataSetChanged()

    }

    val obj = object : FTAdapter.FTListener {
        override fun item(itemData: Bundle) {
            (activity as MainActivity).changeFragment2(TeamDetailFragment(), "TeamDetailFragment", itemData)

        }
    }


}
