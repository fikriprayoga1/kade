package id.co.blogspot.fikriprayoga.www.footballmatchschedule.backendcontroller.recyclerviewhandler

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import id.co.blogspot.fikriprayoga.www.footballmatchschedule.R
import org.json.JSONObject
import java.text.SimpleDateFormat

class LMEFAdapter(internal var context: Context
                 , internal var lmefObject: List<LMEFObject>
                 , internal var lmefListener: LMEFListener) : RecyclerView.Adapter<LMEFAdapter.MyViewHolder>() {
    lateinit var str: String

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var mDate: TextView
        var mTeam1: TextView
        var mTeamScore1: TextView
        var mteam2: TextView
        var mTeamScore2: TextView

        init {
            mDate = view.findViewById(R.id.textView_main_item_1)
            mTeam1 = view.findViewById(R.id.textView_main_item_2)
            mTeamScore1 = view.findViewById(R.id.textView_main_item_3)
            mteam2 = view.findViewById(R.id.textView_main_item_6)
            mTeamScore2 = view.findViewById(R.id.textView_main_item_5)

        }

    }

    // this method for build view
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemLayout = R.layout.main_item
        val itemView = LayoutInflater.from(parent.context)
                .inflate(itemLayout, parent, false)
        return MyViewHolder(itemView)
    }

    // this method for init item in every view item
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val mLMFObject = lmefObject[position].Data

        try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd")
            val outputFormat = SimpleDateFormat("EEE, d MMM yyyy")
            val date = inputFormat.parse(mLMFObject!!.getString("dateEvent"))
            str = outputFormat.format(date)

        } catch (e: java.lang.Exception) {
        }

        holder.mDate.text = str
        holder.mTeam1.text = mLMFObject!!.getString("strHomeTeam")
        try {
            holder.mTeamScore1.text = mLMFObject.getString("intHomeScore")!!
        } catch (e: Exception) {
            holder.mTeamScore1.text = ""
        }
        holder.mteam2.text = mLMFObject.getString("strAwayTeam")
        try {
            holder.mTeamScore2.text = mLMFObject.getString("intAwayScore")
        } catch (e: java.lang.Exception) {
            holder.mTeamScore2.text = ""
        }


        val x1 = Bundle()
        x1.putString("Data", mLMFObject.toString())
        //x1.putBoolean("isLastMatch", true)

        holder.itemView.setOnClickListener {
            lmefListener.item(x1)

        }

    }

    // this method for get total item
    override fun getItemCount(): Int {
        var itemCount = 0

        try {
            val itemSize = lmefObject.size
            itemCount = itemSize
        } catch (e: Exception) {
        }

        return itemCount
    }

    // this interface for handle more button pressed
    interface LMEFListener {
        fun item(itemData: Bundle)

    }

    // this class is object of item in recyclerview
    class LMEFObject(var Data: JSONObject?)

}