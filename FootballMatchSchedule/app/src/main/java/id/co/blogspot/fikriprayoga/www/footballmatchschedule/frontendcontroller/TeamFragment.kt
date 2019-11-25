package id.co.blogspot.fikriprayoga.www.footballmatchschedule.frontendcontroller


import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.google.gson.Gson

import id.co.blogspot.fikriprayoga.www.footballmatchschedule.R
import kotlinx.android.synthetic.main.fragment_event.*
import kotlinx.android.synthetic.main.fragment_team.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import id.co.blogspot.fikriprayoga.www.footballmatchschedule.backendcontroller.MainHandler
import id.co.blogspot.fikriprayoga.www.footballmatchschedule.backendcontroller.recyclerviewhandler.LMEFAdapter
import id.co.blogspot.fikriprayoga.www.footballmatchschedule.backendcontroller.recyclerviewhandler.TeamAdapter
import kotlinx.android.synthetic.main.fragment_last_match_event.*
import org.json.JSONArray
import android.content.Intent
import id.co.blogspot.fikriprayoga.www.footballmatchschedule.frontendcontroller.searchteam.SearchTeamActivity



// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class TeamFragment : Fragment(), AdapterView.OnItemSelectedListener {
    lateinit var teamAdapter: TeamAdapter
    var teamObjects: MutableList<TeamAdapter.TeamObject> = ArrayList()
    lateinit var teamObject: TeamAdapter.TeamObject

    val spinnerNameList: ArrayList<String> = ArrayList()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_team, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        imageButton_fragment_team_2.setOnClickListener {
            try {
                // for hiding keyboard when done button pressed
                val imm = activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(activity!!.currentFocus!!.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)

            } catch (e: Exception) {}

            getSearchTeam(editText_fragment_team_1.text.toString())

        }

        spinner_fragment_team_3_1.onItemSelectedListener = this
        val q = JSONObject((activity as MainActivity).getLeagueListData()).getJSONArray("leagues")
        for (w in 0..(q.length()-1)) {
            spinnerNameList.add(q.getJSONObject(w).getString("strLeague"))

        }

        val aa = ArrayAdapter(context, android.R.layout.simple_spinner_item, spinnerNameList)
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner_fragment_team_3_1.setAdapter(aa)

        teamListHandler()

    }

    override fun onNothingSelected(parent: AdapterView<*>?) {}

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if (!(spinnerNameList.get(position) == (activity as MainActivity).getFirstLeagueName2())) {
            getTeamList(spinnerNameList[position])
            (activity as MainActivity).setFirstLeagueName2(spinnerNameList.get(position))

        }

    }

    private fun getSearchTeam(keyword: String) {
        (activity as MainActivity).startLoading()
        val api = MainHandler().init()
        val call = api!!.readSearchTeam(keyword)

        call.enqueue(object : Callback<Any> {
            override fun onFailure(call: Call<Any>, t: Throwable) {
                (activity as MainActivity).stopLoading()
                (activity as MainActivity).popUp(t.toString())
                Log.e(getTag(), t.toString())

            }

            override fun onResponse(call: Call<Any>, response: Response<Any>) {
                (activity as MainActivity).stopLoading()

                if (response.body() != null) {
                    val intent = Intent(context, SearchTeamActivity::class.java)
                    intent.putExtra("Data", Gson().toJson(response.body()))  // pass your values and retrieve them in the other Activity using keyName
                    startActivity(intent)

                } else {
                    (activity as MainActivity).popUp("Response is null")

                }

            }

        })

    }

    private fun getTeamList(leagueName: String) {
        (activity as MainActivity).startLoading()
        val api = MainHandler().init()
        val call = api!!.readTeamList(leagueName)

        call.enqueue(object : Callback<Any> {
            override fun onFailure(call: Call<Any>, t: Throwable) {
                (activity as MainActivity).stopLoading()
                (activity as MainActivity).popUp(t.toString())
                Log.e(getTag(), t.toString())

            }

            override fun onResponse(call: Call<Any>, response: Response<Any>) {
                (activity as MainActivity).stopLoading()

                if (response.body() != null) {
                    (activity as MainActivity).setTeamListData(Gson().toJson(response.body()))
                    val mData = JSONObject((activity as MainActivity).getTeamListData()).getJSONArray("teams")
                    teamObjects.clear()

                    for (i in 0 until mData.length()) {
                        teamObject = TeamAdapter.TeamObject(mData.getJSONObject(i))
                        teamObjects.add(teamObject)

                    }

                    teamAdapter.notifyDataSetChanged()

                } else {
                    (activity as MainActivity).popUp("Response is null")

                }

            }

        })

    }

    private fun getPlayer(TeamID: Int, itemData: Bundle) {
        (activity as MainActivity).startLoading()
        val api = MainHandler().init()
        val call = api!!.readPlayer(TeamID)

        call.enqueue(object : Callback<Any> {
            override fun onFailure(call: Call<Any>, t: Throwable) {
                (activity as MainActivity).stopLoading()
                (activity as MainActivity).popUp(t.toString())
                Log.e(getTag(), t.toString())

            }

            override fun onResponse(call: Call<Any>, response: Response<Any>) {
                (activity as MainActivity).stopLoading()

                if (response.body() != null) {
                    itemData.putString("Player", Gson().toJson(response.body()))
                    itemData.putBoolean("isFromSearchTeamActivity", false)
                    (activity as MainActivity).changeFragment2(TeamDetailFragment(), "TeamDetailFragment", itemData)

                } else {
                    (activity as MainActivity).popUp("Response is null")

                }

            }

        })

    }

    @SuppressLint("NewApi")
    fun teamListHandler() {
        val mData = JSONObject((activity as MainActivity).getTeamListData())
        val mData2 = mData.getJSONArray("teams")

        showList(mData2)

    }

    fun showList(data: JSONArray) {
        teamAdapter = TeamAdapter(this.context!!, teamObjects, obj)
        val mLayoutManager = LinearLayoutManager(context)
        recyclerView_fragment_team_3_2.layoutManager = mLayoutManager
        recyclerView_fragment_team_3_2.itemAnimator = DefaultItemAnimator()
        recyclerView_fragment_team_3_2.addItemDecoration(DividerItemDecoration(activity, LinearLayoutManager.VERTICAL))
        recyclerView_fragment_team_3_2.adapter = teamAdapter


        teamObjects.clear()

        for (i in 0 until data.length()) {
            teamObject = TeamAdapter.TeamObject(data.getJSONObject(i))
            teamObjects.add(teamObject)

        }

        teamAdapter.notifyDataSetChanged()

    }

    val obj = object : TeamAdapter.TeamListener {
        override fun item(itemData: Bundle) {
            getPlayer(JSONObject(itemData.getString("Data")).getString("idTeam").toInt(), itemData)

        }
    }

}
