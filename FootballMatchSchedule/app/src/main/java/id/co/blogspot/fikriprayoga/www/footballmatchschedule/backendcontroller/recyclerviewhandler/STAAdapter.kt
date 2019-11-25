package id.co.blogspot.fikriprayoga.www.footballmatchschedule.backendcontroller.recyclerviewhandler

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import id.co.blogspot.fikriprayoga.www.footballmatchschedule.R
import org.json.JSONObject

class STAAdapter(internal var context: Context
                 , internal var staObject: List<STAObject>
                 , internal var staListener: STAListener) : RecyclerView.Adapter<STAAdapter.MyViewHolder>() {

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var mLogo: ImageView
        var mTeamName: TextView

        init {
            mLogo = view.findViewById(R.id.imageView_team_item_1)
            mTeamName = view.findViewById(R.id.textView_team_item_2)

        }

    }

    // this method for build view
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemLayout = R.layout.team_item
        val itemView = LayoutInflater.from(parent.context)
                .inflate(itemLayout, parent, false)
        return MyViewHolder(itemView)
    }

    // this method for init item in every view item
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val mSTFObject = staObject[position].Data

        Picasso.get()
                .load(mSTFObject!!.getString("strTeamBadge"))
                .resize(100, 100)
                .into(holder.mLogo)

        holder.mTeamName.text = mSTFObject.getString("strTeam")

        val x1 = Bundle()
        x1.putString("Data", mSTFObject.toString())
        //x1.putBoolean("isLastMatch", true)

        holder.itemView.setOnClickListener {
            staListener.item(x1)

        }

    }

    // this method for get total item
    override fun getItemCount(): Int {
        var itemCount = 0

        try {
            val itemSize = staObject.size
            itemCount = itemSize
        } catch (e: Exception) {
        }

        return itemCount
    }

    // this interface for handle more button pressed
    interface STAListener {
        fun item(itemData: Bundle)

    }

    // this class is object of item in recyclerview
    class STAObject(var Data: JSONObject?)

}