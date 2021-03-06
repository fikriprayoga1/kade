package com.example.footballmatchschedule

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import com.example.footballmatchschedule.util.helper.FMSHelper
import com.example.footballmatchschedule.view.HomeFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lifecycleScope.launchWhenStarted {
            if (lifecycle.currentState >= Lifecycle.State.STARTED) {
                startLoading()

                withContext(Dispatchers.Default) { FMSHelper.init(this@MainActivity) }

                changeFragment0(R.id.frameLayout_activity_main_1,
                    HomeFragment()
                )

                stopLoading()

            }

        }

    }

    fun changeFragment0(layout: Int, fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(layout, fragment)
            .commitAllowingStateLoss()

    }

    fun changeFragment2(layout: Int, fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .addToBackStack(null)
            .add(layout, fragment)
            .commitAllowingStateLoss()

    }

    fun startLoading() {
        cardView_activity_main_2.visibility = View.VISIBLE

    }

    fun stopLoading() {
        cardView_activity_main_2.visibility = View.GONE

    }

    fun popUp(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()

    }

    fun exitKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus!!.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)

    }

}
