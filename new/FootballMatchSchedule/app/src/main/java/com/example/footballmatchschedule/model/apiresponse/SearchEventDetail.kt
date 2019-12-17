package com.example.footballmatchschedule.model.apiresponse

data class SearchEventDetail(
    var dateEvent: String = "",
    var dateEventLocal: String = "",
    var idAwayTeam: Any = Any(),
    var idEvent: String = "",
    var idHomeTeam: Any = Any(),
    var idLeague: String = "",
    var idSoccerXML: Any = Any(),
    var intAwayScore: Any = Any(),
    var intAwayShots: Any = Any(),
    var intHomeScore: Any = Any(),
    var intHomeShots: Any = Any(),
    var intRound: String = "",
    var intSpectators: Any = Any(),
    var strAwayFormation: Any = Any(),
    var strAwayGoalDetails: Any = Any(),
    var strAwayLineupDefense: Any = Any(),
    var strAwayLineupForward: Any = Any(),
    var strAwayLineupGoalkeeper: Any = Any(),
    var strAwayLineupMidfield: Any = Any(),
    var strAwayLineupSubstitutes: Any = Any(),
    var strAwayRedCards: Any = Any(),
    var strAwayTeam: Any = Any(),
    var strAwayYellowCards: Any = Any(),
    var strBanner: Any = Any(),
    var strCircuit: String = "",
    var strCity: String = "",
    var strCountry: String = "",
    var strDate: Any = Any(),
    var strDescriptionEN: String = "",
    var strEvent: String = "",
    var strEventAlternate: Any = Any(),
    var strFanart: Any = Any(),
    var strFilename: String = "",
    var strHomeFormation: Any = Any(),
    var strHomeGoalDetails: Any = Any(),
    var strHomeLineupDefense: Any = Any(),
    var strHomeLineupForward: Any = Any(),
    var strHomeLineupGoalkeeper: Any = Any(),
    var strHomeLineupMidfield: Any = Any(),
    var strHomeLineupSubstitutes: Any = Any(),
    var strHomeRedCards: Any = Any(),
    var strHomeTeam: Any = Any(),
    var strHomeYellowCards: Any = Any(),
    var strLeague: String = "",
    var strLocked: String = "",
    var strMap: Any = Any(),
    var strPoster: Any = Any(),
    var strResult: String = "",
    var strSeason: String = "",
    var strSport: String = "",
    var strTVStation: Any = Any(),
    var strThumb: Any = Any(),
    var strTime: String = "",
    var strTimeLocal: String = "",
    var strTweet1: String = "",
    var strTweet2: String = "",
    var strTweet3: String = "",
    var strVideo: String = ""
)