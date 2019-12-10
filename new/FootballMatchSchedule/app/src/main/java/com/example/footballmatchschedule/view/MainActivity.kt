package com.example.footballmatchschedule.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.footballmatchschedule.R
import com.example.footballmatchschedule.viewmodel.MainActivityViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)

    }

    override fun onResume() {
        super.onResume()
        startLoading(viewModel.getUIScope())
        viewModel.getUIScope().launch {
            withContext(Dispatchers.Default) { viewModel.init() }
            stopLoading(viewModel.getUIScope())
            changeFragment0(R.id.frameLayout_activity_main_1, HomeFragment())

        }

    }

    override fun onPause() {
        viewModel.getJob().cancel()
        super.onPause()
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

    fun startLoading(uiScope: CoroutineScope) {
        uiScope.launch {
            val loadingQueue = withContext(Dispatchers.Default) { viewModel.addLoading() }
            processLoading(loadingQueue)

        }

    }

    fun stopLoading(uiScope: CoroutineScope) {
        uiScope.launch {
            val loadingQueue = withContext(Dispatchers.Default) { viewModel.reduceLoading() }
            processLoading(loadingQueue)

        }

    }

    private fun processLoading(loadingQueue: Short) {
        if (loadingQueue == viewModel.getQueueInit()) {
            cardView_activity_main_2.visibility = View.GONE
        } else {
            cardView_activity_main_2.visibility = View.VISIBLE
        }

    }

    fun popUp(message: String) {
        runOnUiThread { Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show() }

    }

}
