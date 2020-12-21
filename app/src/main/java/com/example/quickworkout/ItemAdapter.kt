package com.example.quickworkout

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ItemAdapter(val context: Context, val items: ArrayList<ContactModel>) :
    RecyclerView.Adapter<ItemAdapter.ViewHolder>() {

    class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        val nameTextView = view.findViewById<TextView>(R.id.contact_name)
        val messageButton = view.findViewById<Button>(R.id.message_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewHolder = ViewHolder(
            LayoutInflater.from(context).inflate(
            R.layout.item_contact,
            parent,
            false))
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.nameTextView.text = items[position].name
        holder.messageButton.text = if(items[position].isOnline) "Message" else "Offline"
        holder.messageButton.isEnabled = (items[position].isOnline)
    }

    override fun getItemCount(): Int {
        return items.size
    }

}