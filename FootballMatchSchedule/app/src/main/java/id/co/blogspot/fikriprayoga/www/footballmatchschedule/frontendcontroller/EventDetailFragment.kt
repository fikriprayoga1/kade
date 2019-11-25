package id.co.blogspot.fikriprayoga.www.footballmatchschedule.frontendcontroller


import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.google.gson.Gson
import com.squareup.picasso.Picasso

import id.co.blogspot.fikriprayoga.www.footballmatchschedule.R
import kotlinx.android.synthetic.main.fragment_event_detail.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import id.co.blogspot.fikriprayoga.www.footballmatchschedule.backendcontroller.MainHandler
import java.lang.Exception
import java.util.*
import id.co.blogspot.fikriprayoga.www.footballmatchschedule.backendcontroller.Receiver
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class EventDetailFragment : Fragment() {
    lateinit var str: String
    var isFavorite = false
    var isAlarm = false
    lateinit var mData: JSONObject

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_event_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val bundle = this@EventDetailFragment.arguments
        if (bundle != null) {
            mData = JSONObject(bundle.getString("Data"))

            try {
                str = (activity as MainActivity).dateParser(mData.getString("dateEvent"))

            } catch (e: java.lang.Exception) {
            }

            textView_fragment_event_detail_1_1.text = str
            setTeamLogo(mData.getString("idHomeTeam").toInt(), imageView_fragment_event_detail_1_2)
            setTeamLogo(mData.getString("idAwayTeam").toInt(), imageView_fragment_event_detail_1_7)
            textView_fragment_event_detail_1_3.text = mData.getString("strHomeTeam")
            textView_fragment_event_detail_1_8.text = mData.getString("strAwayTeam")
            try {
                textView_fragment_event_detail_1_4.text = mData.getString("intHomeScore")
                textView_fragment_event_detail_1_6.text = mData.getString("intAwayScore")
                textView_fragment_event_detail_1_10.text = mData.getString("intHomeShots")
                textView_fragment_event_detail_1_12.text = mData.getString("intAwayShots")
                setNameList1(mData.getString("strHomeGoalDetails"), textView_fragment_event_detail_1_13_2)
                setNameList1(mData.getString("strAwayGoalDetails"), textView_fragment_event_detail_1_13_4)
                textView_fragment_event_detail_1_14_2.text = mData.getString("strHomeLineupGoalkeeper").subSequence(0, mData.getString("strHomeLineupGoalkeeper").length - 2)
                textView_fragment_event_detail_1_14_4.text = mData.getString("strAwayLineupGoalkeeper").subSequence(0, mData.getString("strAwayLineupGoalkeeper").length - 2)
                setNameList2(mData.getString("strHomeLineupDefense"), textView_fragment_event_detail_1_15_2)
                setNameList2(mData.getString("strAwayLineupDefense"), textView_fragment_event_detail_1_15_4)
                setNameList2(mData.getString("strHomeLineupMidfield"), textView_fragment_event_detail_1_16_2)
                setNameList2(mData.getString("strAwayLineupMidfield"), textView_fragment_event_detail_1_16_4)
                setNameList2(mData.getString("strHomeLineupForward"), textView_fragment_event_detail_1_17_2)
                setNameList2(mData.getString("strAwayLineupForward"), textView_fragment_event_detail_1_17_4)
                setNameList2(mData.getString("strHomeLineupSubstitutes"), textView_fragment_event_detail_1_18_2)
                setNameList2(mData.getString("strAwayLineupSubstitutes"), textView_fragment_event_detail_1_18_4)

            } catch (e: Exception) {
                textView_fragment_event_detail_1_4.text = ""
                textView_fragment_event_detail_1_6.text = ""
                textView_fragment_event_detail_1_10.text = ""
                textView_fragment_event_detail_1_12.text = ""
                textView_fragment_event_detail_1_13_2.text = ""
                textView_fragment_event_detail_1_13_4.text = ""
                textView_fragment_event_detail_1_14_2.text = ""
                textView_fragment_event_detail_1_14_4.text = ""
                textView_fragment_event_detail_1_15_2.text = ""
                textView_fragment_event_detail_1_15_4.text = ""
                textView_fragment_event_detail_1_16_2.text = ""
                textView_fragment_event_detail_1_16_4.text = ""
                textView_fragment_event_detail_1_17_2.text = ""
                textView_fragment_event_detail_1_17_4.text = ""
                textView_fragment_event_detail_1_18_2.text = ""
                textView_fragment_event_detail_1_18_4.text = ""

            }

            try {
                val g1 = (activity as MainActivity).readData()
                for (i in 0..(g1.length()-1)) {
                    val m = g1.getJSONObject(i).getString("idLeague")
                    val n = g1.getJSONObject(i).getString("idEvent")
                    if ((mData.getString("idLeague").equals(m)) && (mData.getString("idEvent").equals(n))) {
                        imageView_fragment_event_detail_1_19.setImageResource(R.drawable.ic_favorite_red_24dp)
                        isFavorite = true
                        break

                    }

                }

            } catch (e: Exception) {}

            try {
                val a = (activity as MainActivity).readAlarm()
                for (b in 0..(a.length()-1)) {
                    val c = a.getJSONObject(b).getString("idLeague")
                    val d = a.getJSONObject(b).getString("idEvent")

                    if ((mData.getString("idLeague").equals(c)) && (mData.getString("idEvent").equals(d))) {
                        imageButton_fragment_event_detail_1_20.setImageResource(R.drawable.ic_notifications_accent_24dp)
                        isAlarm = true
                        break

                    }

                }


            } catch (e: Exception) {}

        }

        imageView_fragment_event_detail_1_19.setOnClickListener {
            if (isFavorite) {
                (activity as MainActivity).deleteData(mData.getString("idLeague"), mData.getString("idEvent"))
                imageView_fragment_event_detail_1_19.setImageResource(R.drawable.ic_favorite_border_red_24dp)

            } else {
                (activity as MainActivity).createData(mData.getString("idLeague"), mData.getString("idEvent"), mData.toString())
                imageView_fragment_event_detail_1_19.setImageResource(R.drawable.ic_favorite_red_24dp)

            }

        }

        imageButton_fragment_event_detail_1_20.setOnClickListener {
            if (isAlarm) {
                deleteAlarm((activity as MainActivity).readAlarmId(mData.getString("idLeague"), mData.getString("idEvent")))
                //deleteAlarm(0)
                (activity as MainActivity).deleteAlarm(mData.getString("idLeague"), mData.getString("idEvent"))
                imageButton_fragment_event_detail_1_20.setImageResource(R.drawable.ic_notifications_none_accent_24dp)
                isAlarm = false

            } else {
                (activity as MainActivity).createAlarm(mData.getString("idLeague"), mData.getString("idEvent"), mData.toString())
                createAlarm((activity as MainActivity).readAlarmId(mData.getString("idLeague"), mData.getString("idEvent")), dateParser(mData.getString("strDate") + " " + mData.getString("strTime").substring(0, 8) + " " + mData.getString("strTime").substring(8, 11) + mData.getString("strTime").substring(12)), mData.getString("strLeague") + " " + mData.getString("strEvent"))
                //createAlarm((activity as MainActivity).readAlarmId(mData.getString("idLeague"), mData.getString("idEvent")), dateParser("08/11/18 06:00:00 +0000"), mData.getString("strLeague") + " " + mData.getString("strEvent"))
                imageButton_fragment_event_detail_1_20.setImageResource(R.drawable.ic_notifications_accent_24dp)
                isAlarm = true

            }

        }

    }

    private fun setTeamLogo(id: Int, mImage: ImageView) {
        (activity as MainActivity).startLoading()
        val api = MainHandler().init()
        val call = api!!.readDetailTeam(id)

        call.enqueue(object : Callback<Any> {
            override fun onFailure(call: Call<Any>, t: Throwable) {
                (activity as MainActivity).stopLoading()
                (activity as MainActivity).popUp(t.toString())
                Log.e(tag, t.toString())

            }

            override fun onResponse(call: Call<Any>, response: Response<Any>) {
                (activity as MainActivity).stopLoading()

                if (response.body() != null) {
                    Picasso.get()
                            .load(JSONObject(Gson().toJson(response.body())).getJSONArray("teams").getJSONObject(0).getString("strTeamBadge"))
                            .resize(100, 100)
                            .into(mImage)

                } else {
                    (activity as MainActivity).popUp("Response is null")

                }

            }

        })

    }

    private fun setNameList1(a1: String, mtext: TextView) {
        var goalNameList = ""
        val b1 = a1.split(";")

        for (i in 0..(b1.size - 2)) {
            val c1 = b1[i].split(":")

            if (i == 0) {
                goalNameList = (activity as MainActivity).nameParsing1(goalNameList, c1[1], i)

            } else {
                goalNameList = (activity as MainActivity).nameParsing2(goalNameList, c1[1], i)

            }

        }

        mtext.text = goalNameList

    }

    private fun setNameList2(a1: String, mtext: TextView) {
        var goalNameList = ""
        val b1 = a1.split(";")

        for (i in 0..(b1.size - 2)) {
            if (i == 0) {
                goalNameList = goalNameList + (i + 1).toString() + ". " + b1[i]

            } else {
                goalNameList = goalNameList + "\n" + (i + 1).toString() + ". " + b1[i]

            }

        }

        mtext.text = goalNameList

    }

    private fun createAlarm(ID: Int, Time: Long, textContent: String) {
        val alarmManager = (activity as MainActivity).getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.set(AlarmManager.RTC_WAKEUP, Time, PendingIntent.getBroadcast(context, ID, Intent(context, Receiver::class.java).apply {
            putExtra("notificationId", ID)
            putExtra("textContent", textContent)
        }, PendingIntent.FLAG_UPDATE_CURRENT))

    }

    private fun deleteAlarm(ID: Int) {
        val alarmManager = (activity as MainActivity).getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.cancel(PendingIntent.getBroadcast(context, ID, Intent(context, Receiver::class.java), 0))

    }

    @SuppressLint("SimpleDateFormat")
    private fun dateParser(date: String): Long {
        return SimpleDateFormat("dd/MM/yy HH:mm:ss Z").parse(date).time

    }

}
