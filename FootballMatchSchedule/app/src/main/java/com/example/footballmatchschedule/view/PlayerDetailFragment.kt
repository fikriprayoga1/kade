package com.example.footballmatchschedule.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import com.example.footballmatchschedule.R
import com.example.footballmatchschedule.viewmodel.PlayerDetailViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.player_detail_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PlayerDetailFragment : Fragment() {

    companion object {
        fun newInstance() = PlayerDetailFragment()
    }

    private lateinit var viewModel: PlayerDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.player_detail_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(PlayerDetailViewModel::class.java)

        lifecycleScope.launchWhenStarted {
            if (lifecycle.currentState >= Lifecycle.State.STARTED) {
                (activity as MainActivity).startLoading()

                val dataSource = (activity as MainActivity).viewModel.getSelectedPlayer()

                Picasso.get()
                    .load(dataSource.strThumb)
                    .into(imageView_player_detail_fragment_1_1)
                textView_player_detail_fragment_1_2.text = dataSource.strPlayer
                textView_player_detail_fragment_1_5.text = dataSource.strWeight
                val mHeightSource = dataSource.strHeight
                if (mHeightSource != null) {
                    val mHeightSplit = mHeightSource.split(" ")
                    textView_player_detail_fragment_1_6.text = mHeightSplit[0]

                }
                textView_player_detail_fragment_1_7.text = dataSource.strPosition
                justifiedTextView_player_detail_fragment_1_8.text = dataSource.strDescriptionEN

                (activity as MainActivity).stopLoading()

            }

        }

    }

}
