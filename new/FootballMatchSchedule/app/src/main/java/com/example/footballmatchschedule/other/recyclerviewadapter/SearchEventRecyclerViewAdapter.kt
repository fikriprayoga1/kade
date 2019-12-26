package com.example.footballmatchschedule.other.recyclerviewadapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.footballmatchschedule.R
import com.example.footballmatchschedule.model.apiresponse.EventDetail
import java.text.SimpleDateFormat

class SearchEventRecyclerViewAdapter(
    internal var context: Context
    , private var searchEventObject: List<SearchEventObject>
    , private var searchEventListener: SearchEventListener
) : RecyclerView.Adapter<SearchEventRecyclerViewAdapter.MyViewHolder>() {

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var mDate: TextView = view.findViewById(R.id.textView_main_item_1)
        var mTeam1: TextView = view.findViewById(R.id.textView_main_item_2)
        var mTeamScore1: TextView = view.findViewById(R.id.textView_main_item_3)
        var mteam2: TextView = view.findViewById(R.id.textView_main_item_6)
        var mTeamScore2: TextView = view.findViewById(R.id.textView_main_item_5)

    }

    // this method for build view
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemLayout = R.layout.item_event
        val itemView = LayoutInflater.from(parent.context)
            .inflate(itemLayout, parent, false)
        return MyViewHolder(itemView)
    }

    // this method for init item in every view item
    @SuppressLint("SimpleDateFormat")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val mSearchEventDetail = searchEventObject[position].searchEventDetail

        val dateEvent = mSearchEventDetail.dateEvent
        if (dateEvent != null) {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd")
            val outputFormat = SimpleDateFormat("EEE, d MMM yyyy")
            val date = inputFormat.parse(dateEvent)
            if (date != null) {
                val str = outputFormat.format(date)
                holder.mDate.text = str
            }

        }


        holder.mTeam1.text = mSearchEventDetail.strHomeTeam
        holder.mTeamScore1.text = mSearchEventDetail.intHomeScore
        holder.mteam2.text = mSearchEventDetail.strAwayTeam
        holder.mTeamScore2.text = mSearchEventDetail.intAwayScore

        holder.itemView.setOnClickListener {
            searchEventListener.itemDetail(mSearchEventDetail)

        }

    }

    // this method for get total item
    override fun getItemCount(): Int {
        var itemCount = 0

        try {
            val itemSize = searchEventObject.size
            itemCount = itemSize
        } catch (e: Exception) {
        }

        return itemCount
    }

    // this interface for handle more button pressed
    interface SearchEventListener {
        fun itemDetail(eventDetail: EventDetail)

    }

    // this class is object of item in recyclerview
    class SearchEventObject(var searchEventDetail: EventDetail)

}