package id.co.blogspot.fikriprayoga.www.footballmatchschedule.frontendcontroller

import android.annotation.SuppressLint
import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.gson.Gson
import id.co.blogspot.fikriprayoga.www.footballmatchschedule.backendcontroller.MainHandler
import id.co.blogspot.fikriprayoga.www.footballmatchschedule.backendcontroller.database
import id.co.blogspot.fikriprayoga.www.footballmatchschedule.R
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.db.*
import org.jetbrains.anko.toast
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import android.widget.RemoteViews

// val sharedPreferences = getSharedPreferences("FMS", Context.MODE_PRIVATE)

class MainActivity : AppCompatActivity() {
    private val TAG = "DICODING"
    private var leagueListData = ""
    private var lastMatchData = ""
    private var nextMatchData = ""

    private var teamListData = ""

    private var firstLeagueName = ""
    private var firstLeagueName2 = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Handler().postDelayed({ getLeagueList() }, 1000)

        /*
        cardView_activity_main_2.setOnClickListener {
            Log.d(getTag(), "MainActivity/52 : " + "DITEKAN")
            notifTest("Mantap", 98)

        }
         */

    }

    @SuppressLint("MissingSuperCall")
    override fun onSaveInstanceState(outState: Bundle) {
        //No call for super(). Bug on API Level > 11.
    }

    // ---------------------------------------------------------------------------------------------

    fun changeFragment(fragment: Fragment, tag: String) {
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.frameLayout_activity_main_1, fragment, tag)
                .commitAllowingStateLoss()

    }

    fun changeFragment2(fragment: Fragment, tag: String, bundle: Bundle) {
        fragment.arguments = bundle
        supportFragmentManager
                .beginTransaction()
                .addToBackStack(null) // detect addbackstack
                .replace(R.id.frameLayout_activity_main_1, fragment, tag)
                .commitAllowingStateLoss()

    }

    fun startLoading() {
        runOnUiThread { cardView_activity_main_2.visibility = View.VISIBLE }

    }

    fun stopLoading() {
        runOnUiThread { cardView_activity_main_2.visibility = View.GONE }

    }

    private fun nextProgress() {
        cardView_activity_main_2.visibility = View.GONE

        changeFragment(HomeFragment(), "HomeFragment")

    }

    fun popUp(mMessage: String) {
        Toast.makeText(baseContext, mMessage, Toast.LENGTH_SHORT).show()

    }

    fun dateParser(inputData: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd")
        val outputFormat = SimpleDateFormat("EEE, d MMM yyyy")
        val date = inputFormat.parse(inputData)
        return outputFormat.format(date)

    }

    fun nameParsing1(s1: String, s2: String, i1: Int): String {
        return s1 + (i1 + 1).toString() + ". " + s2

    }

    fun nameParsing2(s1: String, s2: String, i1: Int): String {
        return s1 + "\n" + (i1 + 1).toString() + ". " + s2

    }

    // ---------------------------------------------------------------------------------------------

    private fun getLeagueList() {
        startLoading()
        val api = MainHandler().init()
        val call = api!!.readLeagueList()

        call.enqueue(object : Callback<Any> {
            override fun onFailure(call: Call<Any>, t: Throwable) {
                stopLoading()
                popUp(t.toString())
                Log.e(getTag(), t.toString())

            }

            override fun onResponse(call: Call<Any>, response: Response<Any>) {
                stopLoading()

                if (response.body() != null) {
                    leagueListData = Gson().toJson(response.body())
                    firstLeagueName = JSONObject(leagueListData).getJSONArray("leagues").getJSONObject(0).getString("strLeague")

                    getLastMatch(JSONObject(Gson().toJson(response.body())).getJSONArray("leagues").getJSONObject(0).getString("idLeague").toInt())

                } else {
                    popUp("Response is null")

                }

            }

        })

    }

    private fun getLastMatch(ID: Int) {
        startLoading()
        val api = MainHandler().init()
        val call = api!!.readLastMatch(ID)

        call.enqueue(object : Callback<Any> {
            override fun onFailure(call: Call<Any>, t: Throwable) {
                stopLoading()
                popUp(t.toString())
                Log.e(getTag(), t.toString())

            }

            override fun onResponse(call: Call<Any>, response: Response<Any>) {
                stopLoading()

                if (response.body() != null) {
                    lastMatchData = Gson().toJson(response.body())
                    getNextMatch(ID)

                } else {
                    popUp("Response is null")

                }

            }

        })

    }

    private fun getNextMatch(ID: Int) {
        startLoading()
        val api = MainHandler().init()
        val call = api!!.readNextMatch(ID)

        call.enqueue(object : Callback<Any> {
            override fun onFailure(call: Call<Any>, t: Throwable) {
                stopLoading()
                popUp(t.toString())
                Log.e(getTag(), t.toString())

            }

            override fun onResponse(call: Call<Any>, response: Response<Any>) {
                stopLoading()

                if (response.body() != null) {
                    nextMatchData = Gson().toJson(response.body())

                    getTeamList(JSONObject(leagueListData).getJSONArray("leagues").getJSONObject(0).getString("strLeague"))

                } else {
                    popUp("Response is null")

                }

            }

        })

    }

    private fun getTeamList(leagueList: String) {
        startLoading()
        val api = MainHandler().init()
        val call = api!!.readTeamList(leagueList)

        call.enqueue(object : Callback<Any> {
            override fun onFailure(call: Call<Any>, t: Throwable) {
                stopLoading()
                popUp(t.toString())
                Log.e(getTag(), t.toString())

            }

            override fun onResponse(call: Call<Any>, response: Response<Any>) {
                stopLoading()

                if (response.body() != null) {
                    teamListData = Gson().toJson(response.body())

                    nextProgress()

                } else {
                    popUp("Response is null")

                }

            }

        })

    }

    // ---------------------------------------------------------------------------------------------

    fun getLastMatchData(): String {
        return lastMatchData
    }

    fun getNextMatchData(): String {
        return nextMatchData
    }

    fun getLeagueListData(): String {
        return leagueListData

    }

    fun getTag(): String {
        return TAG

    }

    fun getFirstLeagueName(): String {
        return firstLeagueName

    }

    fun getFirstLeagueName2(): String {
        return firstLeagueName2

    }

    fun getTeamListData(): String {
        return teamListData

    }

    // ---------------------------------------------------------------------------------------------

    fun setLastMatchData(lastMatchData: String) {
        this.lastMatchData = lastMatchData

    }

    fun setNextMatchData(nextMatchData: String) {
        this.nextMatchData = nextMatchData

    }

    fun setFirstLeagueName(firstLeagueName: String) {
        this.firstLeagueName = firstLeagueName

    }

    fun setFirstLeagueName2(firstLeagueName2: String) {
        this.firstLeagueName2 = firstLeagueName2

    }

    fun setTeamListData(teamListData: String) {
        this.teamListData = teamListData

    }

    // ---------------------------------------------------------------------------------------------

                                     // ------- CREATE -------- //

    fun createData(LeagueID: String, EventID: String, Data: String) {
        database.use {
            insert("FavoriteEvent", "LeagueID" to LeagueID, "EventID" to EventID, "Data" to Data)

        }

    }

    fun createFavoriteTeam(LeagueID: String, TeamID: String, Data: String) {
        database.use {
            insert("FavoriteTeam", "LeagueID" to LeagueID, "TeamID" to TeamID, "Data" to Data)

        }

    }

    fun createAlarm(LeagueID: String, EventID: String, Data: String) {
        database.use {
            insert("NextEventAlarm", "LeagueID" to LeagueID, "EventID" to EventID, "Data" to Data)

        }

    }

                                    // ------- READ -------- //

    fun readData(): JSONArray {
        val mData = JSONArray()

        database.use {
            select("FavoriteEvent", "Data").exec {


                parseList(object : RowParser<String> {
                    override fun parseRow(columns: Array<Any?>): String {
                        // Concatenate the values of the first and second columns,
                        // which happen to be NAME and AGE
                        return "${columns[0]}"
                    }
                }).forEach {
                    mData.put(JSONObject(it))

                }

            }

        }

        return mData

    }

    fun readFavoriteTeam(): JSONArray {
        val mData = JSONArray()

        database.use {
            select("FavoriteTeam", "Data").exec {


                parseList(object : RowParser<String> {
                    override fun parseRow(columns: Array<Any?>): String {
                        // Concatenate the values of the first and second columns,
                        // which happen to be NAME and AGE
                        return "${columns[0]}"
                    }
                }).forEach {
                    mData.put(JSONObject(it))

                }

            }

        }

        return mData

    }

    fun readAlarm(): JSONArray {
        val mData = JSONArray()

        database.use {
            select("NextEventAlarm", "Data").exec {


                parseList(object : RowParser<String> {
                    override fun parseRow(columns: Array<Any?>): String {
                        // Concatenate the values of the first and second columns,
                        // which happen to be NAME and AGE
                        return "${columns[0]}"
                    }
                }).forEach {
                    mData.put(JSONObject(it))

                }

            }

        }

        return mData

    }

    fun readAlarmId(LeagueID: String, EventID: String): Int {
        var mData = 0

        database.use {
            select("NextEventAlarm", "ID").whereArgs("LeagueID = {LeagueID} AND EventID = {EventID}", "LeagueID" to LeagueID, "EventID" to EventID).exec {


                parseList(object : RowParser<String> {
                    override fun parseRow(columns: Array<Any?>): String {
                        // Concatenate the values of the first and second columns,
                        // which happen to be NAME and AGE
                        return "${columns[0]}"
                    }
                }).forEach {
                    mData = it.toInt()

                }

            }

        }

        return mData

    }

                                       // ------- DELETE -------- //

    fun deleteData(LeagueID: String, EventID: String) {
        database.use {
            delete("FavoriteEvent", "LeagueID = {LeagueID} AND EventID = {EventID}", "LeagueID" to LeagueID, "EventID" to EventID)
        }

    }

    fun deleteFavoriteTeam(LeagueID: String, TeamID: String) {
        database.use {
            delete("FavoriteTeam", "LeagueID = {LeagueID} AND TeamID = {TeamID}", "LeagueID" to LeagueID, "TeamID" to TeamID)
        }

    }

    fun deleteAlarm(LeagueID: String, EventID: String) {
        database.use {
            delete("NextEventAlarm", "LeagueID = {LeagueID} AND EventID = {EventID}", "LeagueID" to LeagueID, "EventID" to EventID)
        }

    }

}
