package com.example.quickworkout

import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import java.io.Console

class ExerciseStatusRecyclerViewAdapter(val context: Context, val itemList : ArrayList<ExerciseModel>) : RecyclerView.Adapter<ExerciseStatusRecyclerViewAdapter.ViewHolder>() {

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val tvExerciseStatus = itemView.findViewById<TextView>(R.id.tv_exercise_status)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(
                                        R.layout.item_exercise_status,
                                        parent,
                                        false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val presentItem = itemList[position]
        holder.tvExerciseStatus.text = presentItem.getId().toString()
        if(presentItem.getIsSelected()){
            holder.tvExerciseStatus.background = AppCompatResources.getDrawable(context, R.drawable.item_circular_color_white_background_green_border_exercise_status)
        }else if(presentItem.getIsCompleted()){
            holder.tvExerciseStatus.background = AppCompatResources.getDrawable(context, R.drawable.item_circular_color_white_background_green_border_exercise_status_is_completed)
            holder.tvExerciseStatus.setTextColor(Color.WHITE)
        }

    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}