package id.co.blogspot.fikriprayoga.www.footballmatchschedule.frontendcontroller


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso

import id.co.blogspot.fikriprayoga.www.footballmatchschedule.R
import kotlinx.android.synthetic.main.fragment_player_detail.*
import kotlinx.android.synthetic.main.fragment_team_detail.*
import org.json.JSONObject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class PlayerDetailFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_player_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val bundle = this@PlayerDetailFragment.arguments
        if (bundle != null) {
            val playerDetail = JSONObject(bundle.getString("Data"))

            try {
                Picasso.get()
                        .load(playerDetail.getString("strCutout"))
                        .resize(100, 100)
                        .into(imageView_fragment_player_detail_1_1)
            } catch (e: java.lang.Exception) {}

            textView_fragment_player_detail_1_2.text = playerDetail.getString("strPlayer")
            textView_fragment_player_detail_1_5.text = playerDetail.getString("strWeight")
            textView_fragment_player_detail_1_6.text = playerDetail.getString("strHeight")
            textView_fragment_player_detail_1_7.text = playerDetail.getString("strPosition")
            textView_fragment_player_detail_1_8.text = playerDetail.getString("strDescriptionEN")

        }

    }


}
