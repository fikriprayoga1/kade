package com.example.footballmatchschedule.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.footballmatchschedule.R
import com.example.footballmatchschedule.other.helper.Tag
import com.example.footballmatchschedule.viewmodel.MainActivityViewModel
import com.example.footballmatchschedule.viewmodel.SearchEventViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)
        startLoading(null)
        GlobalScope.launch(Dispatchers.Main) {
            withContext(Dispatchers.Default) { viewModel.init(applicationContext) }
            stopLoading(null)
            changeFragment0(R.id.frameLayout_activity_main_1, HomeFragment())

        }

    }

    fun changeFragment0(layout: Int, fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(layout, fragment)
            .commitAllowingStateLoss()

    }

    fun changeFragment1(layout: Int, fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .addToBackStack(null) // detect addbackstack
            .replace(layout, fragment)
            .commitAllowingStateLoss()

    }

    fun startLoading(state: String?) {
        Log.d(Tag().tag, state + "startLoading")
        cardView_activity_main_2.visibility = View.VISIBLE
        GlobalScope.launch(Dispatchers.Main) {
            val loadingQueue = withContext(Dispatchers.Default) {
                viewModel.addLoading()

            }

            if (loadingQueue == viewModel.getQueueInit()) { cardView_activity_main_2.visibility = View.GONE }

        }

    }

    fun stopLoading(state: String?) {
        Log.d(Tag().tag, state + "stopLoading")
        GlobalScope.launch(Dispatchers.Main) {
            val loadingQueue = withContext(Dispatchers.Default) {
                viewModel.reduceLoading()

            }

            if (loadingQueue == viewModel.getQueueInit()) { cardView_activity_main_2.visibility = View.GONE }

        }

    }

    fun popUp(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()

    }
}
