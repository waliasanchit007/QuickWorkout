package com.example.quickworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.quickworkout.databinding.ActivityBmiBinding
import java.math.RoundingMode
import java.text.DecimalFormat

class BmiActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBmiBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bmi)
        binding = ActivityBmiBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbarBmi.setNavigationOnClickListener {
            onBackPressed()
        }

        binding.btnBmiCalculate.setOnClickListener {
            if(binding.etBmiWeight.text.toString().isEmpty() || binding.etBmiHeight.text.toString().isEmpty() ){
                Toast.makeText(this, "Cannot be left blank", Toast.LENGTH_LONG).show()
            }
            else {
                calculateBmi(binding.etBmiWeight.text.toString().toFloat(), binding.etBmiHeight.text.toString().toFloat())
            }
        }
    }
    private fun calculateBmi(weight : Float, height : Float){
        val heightInm = height/100
        val heightSquare = heightInm * heightInm
        var bmi = weight/heightSquare
        bmi = roundOffDecimal(bmi.toDouble()).toFloat()
        displayBmiResult(bmi)
    }
    private fun displayBmiResult(bmi : Float){
        val bmiLabel: String
        val bmiDescription: String

        if (bmi.compareTo(15f) <= 0) {
            bmiLabel = "Very severely underweight"
            bmiDescription = "Oops! You really need to take better care of yourself! Eat more!"
        } else if (bmi.compareTo(15f) > 0 && bmi.compareTo(16f) <= 0
        ) {
            bmiLabel = "Severely underweight"
            bmiDescription = "Oops!You really need to take better care of yourself! Eat more!"
        } else if (bmi.compareTo(16f) > 0 && bmi.compareTo(18.5f) <= 0
        ) {
            bmiLabel = "Underweight"
            bmiDescription = "Oops! You really need to take better care of yourself! Eat more!"
        } else if (bmi.compareTo(18.5f) > 0 && bmi.compareTo(25f) <= 0
        ) {
            bmiLabel = "Normal"
            bmiDescription = "Congratulations! You are in a good shape!"
        } else if (bmi.compareTo(25f) > 0 && bmi.compareTo(30f) <= 0
        ) {
            bmiLabel = "Overweight"
            bmiDescription = "Oops! You really need to take care of your yourself! Workout maybe!"
        } else if (bmi.compareTo(30f) > 0 && bmi.compareTo(35f) <= 0
        ) {
            bmiLabel = "Obese Class | (Moderately obese)"
            bmiDescription = "Oops! You really need to take care of your yourself! Workout maybe!"
        } else if (bmi.compareTo(35f) > 0 && bmi.compareTo(40f) <= 0
        ) {
            bmiLabel = "Obese Class || (Severely obese)"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
        } else {
            bmiLabel = "Obese Class ||| (Very Severely obese)"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
        }

        binding.llBmiResult.visibility = View.VISIBLE
        binding.tvBmiValue.text = bmi.toString()
        binding.tvBmiResult.text = bmiLabel
        binding.tvBmiReview.text = bmiDescription
    }
    fun roundOffDecimal(number: Double): Double {
        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.CEILING
        return df.format(number).toDouble()
    }

}