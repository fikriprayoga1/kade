package com.example.footballmatchschedule.util.recyclerviewadapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.footballmatchschedule.R
import com.example.footballmatchschedule.model.apiresponse.PlayerDetail
import com.squareup.picasso.Picasso

class PlayerRecyclerViewAdapter(
    internal var context: Context
    , private var playerObject: List<PlayerObject>
    , private var playerListener: PlayerListener
) : RecyclerView.Adapter<PlayerRecyclerViewAdapter.MyViewHolder>() {

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var mPlayerImage: ImageView = view.findViewById(R.id.imageView_player_item_1)
        var mPlayerName: TextView = view.findViewById(R.id.textView_player_item_2)
        var mPlayerPosition: TextView = view.findViewById(R.id.textView_player_item_3)

    }

    // this method for build view
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemLayout = R.layout.item_player
        val itemView = LayoutInflater.from(parent.context)
            .inflate(itemLayout, parent, false)
        return MyViewHolder(itemView)
    }

    // this method for init item in every view item
    @SuppressLint("SimpleDateFormat")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val mPlayerDetail = playerObject[position].playerDetail

        Picasso.get()
            .load(mPlayerDetail.strThumb)
            .resize(100, 100)
            .into(holder.mPlayerImage)

        holder.mPlayerName.text = mPlayerDetail.strPlayer
        holder.mPlayerPosition.text = mPlayerDetail.strPosition

        holder.itemView.setOnClickListener {
            playerListener.itemDetail(mPlayerDetail)

        }

    }

    // this method for get total item
    override fun getItemCount(): Int {
        var itemCount = 0

        try {
            val itemSize = playerObject.size
            itemCount = itemSize
        } catch (e: Exception) {
        }

        return itemCount
    }

    // this interface for handle more button pressed
    interface PlayerListener {
        fun itemDetail(playerDetail: PlayerDetail)

    }

    // this class is object of item in recyclerview
    class PlayerObject(var playerDetail: PlayerDetail)

}