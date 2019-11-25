package id.co.blogspot.fikriprayoga.www.footballmatchschedule.frontendcontroller


import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.co.blogspot.fikriprayoga.www.footballmatchschedule.backendcontroller.TabLayoutAdapter

import id.co.blogspot.fikriprayoga.www.footballmatchschedule.R
import kotlinx.android.synthetic.main.fragment_home.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class HomeFragment : Fragment() {
    override fun onAttach(context: Context?) {
        super.onAttach(context)

        Log.d((activity as MainActivity).getTag(), "HomeFragment/30 : onAttach")

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        Log.d((activity as MainActivity).getTag(), "HomeFragment/37 : onCreateView")
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    @SuppressLint("ResourceAsColor")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d((activity as MainActivity).getTag(), "HomeFragment/43 : onViewCreated")

        val adapter = TabLayoutAdapter(childFragmentManager)
        adapter.addFragment(EventFragment(), "Event")
        adapter.addFragment(TeamFragment(), "Team")
        adapter.addFragment(FavoriteFragment(), "Favorite")
        adapter.addFragment(AlarmFragment(), "Alarm")
        viewPager_fragment_home_2.adapter = adapter

        tabLayout_fragment_home_3.setupWithViewPager(viewPager_fragment_home_2)

        toolbar_fragment_home_1.title = "Football Match Schedule"
        toolbar_fragment_home_1.setTitleTextColor(Color.parseColor("#FFFFFF"))

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        Log.d((activity as MainActivity).getTag(), "HomeFragment/56 : onCreate")

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        Log.d((activity as MainActivity).getTag(), "HomeFragment/63 : onActivityCreated")

    }

    override fun onStart() {
        super.onStart()

        Log.d((activity as MainActivity).getTag(), "HomeFragment/70 : onStart")

    }

    override fun onResume() {
        super.onResume()

        Log.d((activity as MainActivity).getTag(), "HomeFragment/77 : onResume")

    }

    override fun onPause() {
        super.onPause()

        Log.d((activity as MainActivity).getTag(), "HomeFragment/84 : onPause")

    }

    override fun onStop() {
        super.onStop()

        Log.d((activity as MainActivity).getTag(), "HomeFragment/91 : onStop")

    }

    override fun onDestroyView() {
        super.onDestroyView()

        Log.d((activity as MainActivity).getTag(), "HomeFragment/98 : onDestroyView")

    }

    override fun onDestroy() {
        super.onDestroy()

        Log.d((activity as MainActivity).getTag(), "HomeFragment/105 : onDestroy")

    }

    override fun onDetach() {
        super.onDetach()

        Log.d((activity as MainActivity).getTag(), "HomeFragment/112 : onDetach")

    }

}
