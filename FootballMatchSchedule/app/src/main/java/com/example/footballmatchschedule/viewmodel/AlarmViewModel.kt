package com.example.footballmatchschedule.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.work.WorkManager
import com.example.footballmatchschedule.model.database.EventDatabase
import com.example.footballmatchschedule.util.jetpack.UserRepository
import com.example.footballmatchschedule.util.recyclerviewadapter.EventRecyclerViewAdapter
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
    private var alarmEvent: LiveData<List<EventDatabase>>? = null
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

    fun getAlarmEvent(): LiveData<List<EventDatabase>>? {
        return alarmEvent
    }

    fun initEventList(eventList: List<EventDatabase>?, context: Context) {
        val nowDate = Date().time

        eventObjects.clear()

        if (eventList != null) {
            for (i in eventList.indices) {
                val sourceDate = eventList[i].dateEvent
                if (sourceDate != null) {
                    val inputFormat = SimpleDateFormat("yyyy-MM-dd")
                    val eventDate = inputFormat.parse(sourceDate)!!
                    val eventDateLong = eventDate.time
                    if (eventDateLong > nowDate) {
                        eventObject = EventRecyclerViewAdapter.EventObject(eventList[i])
                        eventObjects.add(eventObject)

                    } else {
                        WorkManager.getInstance(context)
                            .cancelWorkById(UUID.fromString(eventList[i].isAlarm))
                        if (eventList[i].isFavorite != null) {
                            setAlarmNull(eventList[i])

                        } else {
                            userRepository!!.deleteEvent(eventList[i])
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

    private fun setAlarmNull(eventDatabase: EventDatabase) {
        userRepository!!.updateEvent(
            EventDatabase(
                eventDatabase.id,
                eventDatabase.dateEvent,
                eventDatabase.idEvent,
                eventDatabase.strHomeTeam,
                eventDatabase.strAwayTeam,
                eventDatabase.intHomeScore,
                eventDatabase.intAwayScore,
                eventDatabase.idHomeTeam,
                eventDatabase.idAwayTeam,
                eventDatabase.intHomeShots,
                eventDatabase.intAwayShots,
                eventDatabase.strHomeGoalDetails,
                eventDatabase.strAwayGoalDetails,
                eventDatabase.strHomeLineupGoalkeeper,
                eventDatabase.strAwayLineupGoalkeeper,
                eventDatabase.strHomeLineupDefense,
                eventDatabase.strAwayLineupDefense,
                eventDatabase.strHomeLineupMidfield,
                eventDatabase.strAwayLineupMidfield,
                eventDatabase.strHomeLineupForward,
                eventDatabase.strAwayLineupForward,
                eventDatabase.strHomeLineupSubstitutes,
                eventDatabase.strAwayLineupSubstitutes,
                eventDatabase.strLeague,
                eventDatabase.strEvent,
                null,
                eventDatabase.isFavorite
            )
        )

    }

}
