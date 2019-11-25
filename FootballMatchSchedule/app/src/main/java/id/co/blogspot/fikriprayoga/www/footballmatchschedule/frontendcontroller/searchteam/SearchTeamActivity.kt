package id.co.blogspot.fikriprayoga.www.footballmatchschedule.frontendcontroller.searchteam

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.CoordinatorLayout.Behavior.getTag
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.gson.Gson
import id.co.blogspot.fikriprayoga.www.footballmatchschedule.R
import id.co.blogspot.fikriprayoga.www.footballmatchschedule.backendcontroller.MainHandler
import id.co.blogspot.fikriprayoga.www.footballmatchschedule.backendcontroller.recyclerviewhandler.STAAdapter
import id.co.blogspot.fikriprayoga.www.footballmatchschedule.frontendcontroller.MainActivity
import id.co.blogspot.fikriprayoga.www.footballmatchschedule.frontendcontroller.TeamDetailFragment
import kotlinx.android.synthetic.main.activity_search_team.*
import org.jetbrains.anko.toast
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.support.v4.app.NotificationCompat.getExtras
import id.co.blogspot.fikriprayoga.www.footballmatchschedule.backendcontroller.database
import org.jetbrains.anko.db.*


class SearchTeamActivity : AppCompatActivity() {
    lateinit var staAdapter: STAAdapter
    var staObjects: MutableList<STAAdapter.STAObject> = ArrayList()
    lateinit var staObject: STAAdapter.STAObject
    val mException = "{\n" +
            "    \"teams\": [\n" +
            "        {\n" +
            "            \"idTeam\": \"133739\",\n" +
            "            \"idSoccerXML\": \"155\",\n" +
            "            \"intLoved\": \"6\",\n" +
            "            \"strTeam\": \"Barcelona\",\n" +
            "            \"strTeamShort\": null,\n" +
            "            \"strAlternate\": \"\",\n" +
            "            \"intFormedYear\": \"1899\",\n" +
            "            \"strSport\": \"Soccer\",\n" +
            "            \"strLeague\": \"Spanish La Liga\",\n" +
            "            \"idLeague\": \"4335\",\n" +
            "            \"strDivision\": null,\n" +
            "            \"strManager\": \"Ernesto Valverde\",\n" +
            "            \"strStadium\": \"Camp Nou\",\n" +
            "            \"strKeywords\": \"\",\n" +
            "            \"strRSS\": \"\",\n" +
            "            \"strStadiumThumb\": \"https://www.thesportsdb.com/images/media/team/stadium/rsvstp1420328111.jpg\",\n" +
            "            \"strStadiumDescription\": \"Camp Nou (Catalan pronunciation: , \\\"new field\\\", often incorrectly referred to as the \\\"Nou Camp\\\" in English) is the home stadium of FC Barcelona since its completion in 1957.\\r\\n\\r\\nWith a seating capacity of 99,354, it is the largest stadium in Spain and Europe, and the second largest association football stadium in the world in terms of capacity. It has hosted two European Cup/Champions League finals in 1989 and 1999, five matches including the opening game of the 1982 FIFA World Cup and the football competition final at the 1992 Summer Olympics.\",\n" +
            "            \"strStadiumLocation\": \"Barcelona\",\n" +
            "            \"intStadiumCapacity\": \"99354\",\n" +
            "            \"strWebsite\": \"www.fcbarcelona.com\",\n" +
            "            \"strFacebook\": \"www.facebook.com/fcbarcelona\",\n" +
            "            \"strTwitter\": \"twitter.com/fcbarcelona\",\n" +
            "            \"strInstagram\": \"instagram.com/fcbarcelona\",\n" +
            "            \"strDescriptionEN\": \"Futbol Club Barcelona, also known as Barcelona and familiarly as Barça, is a professional football club, based in Barcelona, Catalonia, Spain.\\r\\n\\r\\nFounded in 1899 by a group of Swiss, English and Catalan footballers led by Joan Gamper, the club has become a symbol of Catalan culture and Catalanism, hence the motto \\\"Més que un club\\\" (More than a club). Unlike many other football clubs, the supporters own and operate Barcelona. It is the second most valuable sports team in the world, worth \$3.2 billion, and the world's second-richest football club in terms of revenue, with an annual turnover of \$613 million. The official Barcelona anthem is the \\\"Cant del Barça\\\", written by Jaume Picas and Josep Maria Espinàs.\\r\\n\\r\\nBarcelona is the joint most successful club in Spain, in terms of overall official titles won (80). It has won 22 La Liga, 26 Copa del Rey, 11 Supercopa de España, 2 Copa Eva Duarte and 2 Copa de la Liga trophies, as well as being the record holder for the latter four competitions. In international club football, Barcelona has won four UEFA Champions League, a record four UEFA Cup Winners' Cup, four UEFA Super Cup, a record three Inter-Cities Fairs Cup and a record two FIFA Club World Cup trophies. Barcelona was ranked first in the IFFHS Club World Ranking for 1997, 2009, 2011 and 2012 and currently occupies the second position on the UEFA club rankings. The club has a long-standing rivalry with Real Madrid; matches between the two teams are referred to as \\\"El Clásico\\\".\\r\\n\\r\\nBarcelona is one of the most supported teams in the world, and has the largest social media following in the world among sports teams. Barcelona's players have won a record number of Ballon d'Or awards (10), as well as a record number of FIFA World Player of the Year awards (7). In 2010, the club created history when three players who came through its youth academy (Messi, Iniesta & Xavi) were chosen as the three best players in the world, having bagged the top spots at the FIFA Ballon d'Or, an unprecedented feat for players from the same football school.\\r\\n\\r\\nBarcelona was one of the founding members of La Liga, and is one of three clubs which have never been relegated from the top division, along with Athletic Bilbao and Real Madrid. In 2009, Barcelona became the first Spanish club to win the continental treble consisting of La Liga, Copa del Rey, and the Champions League. That same year, it also became the first football club ever to win six out of six competitions in a single year, thus completing the sextuple, comprising the aforementioned treble and the Spanish Super Cup, UEFA Super Cup and FIFA Club World Cup. In 2011, the Blaugrana again became European champions and won a total of five titles, missing out only on the Copa del Rey (in which they finished runners-up). This Barcelona team, which reached a record six consecutive Champions League semi-finals and won 14 trophies in just four years under Guardiola's charge, is considered by some managers, players and experts to be the greatest team of all time.\",\n" +
            "            \"strDescriptionDE\": null,\n" +
            "            \"strDescriptionFR\": null,\n" +
            "            \"strDescriptionCN\": null,\n" +
            "            \"strDescriptionIT\": null,\n" +
            "            \"strDescriptionJP\": null,\n" +
            "            \"strDescriptionRU\": null,\n" +
            "            \"strDescriptionES\": \"El Fútbol Club Barcelona es una entidad polideportiva de Barcelona (España). Fue fundado como club de fútbol el 29 de noviembre de 1899 por doce jóvenes futbolistas aficionados, liderados por el suizo Hans Gamper. El F. C. Barcelona es conocido popularmente como Barça (abreviación de la pronunciación de Barcelona en catalán central) y sus hinchas como culés6 (pronunciación del catalán culers); también, y en referencia a sus colores, se utiliza el término azulgranas, que procede del catalán blaugranes, tal como aparece en su himno, el Cant del Barça, donde en su segunda línea se dice «Som la gent blaugrana» (en castellano, \\\"Somos la gente azulgrana\\\"). A nivel institucional, el Fútbol Club Barcelona denomina a sus aficionados como barcelonistas y tiene a su servicio, para atender a socios, simpatizantes y público en general, la OAB, sigla que significa Oficina de Atención al Barcelonista, donde quien lo solicita es atendido en los idiomas oficiales del club, que son el catalán, el castellano y el inglés.\\r\\n\\r\\nOtro de sus hechos distintivos es su masa social de socios y aficionados. El club alcanzó en 2011 los 180 000 socios,15 16 lo que lo convierte en el segundo club de fútbol con más asociados del mundo (el primero es el SL Benfica), seguido por el Manchester United.17 Existen, además, más de 1200 peñas barcelonistas repartidas por todo el mundo. Cabe anotar que el F. C. Barcelona es uno de los cuatro únicos clubes profesionales de España (junto a Real Madrid, Athletic Club y Osasuna) que no es sociedad anónima, de manera que la propiedad del club recae en sus socios.\\r\\n\\r\\nEs uno de los equipos más populares de su país –el segundo con la mayor cantidad de aficionados en España con el 25,7 % del total de simpatizantes al fútbol de acuerdo con un estudio realizado en el mes de mayo de 2007 por el Centro de Investigaciones Sociológicas (CIS) y del mundo. Es el equipo con más títulos nacionales de España, y en Europa,21 22 contando en sus vitrinas a nivel nacional con 22 Ligas, 26 Copas, 2 Copas de la Liga, 11 Supercopas y 3 Copas Eva Duarte y a nivel internacional con 2 Copa Mundial de Clubes, 4 Copas de Europa, 4 Recopas de Europa, 3 Copa de Ferias y 4 Supercopas de Europa. El F. C. Barcelona ha ganado 81 títulos oficiales, lo cual le convierte en el equipo con más títulos en España. Sus dos rivales históricos son el RCD Español, contra el que disputa el derbi catalán, y el Real Madrid, con quien se enfrenta en «El Clásico», siendo este uno de los encuentros de mayor rivalidad e interés del fútbol mundial.\\r\\n\\r\\nSegún las estadísticas que realiza el IFFHS, el F. C. Barcelona es el mejor equipo de fútbol europeo y mundial de la primera década del siglo XXI. Con fecha a 31 de diciembre del 2009, el F. C. Barcelona lidera con 807 puntos la clasificación histórica del ranking mundial de clubes que realizaba hasta finales de 2009 la IFFHS, con una diferencia de 81 puntos sobre el segundo en el ranking (Manchester United).28 Cabe destacar también que según la clasificación anual de clubes que realiza la IFFHS, en 1997, 2009 y 2011 fue designado como el mejor equipo del fútbol mundial, siendo también el club que más veces ha encabezado dicha clasificación. Es además el equipo de fútbol que más veces ha figurado en los podios del FIFA World Player (15) y del Balón de Oro (20).32\\r\\n\\r\\nEn 2009 el club ganó todas las competiciones que disputó (Liga, Copa, Supercopa de España, Liga de Campeones, Supercopa de Europa y Copa Mundial de Clubes), pasando a la historia por ser el primer equipo del mundo en lograr un «sextete», al ganar seis títulos oficiales en un mismo año.\",\n" +
            "            \"strDescriptionPT\": null,\n" +
            "            \"strDescriptionSE\": null,\n" +
            "            \"strDescriptionNL\": null,\n" +
            "            \"strDescriptionHU\": null,\n" +
            "            \"strDescriptionNO\": null,\n" +
            "            \"strDescriptionIL\": null,\n" +
            "            \"strDescriptionPL\": null,\n" +
            "            \"strGender\": \"Male\",\n" +
            "            \"strCountry\": \"Spain\",\n" +
            "            \"strTeamBadge\": \"https://www.thesportsdb.com/images/media/team/badge/xqwpup1473502878.png\",\n" +
            "            \"strTeamJersey\": \"https://www.thesportsdb.com/images/media/team/jersey/qpwqyv1472752290.png\",\n" +
            "            \"strTeamLogo\": \"https://www.thesportsdb.com/images/media/team/logo/vsqdh71536400613.png\",\n" +
            "            \"strTeamFanart1\": \"https://www.thesportsdb.com/images/media/team/fanart/swqxry1424485326.jpg\",\n" +
            "            \"strTeamFanart2\": \"https://www.thesportsdb.com/images/media/team/fanart/wrxvsw1433680782.jpg\",\n" +
            "            \"strTeamFanart3\": \"https://www.thesportsdb.com/images/media/team/fanart/yrustw1424184467.jpg\",\n" +
            "            \"strTeamFanart4\": \"https://www.thesportsdb.com/images/media/team/fanart/yvqprv1424485475.jpg\",\n" +
            "            \"strTeamBanner\": \"https://www.thesportsdb.com/images/media/team/banner/vytuur1420329853.jpg\",\n" +
            "            \"strYoutube\": \"www.youtube.com/user/fcbarcelona\",\n" +
            "            \"strLocked\": \"unlocked\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"idTeam\": \"134487\",\n" +
            "            \"idSoccerXML\": \"947\",\n" +
            "            \"intLoved\": null,\n" +
            "            \"strTeam\": \"Barcelona B\",\n" +
            "            \"strTeamShort\": null,\n" +
            "            \"strAlternate\": \"Barça B\",\n" +
            "            \"intFormedYear\": \"1970\",\n" +
            "            \"strSport\": \"Soccer\",\n" +
            "            \"strLeague\": \"_No League\",\n" +
            "            \"idLeague\": \"4367\",\n" +
            "            \"strDivision\": null,\n" +
            "            \"strManager\": \"Gerard López\",\n" +
            "            \"strStadium\": \"Mini Estadi\",\n" +
            "            \"strKeywords\": \"\",\n" +
            "            \"strRSS\": \"\",\n" +
            "            \"strStadiumThumb\": null,\n" +
            "            \"strStadiumDescription\": \"Mini Estadi (Catalan pronunciation: , meaning in English \\\"Mini Stadium\\\") is a football stadium located in Barcelona, Catalonia, Spain. The 15,276-seat stadium is situated across from Camp Nou, the home stadium of FC Barcelona.\\r\\n\\r\\nThe stadium was home to FC Barcelona C until July 2007, when they disbanded. It has also been home to the Barcelona Dragons, an NFL Europe American football team, until they were disbanded in 2003.\\r\\n\\r\\nThe stadium is currently home to FC Barcelona B, FCB's reserve team, as well as FC Barcelona's women's team, and Juvenil A.\\r\\n\\r\\nIt occasionally hosts the national team of Andorra as well.\\r\\n\\r\\nQueen performed at the stadium during their Magic Tour on 1 August 1986.\\r\\n\\r\\nDavid Bowie performed, on two consecutive nights, at the stadium during his Glass Spider Tour on 7–8 July 1987.\\r\\n\\r\\nElton John performed at the stadium during his One Tour on 21 July 1992. The concert was recorded and released on VHS and DVD.\",\n" +
            "            \"strStadiumLocation\": \"Barcelona\",\n" +
            "            \"intStadiumCapacity\": \"15276\",\n" +
            "            \"strWebsite\": \"www.fcbarcelona.com/football/barca-b\",\n" +
            "            \"strFacebook\": \"\",\n" +
            "            \"strTwitter\": \"\",\n" +
            "            \"strInstagram\": \"\",\n" +
            "            \"strDescriptionEN\": \"Futbol Club Barcelona \\\"B\\\" is a Spanish football team based in Barcelona, in the autonomous community of Catalonia. Founded in 1970 as FC Barcelona Atlètic, it is the reserve team of FC Barcelona, and currently plays in Segunda División, holding home matches at Mini Estadi.\\r\\n\\r\\nReserve teams in Spain play in the same league system as the senior team, rather than in a reserve team league. They must play at least one level below their main side, however, so Barcelona B is ineligible for promotion to La Liga and cannot play in the Copa del Rey.\\r\\n\\r\\nFounded on 1 August 1934 as Societat Esportiva Industrial Espanya, the club was originally the sports team of the factory with the same name, and its shirt featured blue and white vertical stripes. The company was owned by the family of Josep Antoni de Albert, who was briefly president of FC Barcelona in 1943; during Albert's presidency the club, now known as Club Deportivo Espanya Industrial, became Barcelona's reserve team and began to play home games at Camp de Les Corts.\\r\\n\\r\\nInitially, Industrial played in the local regional leagues but, in 1950, it was promoted to Tercera División, reaching Segunda División two years later. In 1953 the club finished as runners-up in both the league and the promotion play-off but, being a nursery club of Barcelona, it was unable to move up a division.\",\n" +
            "            \"strDescriptionDE\": null,\n" +
            "            \"strDescriptionFR\": null,\n" +
            "            \"strDescriptionCN\": null,\n" +
            "            \"strDescriptionIT\": null,\n" +
            "            \"strDescriptionJP\": null,\n" +
            "            \"strDescriptionRU\": null,\n" +
            "            \"strDescriptionES\": \"El Fútbol Club Barcelona \\\"B\\\" es un club de fútbol de España, de la ciudad de Barcelona, en Cataluña, filial del Fútbol Club Barcelona. Fue fundado como Futbol Club Barcelona Atlètic en 1970, con la fusión del Club Deportivo Condal y el Atlético Cataluña Club de Fútbol. Actualmente milita en la Segunda División de España.\\r\\n\\r\\nPor las filas del F. C. Barcelona \\\"B\\\" han pasado los mejores jugadores que han llegado al primer equipo del F. C. Barcelona procedentes de la cantera barcelonista: Carrasco, Calderé, Amor, Ferrer, Guardiola, Sergi, Andrés Iniesta, Carles Puyol, Víctor Valdés o Xavi Hernández, así como futbolistas que han desarrollado el grueso de su carrera en otros equipos importantes como Nayim, Luis García, Albert Luque, Sergio García, Pepe Reina o Thiago Alcántara. Una de las últimas estrellas surgidas del Barcelona B es el argentino Lionel Messi, que en la temporada 2004-2005 debutó en el primer equipo, entrenado por Frank Rijkaard. El hispano-danés Thomas Christiansen y los españoles Martín Montoya y Munir El Haddadi son los únicos jugadores que han alcanzado la internacionalidad absoluta con la selección española siendo parte de la plantilla del Barcelona \\\"B\\\".\\r\\n\\r\\nActualmente destacan jugadores como Munir El Haddadi, Jean Marie Dongou, Sergi Samper, Adama Traoré o Sandro Ramírez. Es posible que muchos de los jugadores del Barça B terminen jugando en el primer equipo debido a la filosofía de cantera que está implantada en la entidad azulgrana.\",\n" +
            "            \"strDescriptionPT\": null,\n" +
            "            \"strDescriptionSE\": null,\n" +
            "            \"strDescriptionNL\": null,\n" +
            "            \"strDescriptionHU\": null,\n" +
            "            \"strDescriptionNO\": null,\n" +
            "            \"strDescriptionIL\": null,\n" +
            "            \"strDescriptionPL\": null,\n" +
            "            \"strGender\": \"Male\",\n" +
            "            \"strCountry\": \"Spain\",\n" +
            "            \"strTeamBadge\": \"https://www.thesportsdb.com/images/media/team/badge/rwxxuw1420415212.png\",\n" +
            "            \"strTeamJersey\": \"https://www.thesportsdb.com/images/media/team/jersey/uuputy1423786742.png\",\n" +
            "            \"strTeamLogo\": null,\n" +
            "            \"strTeamFanart1\": null,\n" +
            "            \"strTeamFanart2\": null,\n" +
            "            \"strTeamFanart3\": null,\n" +
            "            \"strTeamFanart4\": null,\n" +
            "            \"strTeamBanner\": null,\n" +
            "            \"strYoutube\": \"\",\n" +
            "            \"strLocked\": \"unlocked\"\n" +
            "        }\n" +
            "    ]\n" +
            "}"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_team)

        val extras = intent.extras
        if (extras != null) {
            showList(JSONObject(extras.getString("Data")).getJSONArray("teams"))

        } else {
            showList(JSONObject(mException).getJSONArray("teams"))

        }

    }

    fun showList(data: JSONArray) {
        staAdapter = STAAdapter(this, staObjects, obj)
        val mLayoutManager = LinearLayoutManager(this)
        recyclerView_activity_search_team_1_1.layoutManager = mLayoutManager
        recyclerView_activity_search_team_1_1.itemAnimator = DefaultItemAnimator()
        recyclerView_activity_search_team_1_1.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
        recyclerView_activity_search_team_1_1.adapter = staAdapter

        staObjects.clear()

        for (i in 0 until data.length()) {
            staObject = STAAdapter.STAObject(data.getJSONObject(i))
            staObjects.add(staObject)

        }

        staAdapter.notifyDataSetChanged()

    }

    fun updateList() {


    }

    val obj = object : STAAdapter.STAListener {
        override fun item(itemData: Bundle) {
            cardView_activity_search_team_2.visibility = View.VISIBLE
            Log.d("DICODING", "SearchTeamActivity/69 : " + itemData.toString())
            SearchTeamPresenter(obj2).getPlayer(JSONObject(itemData.getString("Data")).getString("idTeam").toInt(), itemData)

        }
    }

    val obj2 = object : SearchTeamView {
        override fun onFailure(searchTeamModel: SearchTeamModel) {
            cardView_activity_search_team_2.visibility = View.GONE
            Toast.makeText(applicationContext, searchTeamModel.t.toString(), Toast.LENGTH_SHORT).show()
            Log.e("Dicoding", searchTeamModel.t.toString())

        }

        override fun onResponse(searchTeamModel: SearchTeamModel) {
            cardView_activity_search_team_2.visibility = View.GONE
            Log.d("DICODING", "SearchTeamActivity/84 : " + Gson().toJson(searchTeamModel.response))
            if (searchTeamModel.response != "") {
                searchTeamModel.itemData!!.putString("Player", Gson().toJson(searchTeamModel.response))
                searchTeamModel.itemData!!.putBoolean("isFromSearchTeamActivity", true)
                val mFrag = TeamDetailFragment()
                mFrag.arguments = searchTeamModel.itemData
                supportFragmentManager
                        .beginTransaction()
                        .addToBackStack(null) // detect addbackstack
                        .add(R.id.frameLayout_activity_search_team_1, mFrag, "TeamDetailFragment")
                        .commitAllowingStateLoss()

            } else {
                Toast.makeText(applicationContext, "Response is null", Toast.LENGTH_SHORT).show()

            }

        }
    }

    fun createFavoriteTeam(LeagueID: String, TeamID: String, Data: String) {
        database.use {
            insert("FavoriteTeam", "LeagueID" to LeagueID, "TeamID" to TeamID, "Data" to Data)

        }

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

    fun deleteFavoriteTeam(LeagueID: String, TeamID: String) {
        database.use {
            delete("FavoriteTeam", "LeagueID = {LeagueID} AND TeamID = {TeamID}", "LeagueID" to LeagueID, "TeamID" to TeamID)
        }

    }

}
