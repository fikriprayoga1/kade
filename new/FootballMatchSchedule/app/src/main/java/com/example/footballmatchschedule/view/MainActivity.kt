package com.example.footballmatchschedule.view

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import com.example.footballmatchschedule.R
import com.example.footballmatchschedule.other.helper.TagHelper
import com.example.footballmatchschedule.viewmodel.MainActivityViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)

        val thisContext = this
        lifecycleScope.launchWhenStarted {
            if (lifecycle.currentState >= Lifecycle.State.STARTED) {
                val loadingStatus0 =
                    withContext(Dispatchers.Default) { viewModel.updateLoading(true) }
                updateLoading(
                    loadingStatus0,
                    this.javaClass.name,
                    Thread.currentThread().stackTrace[2].lineNumber,
                    "start"
                )

                withContext(Dispatchers.Default) { viewModel.init(thisContext) }

                changeFragment0(R.id.frameLayout_activity_main_1, HomeFragment())

                val loadingStatus1 =
                    withContext(Dispatchers.Default) { viewModel.updateLoading(false) }
                updateLoading(
                    loadingStatus1,
                    this.javaClass.name,
                    Thread.currentThread().stackTrace[2].lineNumber,
                    "stop"
                )

            }

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

    fun changeFragment2(layout: Int, fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .addToBackStack(null)
            .add(layout, fragment)
            .commitAllowingStateLoss()

    }

    fun changeFragment3(layout: Int, fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .addToBackStack(null)
            .replace(layout, fragment)
            .commitAllowingStateLoss()

    }

    fun updateLoading(viewStatus: Int, className: String, line: Int, description: String?) {
        TagHelper().writeTag(className, line, description)
        cardView_activity_main_2.visibility = viewStatus

    }

    fun popUp(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()

    }

    fun exitKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus!!.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)

    }

}
