package com.example.footballmatchschedule.other.recyclerviewadapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.footballmatchschedule.R
import com.example.footballmatchschedule.model.apiresponse.LMEDetail
import java.text.SimpleDateFormat

class LMERecyclerViewAdapter(
    internal var context: Context
    , private var lmeObject: List<LMEObject>
    , private var lmeListener: LMEListener
) : RecyclerView.Adapter<LMERecyclerViewAdapter.MyViewHolder>() {

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var mDate: TextView = view.findViewById(R.id.textView_main_item_1)
        var mTeam1: TextView = view.findViewById(R.id.textView_main_item_2)
        var mTeamScore1: TextView = view.findViewById(R.id.textView_main_item_3)
        var mteam2: TextView = view.findViewById(R.id.textView_main_item_6)
        var mTeamScore2: TextView = view.findViewById(R.id.textView_main_item_5)

    }

    // this method for build view
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemLayout = R.layout.main_item
        val itemView = LayoutInflater.from(parent.context)
            .inflate(itemLayout, parent, false)
        return MyViewHolder(itemView)
    }

    // this method for init item in every view item
    @SuppressLint("SimpleDateFormat")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val mLMEDetail = lmeObject[position].lmeDetail

        val dateEvent = mLMEDetail.dateEvent
        if (dateEvent != null) {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd")
            val outputFormat = SimpleDateFormat("EEE, d MMM yyyy")
            val date = inputFormat.parse(dateEvent)
            if (date != null) {
                val str = outputFormat.format(date)
                holder.mDate.text = str
            }

        }


        holder.mTeam1.text = mLMEDetail.strHomeTeam
        holder.mTeamScore1.text = mLMEDetail.intHomeScore
        holder.mteam2.text = mLMEDetail.strAwayTeam
        holder.mTeamScore2.text = mLMEDetail.intAwayScore

        holder.itemView.setOnClickListener {
            lmeListener.itemDetail(mLMEDetail)

        }

    }

    // this method for get total item
    override fun getItemCount(): Int {
        var itemCount = 0

        try {
            val itemSize = lmeObject.size
            itemCount = itemSize
        } catch (e: Exception) {
        }

        return itemCount
    }

    // this interface for handle more button pressed
    interface LMEListener {
        fun itemDetail(lmeDetail: LMEDetail)

    }

    // this class is object of item in recyclerview
    class LMEObject(var lmeDetail: LMEDetail)

}