package com.example.cryptoapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.cryptoapp.databinding.RvItemBinding

class Rv_Adaptor(val context: Context,  var data: ArrayList<Rv_Modal>): RecyclerView.Adapter<Rv_Adaptor.MyViewHolder>() {

    fun changeData(filterdata:ArrayList<Rv_Modal>){
        data = filterdata
        notifyDataSetChanged()
    }
    inner class MyViewHolder(val binding: RvItemBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = RvItemBinding.inflate(LayoutInflater.from(context),parent,false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
       return data.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        setAnimation(holder.itemView)
       holder.binding.name.text = data[position].name
        holder.binding.symbol.text = data[position].symbols
        holder.binding.price.text = data[position].price


    }
    fun setAnimation(viw:View){
        val anim = AlphaAnimation(0.0f,1.0f)
        anim.duration = 1000
        viw.startAnimation(anim)
    }
}