package com.example.hamina.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hamina.R
import com.example.hamina.layouts.Layout_Notification
import com.example.hamina.shows.Layout_ShowType
import com.example.hamina.units.Type
import com.squareup.picasso.Picasso

class Adapter_Type(private val list: ArrayList<Type>): RecyclerView.Adapter<Adapter_Type.MyViewHolder>() {

//    Choice a product in type
    private lateinit var mListener: onItemClickListener

    interface onItemClickListener {

        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener){

        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_type, parent, false)
        return MyViewHolder(itemView, mListener)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentitem = list[position]
        holder.itemname.text = currentitem.name
        Picasso.with(holder.itemimage.context).load(currentitem.photo).into(holder.itemimage)

    }

    override fun getItemCount(): Int {
        return list.size
    }

    class MyViewHolder(itemView: View, listener: onItemClickListener): RecyclerView.ViewHolder(itemView){

        val itemname: TextView = itemView.findViewById(R.id.item_name)
        val itemimage: ImageView = itemView.findViewById(R.id.item_image)

        init {

            itemView.setOnClickListener{

                listener.onItemClick(adapterPosition)
            }
        }

    }
}