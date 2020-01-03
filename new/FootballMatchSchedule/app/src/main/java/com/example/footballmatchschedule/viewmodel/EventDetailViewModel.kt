package com.example.footballmatchschedule.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.footballmatchschedule.model.database.EventDatabase
import com.example.footballmatchschedule.other.helper.AlarmWorker
import com.example.footballmatchschedule.other.helper.ResponseListener
import com.example.footballmatchschedule.other.jetpack.UserRepository
import com.example.footballmatchschedule.view.MainActivity
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class EventDetailViewModel : ViewModel() {
    // 1
    private lateinit var userRepository: UserRepository
    // 2
    private lateinit var mainActivity: MainActivity

    fun init(userRepository: UserRepository, mainActivity: MainActivity) {
        this.userRepository = userRepository
        this.mainActivity = mainActivity

    }

    fun getNameList1(nameList: String?): String {
        var goalNameList = ""
        if (nameList != null) {
            val b1 = nameList.split(";")

            for (i in 0..(b1.size - 2)) {
                val c1 = b1[i].split(":")

                goalNameList = if (i == 0) {
                    "$goalNameList${i + 1}. ${c1[1]}"

                } else {
                    "$goalNameList\n${i + 1}. ${c1[1]}"
                }

            }

        }

        return goalNameList

    }

    fun getNameList2(nameList: String?): String {
        var goalNameList = ""
        if (nameList != null) {
            val b1 = nameList.split(";")

            for (i in 0..(b1.size - 2)) {
                goalNameList = if (i == 0) {
                    "$goalNameList${i + 1}. ${b1[i]}"

                } else {
                    "$goalNameList\n${i + 1}. ${b1[i]}"
                }

            }

        }

        return goalNameList

    }

    fun getGoalKeeperName(name: String?): String {
        var keeperName = ""
        if (name != null) {
            if (name != "") {
                keeperName = name.subSequence(0, name.length - 2).toString()
            }
        }

        return keeperName

    }

    fun requestTeamLogo(responseListener: ResponseListener, id: String?) {
        if (id != null) {
            userRepository.requestTeamDetail(responseListener, id)

        }

    }

    fun getMainActivity(): MainActivity {
        return mainActivity
    }

    fun getDate(inputData: String?): String {
        var date = ""

        if (inputData != null) {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd")
            val outputFormat = SimpleDateFormat("EEE, d MMM yyyy")
            val sourceDate = inputFormat.parse(inputData)
            if (sourceDate != null) {
                date = outputFormat.format(sourceDate)
            }

        }

        return date

    }

    fun isAlarm(eventDatabase: EventDatabase): Boolean {
        var mAlarm = false
        if (getMainActivity().viewModel.getIsFromAPI()) {
            val eventData = userRepository.readEvent(eventDatabase.idEvent!!)

            for (i in eventData.indices) {
                if (eventData[i].isAlarm != null) {
                    mAlarm = true

                }

            }

        } else {
            if (eventDatabase.isAlarm != null) {
                mAlarm = true

            }

        }

        return mAlarm

    }

    fun isNextDateAndHasIdEvent(eventDatabase: EventDatabase): Boolean {
        if (eventDatabase.idEvent != null) {
            val sourceDate = eventDatabase.dateEvent
            return if (sourceDate != null) {
                val inputFormat = SimpleDateFormat("yyyy-MM-dd")
                val eventDate = inputFormat.parse(sourceDate)
                if (eventDate != null) {
                    val eventDateLong = eventDate.time
                    val nowDate = Date().time
                    eventDateLong > nowDate

                } else {
                    return false

                }

            } else {
                false

            }

        } else {
            return false

        }

    }

    fun isNextDate(eventDatabase: EventDatabase): Boolean {
        val sourceDate = eventDatabase.dateEvent
        return if (sourceDate != null) {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd")
            val eventDate = inputFormat.parse(sourceDate)
            if (eventDate != null) {
                val eventDateLong = eventDate.time
                val nowDate = Date().time
                eventDateLong > nowDate

            } else {
                return false

            }

        } else {
            false

        }

    }

    fun hasIdEvent(eventDatabase: EventDatabase): Boolean {
        return eventDatabase.idEvent != null

    }

    fun isFavorite(eventDatabase: EventDatabase): Boolean {
        var mFavorite = false
        if (getMainActivity().viewModel.getIsFromAPI()) {
            val eventData = userRepository.readEvent(eventDatabase.idEvent!!)

            for (i in eventData.indices) {
                if (eventData[i].isFavorite != null) {
                    mFavorite = true

                }

            }

        } else {
            if (eventDatabase.isFavorite != null) {
                mFavorite = true

            }

        }

        return mFavorite

    }

    fun setAlarm(isAlarm: Boolean, eventDatabase: EventDatabase, context: Context) {
        var eventData = emptyList<EventDatabase>()
        if (getMainActivity().viewModel.getIsFromAPI()) {
            eventData = userRepository.readEvent(eventDatabase.idEvent!!)

        }

        if (isAlarm) {
            val uploadWorkRequest = OneTimeWorkRequestBuilder<AlarmWorker>()
            val data = Data.Builder()
            data.putString("idEvent", eventDatabase.idEvent)
            data.putString("strLeague", eventDatabase.strLeague)
            data.putString("strEvent", eventDatabase.strEvent)

            val inputFormat = SimpleDateFormat("yyyy-MM-dd")
            val date = inputFormat.parse(eventDatabase.dateEvent!!)!!
            val duration = (date.time - Date().time) / 1000


            uploadWorkRequest.setInitialDelay(duration, TimeUnit.SECONDS)
            uploadWorkRequest.setInputData(data.build())
            val workRequest = uploadWorkRequest.build()
            WorkManager.getInstance(context).enqueue(workRequest)
            val workRequestID = workRequest.id.toString()

            if (getMainActivity().viewModel.getIsFromAPI()) {
                if (eventData.isNotEmpty()) {
                    for (i in eventData.indices) {
                        updateEventDatabase(
                            eventDatabase,
                            eventData[i].id,
                            workRequestID,
                            eventData[i].isFavorite
                        )

                    }

                } else {
                    createEventDatabase(eventDatabase, workRequestID, null)

                }

            } else {
                updateEventDatabase(
                    eventDatabase,
                    eventDatabase.id,
                    workRequestID,
                    eventDatabase.isFavorite
                )

            }


        } else {
            if (getMainActivity().viewModel.getIsFromAPI()) {
                for (i in eventData.indices) {
                    WorkManager.getInstance(context)
                        .cancelWorkById(UUID.fromString(eventData[i].isAlarm))
                    if (eventData[i].isFavorite != null) {
                        updateEventDatabase(
                            eventDatabase,
                            eventData[i].id,
                            null,
                            eventData[i].isFavorite
                        )

                    } else {
                        userRepository.deleteEvent(eventData[i])
                    }

                }

            } else {
                WorkManager.getInstance(context)
                    .cancelWorkById(UUID.fromString(eventDatabase.isAlarm))
                if (eventDatabase.isFavorite != null) {
                    updateEventDatabase(
                        eventDatabase,
                        eventDatabase.id,
                        null,
                        eventDatabase.isFavorite
                    )

                } else {
                    userRepository.deleteEvent(eventDatabase)
                }

            }

        }

    }

    fun setFavorite(isFavorite: Boolean, eventDatabase: EventDatabase) {
        var eventData = emptyList<EventDatabase>()
        if (getMainActivity().viewModel.getIsFromAPI()) {
            eventData = userRepository.readEvent(eventDatabase.idEvent!!)

        }

        if (isFavorite) {
            if (getMainActivity().viewModel.getIsFromAPI()) {
                if (eventData.isNotEmpty()) {
                    for (i in eventData.indices) {
                        updateEventDatabase(
                            eventDatabase,
                            eventData[i].id,
                            eventData[i].isAlarm,
                            true
                        )

                    }

                } else {
                    createEventDatabase(eventDatabase, eventDatabase.isAlarm, true)

                }

            } else {
                updateEventDatabase(eventDatabase, eventDatabase.id, eventDatabase.isAlarm, true)

            }

        } else {
            if (getMainActivity().viewModel.getIsFromAPI()) {
                for (i in eventData.indices) {
                    if (eventData[i].isAlarm != null) {
                        updateEventDatabase(
                            eventDatabase,
                            eventData[i].id,
                            eventData[i].isAlarm,
                            null
                        )

                    } else {
                        userRepository.deleteEvent(eventData[i])
                    }

                }

            } else {
                if (eventDatabase.isAlarm != null) {
                    updateEventDatabase(
                        eventDatabase,
                        eventDatabase.id,
                        eventDatabase.isAlarm,
                        null
                    )

                } else {
                    userRepository.deleteEvent(eventDatabase)
                }

            }

        }

    }

    private fun updateEventDatabase(
        eventDatabase: EventDatabase,
        id: Int,
        isAlarm: String?,
        isFavorite: Boolean?
    ) {
        userRepository.updateEvent(
            EventDatabase(
                id,
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
                isAlarm,
                isFavorite
            )
        )

    }

    private fun createEventDatabase(
        eventDatabase: EventDatabase,
        isAlarm: String?,
        isFavorite: Boolean?
    ) {
        userRepository.createEvent(
            EventDatabase(
                0,
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
                isAlarm,
                isFavorite

            )
        )

    }

}
