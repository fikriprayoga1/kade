package com.example.footballmatchschedule.other.recyclerviewadapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.footballmatchschedule.R
import com.example.footballmatchschedule.model.apiresponse.NMEDetail
import java.text.SimpleDateFormat

class NMERecyclerViewAdapter(internal var context: Context
                             , private var nmeObject: List<NMEObject>
                             , private var nmeListener: NMEListener) : RecyclerView.Adapter<NMERecyclerViewAdapter.MyViewHolder>() {

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
        val mNMEDetail = nmeObject[position].nmeDetail

        val inputFormat = SimpleDateFormat("yyyy-MM-dd")
        val outputFormat = SimpleDateFormat("EEE, d MMM yyyy")
        val dateEvent = mNMEDetail.dateEvent
        if (dateEvent != null) {
            val date = inputFormat.parse(dateEvent)
            if (date != null) {
                val str = outputFormat.format(date)
                holder.mDate.text = str
            }

        }


        holder.mTeam1.text = mNMEDetail.strHomeTeam
        holder.mTeamScore1.text = mNMEDetail.intHomeScore
        holder.mteam2.text = mNMEDetail.strAwayTeam
        holder.mTeamScore2.text = mNMEDetail.intAwayScore

        holder.itemView.setOnClickListener {
            nmeListener.itemDetail(mNMEDetail)

        }

    }

    // this method for get total item
    override fun getItemCount(): Int {
        var itemCount = 0

        try {
            val itemSize = nmeObject.size
            itemCount = itemSize
        } catch (e: Exception) {
        }

        return itemCount
    }

    // this interface for handle more button pressed
    interface NMEListener {
        fun itemDetail(nmeDetail: NMEDetail)

    }

    // this class is object of item in recyclerview
    class NMEObject(var nmeDetail: NMEDetail)

}