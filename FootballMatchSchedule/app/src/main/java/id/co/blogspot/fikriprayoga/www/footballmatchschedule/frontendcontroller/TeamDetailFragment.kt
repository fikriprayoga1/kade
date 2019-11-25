package id.co.blogspot.fikriprayoga.www.footballmatchschedule.frontendcontroller


import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import com.squareup.picasso.Picasso

import id.co.blogspot.fikriprayoga.www.footballmatchschedule.R
import id.co.blogspot.fikriprayoga.www.footballmatchschedule.backendcontroller.recyclerviewhandler.TDFAdapter
import kotlinx.android.synthetic.main.fragment_team_detail.*
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import id.co.blogspot.fikriprayoga.www.footballmatchschedule.backendcontroller.MainHandler
import id.co.blogspot.fikriprayoga.www.footballmatchschedule.frontendcontroller.searchteam.SearchTeamActivity
import kotlinx.android.synthetic.main.fragment_event_detail.*
import java.lang.Exception

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class TeamDetailFragment : Fragment() {
    lateinit var tdAdapter: TDFAdapter
    var tdObjects: MutableList<TDFAdapter.TDFObject> = ArrayList()
    lateinit var tdObject: TDFAdapter.TDFObject
    var isFavorite = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_team_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val bundle = this@TeamDetailFragment.arguments
        if (bundle != null) {
            val teamDetail = JSONObject(bundle.getString("Data"))
            Log.d("DICODING", "TeamDetailFragment/56 : " + bundle.getString("Data"))

            Picasso.get()
                    .load(teamDetail.getString("strTeamBadge"))
                    .resize(100, 100)
                    .into(imageView_fragment_team_detail_1_1_1)
            textView_fragment_team_detail_1_1_2.text = teamDetail.getString("strTeam")
            textView_fragment_team_detail_1_1_3.text = teamDetail.getString("intFormedYear")
            textView_fragment_team_detail_1_1_4.text = teamDetail.getString("strStadium")
            textView_fragment_team_detail_1_2.text = teamDetail.getString("strDescriptionEN")

            if (bundle.getBoolean("isFromSearchTeamActivity")) {
                try {
                    val g1 = (activity as SearchTeamActivity).readFavoriteTeam()
                    for (i in 0..(g1.length()-1)) {
                        val m = g1.getJSONObject(i).getString("idLeague")
                        val n = g1.getJSONObject(i).getString("idTeam")
                        if ((teamDetail.getString("idLeague").equals(m)) && (teamDetail.getString("idTeam").equals(n))) {
                            imageView_fragment_team_detail_1_4.setImageResource(R.drawable.ic_favorite_red_24dp)
                            isFavorite = true
                            break

                        }

                    }

                } catch (e: Exception) {}

                imageView_fragment_team_detail_1_4.setOnClickListener {
                    if (isFavorite) {
                        (activity as SearchTeamActivity).deleteFavoriteTeam(teamDetail.getString("idLeague"), teamDetail.getString("idTeam"))
                        imageView_fragment_team_detail_1_4.setImageResource(R.drawable.ic_favorite_border_red_24dp)

                    } else {
                        (activity as SearchTeamActivity).createFavoriteTeam(teamDetail.getString("idLeague"), teamDetail.getString("idTeam"), teamDetail.toString())
                        imageView_fragment_team_detail_1_4.setImageResource(R.drawable.ic_favorite_red_24dp)
                        isFavorite = true

                    }

                }


            } else {
                try {
                    val g1 = (activity as MainActivity).readFavoriteTeam()
                    for (i in 0..(g1.length()-1)) {
                        val m = g1.getJSONObject(i).getString("idLeague")
                        val n = g1.getJSONObject(i).getString("idTeam")
                        if ((teamDetail.getString("idLeague").equals(m)) && (teamDetail.getString("idTeam").equals(n))) {
                            imageView_fragment_team_detail_1_4.setImageResource(R.drawable.ic_favorite_red_24dp)
                            isFavorite = true
                            break

                        }

                    }

                } catch (e: Exception) {}

                imageView_fragment_team_detail_1_4.setOnClickListener {
                    if (isFavorite) {
                        (activity as MainActivity).deleteFavoriteTeam(teamDetail.getString("idLeague"), teamDetail.getString("idTeam"))
                        imageView_fragment_team_detail_1_4.setImageResource(R.drawable.ic_favorite_border_red_24dp)

                    } else {
                        (activity as MainActivity).createFavoriteTeam(teamDetail.getString("idLeague"), teamDetail.getString("idTeam"), teamDetail.toString())
                        imageView_fragment_team_detail_1_4.setImageResource(R.drawable.ic_favorite_red_24dp)
                        isFavorite = true

                    }

                }

            }

            try {
                getData(bundle.getString("Player")!!)

            } catch (e: Exception) {}

        }

    }

    /*
    private fun getPlayer(PlayerID: Int, itemData: Bundle) {
        (activity as MainActivity).startLoading()
        val api = MainHandler().init()
        val call = api!!.readPlayerDetail(PlayerID)

        call.enqueue(object : Callback<Any> {
            override fun onFailure(call: Call<Any>, t: Throwable) {
                (activity as MainActivity).stopLoading()
                (activity as MainActivity).popUp(t.toString())
                Log.e(getTag(), t.toString())

            }

            override fun onResponse(call: Call<Any>, response: Response<Any>) {
                (activity as MainActivity).stopLoading()

                if (response.body() != null) {
                    itemData.putString("Data", Gson().toJson(response.body()))
                    (activity as MainActivity).changeFragment2(PlayerDetailFragment(), "PlayerDetailFragment", itemData)

                } else {
                    (activity as MainActivity).popUp("Response is null")

                }

            }

        })

    }
     */

    @SuppressLint("NewApi")
    fun getData(playerData: String) {
        val mData = JSONObject(playerData).getJSONArray("player")

        showList(mData)

    }

    fun showList(data: JSONArray) {
        tdAdapter = TDFAdapter(this.context!!, tdObjects, obj)
        val mLayoutManager = LinearLayoutManager(context)
        recyclerView_fragment_team_detail_1_3.layoutManager = mLayoutManager
        recyclerView_fragment_team_detail_1_3.itemAnimator = DefaultItemAnimator()
        recyclerView_fragment_team_detail_1_3.addItemDecoration(DividerItemDecoration(activity, LinearLayoutManager.VERTICAL))
        recyclerView_fragment_team_detail_1_3.adapter = tdAdapter


        tdObjects.clear()

        for (i in 0 until data.length()) {
            tdObject = TDFAdapter.TDFObject(data.getJSONObject(i))
            tdObjects.add(tdObject)

        }

        tdAdapter.notifyDataSetChanged()

    }

    val obj = object : TDFAdapter.TDFListener {
        override fun item(itemData: Bundle) {
            (activity as MainActivity).changeFragment2(PlayerDetailFragment(), "PlayerDetailFragment", itemData)

        }
    }

}
