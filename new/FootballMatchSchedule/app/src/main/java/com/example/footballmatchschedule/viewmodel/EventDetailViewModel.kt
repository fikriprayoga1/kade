package com.example.footballmatchschedule.viewmodel

import android.content.Context
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.footballmatchschedule.model.apiresponse.EventDetail
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
            if (sourceDate != null) { date = outputFormat.format(sourceDate) }

        }
        return date

    }

    fun isAlarm(idEvent: String): Boolean {
        val eventData = userRepository.readEvent(idEvent)
        var mAlarm = false

        for (i in eventData.indices) {
            if (eventData[i].isAlarm != null) {
                mAlarm = true

            }

        }

        return mAlarm

    }

    fun isFavorite(idEvent: String): Boolean {
        val eventData = userRepository.readEvent(idEvent)
        var mFavorite = false

        for (i in eventData.indices) {
            if (eventData[i].isFavorite != null) {
                mFavorite = true

            }

        }

        return mFavorite

    }

    fun setAlarm(isAlarm: Boolean, eventDetail: EventDetail, context: Context) {
        val eventData = userRepository.readEvent(eventDetail.idEvent)

        if (isAlarm) {
            val uploadWorkRequest = OneTimeWorkRequestBuilder<AlarmWorker>()
            val data = Data.Builder()
            data.putString("idEvent", eventDetail.idEvent)
            data.putString("strLeague", eventDetail.strLeague)
            data.putString("strEvent", eventDetail.strEvent)
            val calendar = Calendar.getInstance()
            calendar.time = Date()
            calendar.add(Calendar.MINUTE, 1)
            val duration = (calendar.time.time - Date().time) / 1000


            uploadWorkRequest.setInitialDelay(duration, TimeUnit.SECONDS)
            uploadWorkRequest.setInputData(data.build())
            val workRequest = uploadWorkRequest.build()
            WorkManager.getInstance(context).enqueue(workRequest)
            val workRequestID = workRequest.id.toString()

            if (eventData.isNotEmpty()) {
                for (i in eventData.indices) {
                    setEventDatabase(eventDetail, workRequestID, eventData[i].isFavorite)

                }

            } else {
                setEventDatabase(eventDetail, workRequestID, eventDetail.isFavorite)

            }

        } else {
            for (i in eventData.indices) {
                WorkManager.getInstance(context).cancelWorkById(UUID.fromString(eventData[i].isAlarm))
                if (eventData[i].isFavorite != null) {
                    setEventDatabase(eventDetail, null, eventData[i].isFavorite)

                } else { userRepository.deleteEvent(eventDetail.idEvent) }

            }

        }

    }

    fun setFavorite(isFavorite: Boolean, eventDetail: EventDetail) {
        val eventData = userRepository.readEvent(eventDetail.idEvent)

        if (isFavorite) {
            if (eventData.isNotEmpty()) {
                for (i in eventData.indices) {
                    setEventDatabase(eventDetail, eventData[i].isAlarm, true)

                }

            } else {
                setEventDatabase(eventDetail, eventDetail.isAlarm, true)

            }

        } else {
            for (i in eventData.indices) {
                if (eventData[i].isAlarm != null) {
                    setEventDatabase(eventDetail, eventData[i].isAlarm, null)

                } else { userRepository.deleteEvent(eventDetail.idEvent) }

            }

        }

    }

    fun getAlarmVisibility(eventDetail: EventDetail): Int {
        val sourceDate = eventDetail.dateEvent
        val nowDate = Date().time
        return if (sourceDate != null) {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd")
            val eventDate = inputFormat.parse(sourceDate).time
            if (eventDate > nowDate) {
                View.VISIBLE

            } else {
                View.GONE

            }

        } else {
            View.GONE

        }

    }

    private fun setEventDatabase(eventDetail: EventDetail, isAlarm: String?, isFavorite: Boolean?) {
        userRepository.createEvent(
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
                isAlarm,
                isFavorite
            )
        )

    }

}
