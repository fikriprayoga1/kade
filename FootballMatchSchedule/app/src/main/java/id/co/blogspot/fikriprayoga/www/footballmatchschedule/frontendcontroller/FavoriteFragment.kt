package id.co.blogspot.fikriprayoga.www.footballmatchschedule.frontendcontroller


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import id.co.blogspot.fikriprayoga.www.footballmatchschedule.R
import kotlinx.android.synthetic.main.fragment_event.*
import id.co.blogspot.fikriprayoga.www.footballmatchschedule.backendcontroller.TabLayoutAdapter
import kotlinx.android.synthetic.main.fragment_favorite.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class FavoriteFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val adapter = TabLayoutAdapter(childFragmentManager)
        adapter.addFragment(FavoriteEventFragment(), "Favorite Event")
        adapter.addFragment(FavoriteTeamFragment(), "Favorite Team")
        viewPager_fragment_favorite_2.adapter = adapter
        tabLayout_fragment_favorite_1.setupWithViewPager(viewPager_fragment_favorite_2)

    }

}
