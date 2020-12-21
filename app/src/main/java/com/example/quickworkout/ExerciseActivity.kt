package com.example.quickworkout

import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Adapter
import android.widget.SimpleAdapter
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quickworkout.databinding.ActivityExerciseBinding
import java.util.*
import kotlin.collections.ArrayList

class ExerciseActivity : AppCompatActivity(){

    var mCountDownTimer: CountDownTimer? = null
    var mTimerDuration : Long = 10000
    var mRemainingTime: Long = mTimerDuration
    var mTimeElapsed : Long = 0
    private lateinit var binding: ActivityExerciseBinding
    private var mExerciseList : ArrayList<ExerciseModel>? = null
    private var presentExercisePosition = -1
    private var adapter : ExerciseStatusRecyclerViewAdapter? = null

    private var tts : TextToSpeech? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise)
        binding = ActivityExerciseBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setUpToolBar()
        mExerciseList = Constants.defaultExerciseList()
        setUpRecyclerViewAdapter()
        tts = TextToSpeech(this) {
            if (it == TextToSpeech.SUCCESS) {
                val result = tts!!.setLanguage(Locale.US)
                if (result == TextToSpeech.LANG_MISSING_DATA || result ==
                        TextToSpeech.LANG_NOT_SUPPORTED)
                    Log.e("TTS", "the language provided is not supported")
                else {
                    Log.i("speak", "success")
                    setupRestView()
                }

            } else {
                Log.e("TTS", "Initialization failed")
            }
        }

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
                presentExercisePosition++
                mExerciseList!![presentExercisePosition].setIsSelected(true)
                adapter!!.notifyDataSetChanged()
                setupExerciseView()
            }
        }.start()
    }

    private fun setupRestView(){

        if(presentExercisePosition < mExerciseList!!.size - 1) {
            binding.llExerciseView.visibility = View.GONE
            binding.llRestView.visibility  = View.VISIBLE
            binding.tvRestViewExerciseName.text = mExerciseList!![presentExercisePosition + 1].getName()
            if(mCountDownTimer != null)
            mCountDownTimer?.cancel()
            mTimeElapsed = 0
            mTimerDuration = 10000
            speakOut("Be ready for " + mExerciseList!![presentExercisePosition + 1].getName())
            setupRestProgressBar()
        }
    }

    private fun setupExerciseView(){
        speakOut( mExerciseList!![presentExercisePosition].getName())
        binding.ivExercise.setImageResource(mExerciseList!![presentExercisePosition].getImage())
        binding.tvExerciseName.text = mExerciseList!![presentExercisePosition].getName()
        if(mCountDownTimer != null)
            mCountDownTimer!!.cancel()
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
                if(presentExercisePosition < mExerciseList!!.size - 1) {
                    mExerciseList!![presentExercisePosition].setIsCompleted(true)
                    mExerciseList!![presentExercisePosition].setIsSelected(false)
                    adapter!!.notifyDataSetChanged()
                    setupRestView()
                }else{
                    Toast.makeText(this@ExerciseActivity, "congratulations!!", Toast.LENGTH_LONG).show()
                }
            }
        }.start()
    }

    private fun speakOut(text : CharSequence){
        if (tts!= null) {
            tts!!.speak(text, TextToSpeech.QUEUE_ADD, null, "")
            Log.i("speak", "$text")
        }else Log.i("speak", "null")
    }

    override fun onDestroy() {

        if(mCountDownTimer != null) mCountDownTimer!!.cancel()
        if(tts!= null) {
            tts!!.stop()
            tts!!.shutdown()
        }

        super.onDestroy()
    }

    private fun setUpRecyclerViewAdapter(){
        adapter = ExerciseStatusRecyclerViewAdapter(this,mExerciseList!!)
        binding.rvExerciseNumber.adapter = adapter
        binding.rvExerciseNumber.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

    }
}