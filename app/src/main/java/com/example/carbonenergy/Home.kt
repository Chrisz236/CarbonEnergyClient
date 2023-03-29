package com.example.carbonenergy

import android.annotation.SuppressLint
import kotlinx.coroutines.*
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.carbonenergy.model.User
import com.example.carbonenergy.network.CarbonEnergyApi

class Home : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menu)
        intent = getIntent()

        val userId = intent.getStringExtra("user_id").toString()
        var user = User("null", -1)

        runBlocking {
            val job = launch {
                user = CarbonEnergyApi.retrofitService.getUser(userId)
            }
            job.join()
        }

        val test = findViewById<TextView>(R.id.textGreeting)
        test.setText("Hello, ${user.user_id}\nYou have ${user.energy} points!")
    }
}