package com.example.footballmatchschedule.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.work.WorkManager
import com.example.footballmatchschedule.model.apiresponse.EventDetail
import com.example.footballmatchschedule.other.jetpack.UserRepository
import com.example.footballmatchschedule.other.recyclerviewadapter.EventRecyclerViewAdapter
import com.example.footballmatchschedule.view.MainActivity
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class AlarmViewModel : ViewModel() {
    // 1
    private var userRepository: UserRepository? = null
    // 2
    private lateinit var mainActivity: MainActivity
    // 3
    private var alarmEvent: LiveData<List<EventDetail>>? = null
    // 4
    private val eventObjects: MutableList<EventRecyclerViewAdapter.EventObject> = ArrayList()
    // 5
    private lateinit var eventObject: EventRecyclerViewAdapter.EventObject

    fun init(userRepository: UserRepository, mainActivity: MainActivity) {
        this.userRepository = userRepository
        this.mainActivity = mainActivity

        if (alarmEvent == null) {
            alarmEvent = userRepository.readAlarmEvent()

        }

    }

    fun getAlarmEvent(): LiveData<List<EventDetail>>? {
        return alarmEvent
    }

    fun initEventList(eventList: List<EventDetail>?, context: Context) {
        val nowDate = Date().time

        eventObjects.clear()

        if (eventList != null) {
            for (i in eventList.indices) {
                val sourceDate = eventList[i].dateEvent
                if (sourceDate != null) {
                    val inputFormat = SimpleDateFormat("yyyy-MM-dd")
                    val eventDate = inputFormat.parse(sourceDate).time
                    if (eventDate > nowDate) {
                        eventObject = EventRecyclerViewAdapter.EventObject(eventList[i])
                        eventObjects.add(eventObject)

                    } else {
                        WorkManager.getInstance(context)
                            .cancelWorkById(UUID.fromString(eventList[i].isAlarm))
                        if (eventList[i].isFavorite != null) {
                            setAlarmNull(eventList[i], eventList[i].isFavorite)

                        } else {
                            userRepository!!.deleteEvent(eventList[i].idEvent)
                        }

                    }

                }

            }

        }

    }

    fun getMainActivity(): MainActivity {
        return mainActivity
    }

    fun getEventObjects(): MutableList<EventRecyclerViewAdapter.EventObject> {
        return eventObjects
    }

    private fun setAlarmNull(eventDetail: EventDetail, isFavorite: Boolean?) {
        userRepository!!.createEvent(
            EventDetail(
                eventDetail.dateEvent,
                eventDetail.dateEventLocal,
                eventDetail.idAwayTeam,
                eventDetail.idEvent,
                eventDetail.idHomeTeam,
                eventDetail.idLeague,
                eventDetail.idSoccerXML,
                eventDetail.intAwayScore,
                eventDetail.intAwayShots,
                eventDetail.intHomeScore,
                eventDetail.intHomeShots,
                eventDetail.intRound,
                eventDetail.intSpectators,
                eventDetail.strAwayFormation,
                eventDetail.strAwayGoalDetails,
                eventDetail.strAwayLineupDefense,
                eventDetail.strAwayLineupForward,
                eventDetail.strAwayLineupGoalkeeper,
                eventDetail.strAwayLineupMidfield,
                eventDetail.strAwayLineupSubstitutes,
                eventDetail.strAwayRedCards,
                eventDetail.strAwayTeam,
                eventDetail.strAwayYellowCards,
                eventDetail.strBanner,
                eventDetail.strCircuit,
                eventDetail.strCity,
                eventDetail.strCountry,
                eventDetail.strDate,
                eventDetail.strDescriptionEN,
                eventDetail.strEvent,
                eventDetail.strEventAlternate,
                eventDetail.strFanart,
                eventDetail.strFilename,
                eventDetail.strHomeFormation,
                eventDetail.strHomeGoalDetails,
                eventDetail.strHomeLineupDefense,
                eventDetail.strHomeLineupForward,
                eventDetail.strHomeLineupGoalkeeper,
                eventDetail.strHomeLineupMidfield,
                eventDetail.strHomeLineupSubstitutes,
                eventDetail.strHomeRedCards,
                eventDetail.strHomeTeam,
                eventDetail.strHomeYellowCards,
                eventDetail.strLeague,
                eventDetail.strLocked,
                eventDetail.strMap,
                eventDetail.strPoster,
                eventDetail.strResult,
                eventDetail.strSeason,
                eventDetail.strSport,
                eventDetail.strTVStation,
                eventDetail.strThumb,
                eventDetail.strTime,
                eventDetail.strTimeLocal,
                eventDetail.strTweet1,
                eventDetail.strTweet2,
                eventDetail.strTweet3,
                eventDetail.strVideo,
                null,
                isFavorite
            )
        )

    }

}
