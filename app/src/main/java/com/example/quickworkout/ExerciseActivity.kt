package com.example.quickworkout

import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.SimpleAdapter
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.quickworkout.databinding.ActivityExerciseBinding

class ExerciseActivity : AppCompatActivity() {

    var mCountDownTimer: CountDownTimer? = null
    var mTimerDuration : Long = 10000
    var mRemainingTime: Long = mTimerDuration
    var mTimeElapsed : Long = 0
    private lateinit var binding: ActivityExerciseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise)
        binding = ActivityExerciseBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setUpToolBar()
        setupRestView()
    }

    private fun setUpToolBar(){
        binding.toolbarExerciseActivity.setTitle(R.string.app_name)
        binding.toolbarExerciseActivity.setTitleTextColor(Color.BLACK)
        binding.toolbarExerciseActivity.setNavigationOnClickListener { onBackPressed() }
    }

    private fun setupRestProgressBar(){
        mRemainingTime = mTimerDuration - mTimeElapsed
        mCountDownTimer = object: CountDownTimer(mRemainingTime, 1000){
            override fun onTick(millisUntilFinished: Long) {
                mTimeElapsed += 1000
                mRemainingTime = mTimerDuration - mTimeElapsed
                binding.tvTimer.text = (mRemainingTime/1000).toString()
                binding.progressBar.progress = (mRemainingTime/1000).toInt()

            }
            override fun onFinish() {
               // Toast.makeText(this@ExerciseActivity, "Timer is Finished", Toast.LENGTH_SHORT).show()
                setupExerciseView()
            }
        }.start()
    }

    private fun setupRestView(){
        mCountDownTimer?.cancel()
        mTimeElapsed = 0
        mTimerDuration = 10000
        setupRestProgressBar()
    }

    private fun setupExerciseView(){
        mCountDownTimer?.cancel()
        mTimeElapsed = 0
        mTimerDuration = 30000
        binding.llRestView.visibility = View.GONE
        binding.llExerciseView.visibility = View.VISIBLE
        setupExerciseProgressBar()
    }

    private fun setupExerciseProgressBar(){
        mRemainingTime = mTimerDuration - mTimeElapsed
        mCountDownTimer = object: CountDownTimer(mRemainingTime, 1000){
            override fun onTick(millisUntilFinished: Long) {
                mTimeElapsed += 1000
                mRemainingTime = mTimerDuration - mTimeElapsed
                binding.tvExerciseTimer.text = (mRemainingTime/1000).toString()
                binding.progressBarExercise.progress = (mRemainingTime/1000).toInt()

            }
            override fun onFinish() {
                Toast.makeText(this@ExerciseActivity, "Timer is Finished", Toast.LENGTH_SHORT).show()
            }
        }.start()
    }
}