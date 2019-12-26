package com.example.footballmatchschedule.other.recyclerviewadapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.footballmatchschedule.R
import com.example.footballmatchschedule.model.apiresponse.TeamDetail
import com.squareup.picasso.Picasso

class SearchTeamRecyclerViewAdapter(
    internal var context: Context
    , private var searchTeamObject: List<SearchTeamObject>
    , private var searchTeamListener: SearchTeamListener
) : RecyclerView.Adapter<SearchTeamRecyclerViewAdapter.MyViewHolder>() {

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var mLogo: ImageView = view.findViewById(R.id.imageView_item_team_1)
        var mSearchTeamName: TextView = view.findViewById(R.id.textView_item_team_2)

    }

    // this method for build view
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemLayout = R.layout.item_team
        val itemView = LayoutInflater.from(parent.context)
            .inflate(itemLayout, parent, false)
        return MyViewHolder(itemView)
    }

    // this method for init item in every view item
    @SuppressLint("SimpleDateFormat")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val mSearchTeamDetail = searchTeamObject[position].searchTeamDetail

        Picasso.get()
            .load(mSearchTeamDetail.strTeamBadge)
            .resize(100, 100)
            .into(holder.mLogo)

        holder.mSearchTeamName.text = mSearchTeamDetail.strTeam

        holder.itemView.setOnClickListener {
            searchTeamListener.itemDetail(mSearchTeamDetail)

        }

    }

    // this method for get total item
    override fun getItemCount(): Int {
        var itemCount = 0

        try {
            val itemSize = searchTeamObject.size
            itemCount = itemSize
        } catch (e: Exception) {
        }

        return itemCount
    }

    // this interface for handle more button pressed
    interface SearchTeamListener {
        fun itemDetail(teamDetail: TeamDetail)

    }

    // this class is object of item in recyclerview
    class SearchTeamObject(var searchTeamDetail: TeamDetail)

}