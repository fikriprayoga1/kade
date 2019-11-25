package id.co.blogspot.fikriprayoga.www.footballmatchschedule.frontendcontroller.searchteam

interface SearchTeamView {
    fun onFailure(searchTeamModel: SearchTeamModel)

    fun onResponse(searchTeamModel: SearchTeamModel)

}