package id.co.blogspot.fikriprayoga.www.footballmatchschedule.frontendcontroller.searchteam

import android.os.Bundle
import android.telecom.Call
import okhttp3.Response
import org.json.JSONObject

class SearchTeamModel {
    var t: Throwable? = null
    var response: String = ""
    var itemData: Bundle? = null

    constructor(t: Throwable) {
        this.t = t

    }

    constructor(response: String, itemData: Bundle) {
        this.response = response
        this.itemData = itemData

    }

}