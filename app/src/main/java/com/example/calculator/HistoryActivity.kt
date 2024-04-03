package com.example.calculator

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.calculator.databinding.ActivityHistoryBinding
import com.example.calculator.databinding.ActivityMainBinding

class HistoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHistoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHistoryBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val historyList: Array<String> =
            intent.getStringArrayExtra("history_list") ?: arrayOf()

        Log.d("HistoryActivity" , "hitory size ${historyList.size}")

        textHistory(historyList.toList())


    }

    private fun textHistory(list : List<String>) = with(binding) {

        val historyText  = StringBuilder()

        for ( text in list ) {

            historyText.append("$text \n")
        }

        historyCalculationText.text = historyText
        }



    }