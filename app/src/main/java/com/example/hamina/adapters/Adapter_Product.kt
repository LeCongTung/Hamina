package com.example.hamina.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hamina.R
import com.example.hamina.layouts.Layout_Home
import com.example.hamina.units.ProductMen
import com.squareup.picasso.Picasso

class Adapter_Product(private val list: ArrayList<ProductMen>): RecyclerView.Adapter<Adapter_Product.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentitem = list[position]
        holder.itemname.text = currentitem.name
        holder.itemprice.text = "$" + currentitem.price.toString()
        Picasso.with(holder.itemimage.context).load(currentitem.photomain).into(holder.itemimage)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val itemname: TextView = itemView.findViewById(R.id.item_name)
        val itemprice: TextView = itemView.findViewById(R.id.item_price)
        val itemimage: ImageView = itemView.findViewById(R.id.item_image)

    }
}