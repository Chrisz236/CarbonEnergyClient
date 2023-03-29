package com.example.carbonenergy

import android.annotation.SuppressLint
import kotlinx.coroutines.*
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.carbonenergy.model.User
import com.example.carbonenergy.network.CarbonEnergyApi

class Home : AppCompatActivity() {
    @SuppressLint("SetTextI18n", "MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home)
        intent = getIntent()

        val userId = intent.getStringExtra("user_id").toString()
        var user = User("null", -1, -1)

        runBlocking {
            val job = launch {
                user = CarbonEnergyApi.retrofitService.getUser(userId)
            }
            job.join()
        }

        // Title
        val title = findViewById<TextView>(R.id.textGreeting)
        title.setText("Hello, ${user.userId}\n" +
                "You have ${user.energy} points.\n" +
                "You have ${user.treesPlanted} trees planted.")

        val steps = findViewById<EditText>(R.id.textSteps)
        val btnSubmitSteps = findViewById<Button>(R.id.btnSubmitStep)
        val btnPlantTree = findViewById<Button>(R.id.btnPlantTree)

        btnSubmitSteps.setOnClickListener {
            if(steps.text.toString().isNotBlank() || steps.text.toString().isNotEmpty()) {
                // every 500 step convert to 1 energy
                val newEnergy = (steps.text.toString().toFloat() / 300.0).toInt()
                val updatedUser = user.copy(energy = user.energy + newEnergy)
                runBlocking {
                    val job = launch {
                        CarbonEnergyApi.retrofitService.updateUser(updatedUser)
                    }
                    job.join()
                }
                Toast.makeText(applicationContext, "Energy upload succeed", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(applicationContext, "Invalid input", Toast.LENGTH_SHORT).show()
            }
        }

        btnPlantTree.setOnClickListener {
            if(user.energy < 10000) {
                Toast.makeText(applicationContext, "Failed to Plant! You must have 10000 points for a tree", Toast.LENGTH_LONG).show()
            } else {
                val updatedUser = user.copy(energy = user.energy - 10000, treesPlanted = user.treesPlanted + 1)
                runBlocking {
                    val job = launch {
                        CarbonEnergyApi.retrofitService.updateUser(updatedUser)
                    }
                    job.join()
                }
                Toast.makeText(applicationContext, "Yeah! You just planted a new tree", Toast.LENGTH_SHORT).show()
            }
        }
    }
}