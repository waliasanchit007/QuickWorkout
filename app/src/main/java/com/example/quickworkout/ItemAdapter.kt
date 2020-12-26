package com.example.quickworkout

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text

class ItemAdapter(val context: Context, val items: ArrayList<String>) :
    RecyclerView.Adapter<ItemAdapter.ViewHolder>() {

    class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        val llHistory = view.findViewById<LinearLayout>(R.id.ll_history_main_item)
        val position = view.findViewById<TextView>(R.id.tv_position)
        val date = view.findViewById<TextView>(R.id.tv_date)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewHolder = ViewHolder(
            LayoutInflater.from(context).inflate(
            R.layout.item_history,
            parent,
            false))
        return viewHolder
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.position.text = (position + 1).toString()
        holder.date.text = items[position]

        if(position%2 == 0){
            holder.llHistory.setBackgroundColor(Color.parseColor("#EBEBEB"))
        }else
            holder.llHistory.setBackgroundColor(Color.parseColor("#FFFFFF"))
    }


    override fun getItemCount(): Int {
        return items.size
    }

}