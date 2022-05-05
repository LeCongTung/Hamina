package com.example.hamina.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hamina.R
import com.example.hamina.units.History
import com.squareup.picasso.Picasso

class Adapter_History(private val list: ArrayList<History>): RecyclerView.Adapter<Adapter_History.MyViewHolder>() {

    //    Choice a product in type
    private lateinit var mListener: onItemClickListener

    interface onItemClickListener {

        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener){

        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_history, parent, false)
        return MyViewHolder(itemView, mListener)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentitem = list[position]
        holder.historyid.text = currentitem.id
        holder.historydate.text = currentitem.date
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class MyViewHolder(itemView: View, listener: onItemClickListener): RecyclerView.ViewHolder(itemView){

        val historyid: TextView = itemView.findViewById(R.id.history_id)
        val historydate: TextView = itemView.findViewById(R.id.history_date)

        init {

            itemView.setOnClickListener{

                listener.onItemClick(adapterPosition)
            }
        }
    }
}