package com.example.quickworkout

import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.SimpleAdapter
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.quickworkout.databinding.ActivityExerciseBinding
import java.util.*
import kotlin.collections.ArrayList

class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener{

    var mCountDownTimer: CountDownTimer? = null
    var mTimerDuration : Long = 10000
    var mRemainingTime: Long = mTimerDuration
    var mTimeElapsed : Long = 0
    private lateinit var binding: ActivityExerciseBinding
    private var mExerciseList : ArrayList<ExerciseModel>? = null
    private var presentExercisePosition = -1

    private var tts : TextToSpeech? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise)
        binding = ActivityExerciseBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        tts = TextToSpeech(this, this)
        mExerciseList = Constants.defaultExerciseList()
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
                presentExercisePosition++
                setupExerciseView()
            }
        }.start()
    }

    private fun setupRestView(){
        binding.llExerciseView.visibility = View.GONE
        binding.llRestView.visibility  = View.VISIBLE
        if(presentExercisePosition < mExerciseList!!.size - 1) {
            binding.tvRestViewExerciseName.text =
                mExerciseList!![presentExercisePosition + 1].getName()
            speakOut("Be ready for " + mExerciseList!![presentExercisePosition + 1].getName())
        }
        mCountDownTimer?.cancel()
        mTimeElapsed = 0
        mTimerDuration = 10000
        setupRestProgressBar()
    }

    private fun setupExerciseView(){
        speakOut( mExerciseList!![presentExercisePosition].getName())
        binding.ivExercise.setImageResource(mExerciseList!![presentExercisePosition].getImage())
        binding.tvExerciseName.text = mExerciseList!![presentExercisePosition].getName()
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
                if(presentExercisePosition < mExerciseList!!.size - 1)
                    setupRestView()
            }
        }.start()
    }

    override fun onInit(status: Int) {
        if(status == TextToSpeech.SUCCESS){
            val result = tts!!.setLanguage(Locale.ENGLISH)
            if(result == TextToSpeech.LANG_MISSING_DATA || result ==
                    TextToSpeech.LANG_NOT_SUPPORTED)
                        Log.e("TTS", "the language provided is not supported")
            else{
                Log.e("TTS", "Initialization failed")
            }
        }
    }

    private fun speakOut(text : CharSequence){
        tts!!.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }

    override fun onDestroy() {

        if(mCountDownTimer != null) mCountDownTimer!!.cancel()
        if(tts!= null) {
            tts!!.stop()
            tts!!.shutdown()
        }

        super.onDestroy()
    }
}