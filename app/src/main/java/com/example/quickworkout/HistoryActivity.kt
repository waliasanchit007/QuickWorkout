package com.example.quickworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quickworkout.databinding.ActivityHistoryBinding

class HistoryActivity : AppCompatActivity() {
    private lateinit var binding : ActivityHistoryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        getAllCompleteDated()
    }

    fun getAllCompleteDated(){
        val dbHandler = SqliteOpenHelper(this, null)
        val allCompletedDateList = dbHandler.getAllCompletedDateList()
        if(allCompletedDateList.isEmpty()){
            binding.tvHeading.visibility = View.VISIBLE
            binding.rvHistory.visibility = View.GONE
            binding.tvHeading.visibility = View.GONE
        } else{
            binding.rvHistory.visibility = View.VISIBLE
            binding.tvHeading.visibility = View.VISIBLE
            binding.tvNoDataAvailable.visibility = View.GONE

            binding.rvHistory.adapter = ItemAdapter(this,allCompletedDateList)
            binding.rvHistory.layoutManager = LinearLayoutManager(this)
        }

    }
}