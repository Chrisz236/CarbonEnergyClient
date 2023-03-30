package com.example.carbonenergy

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import kotlinx.coroutines.*
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.carbonenergy.model.User
import com.example.carbonenergy.network.CarbonEnergyApi

class Home : AppCompatActivity(), SensorEventListener {

    var sensorManager: SensorManager? = null

    var running = false
    var totalSteps = 0f
    var previousTotalSteps = 0f

    @SuppressLint("SetTextI18n", "MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_with_step)

        intent = getIntent()
        val userId = intent.getStringExtra("user_id").toString()
        var user = User("TEST USER", 100000, 6)

        // network activity to update user data
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

        loadData()
        collectSteps(user, title)
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        val steps = findViewById<EditText>(R.id.textSteps)
        val btnSubmitSteps = findViewById<Button>(R.id.btnSubmitStep)
        val btnPlantTree = findViewById<Button>(R.id.btnPlantTree)

        btnSubmitSteps.setOnClickListener {
            if(steps.text.toString().isNotBlank() || steps.text.toString().isNotEmpty()) {
                // every 200 step convert to 1 energy
                val newEnergy = (steps.text.toString().toFloat() / 200.0).toInt()
                user = user.copy(energy = user.energy + newEnergy)

                // network activity to update user data
                runBlocking {
                    val job = launch {
                        CarbonEnergyApi.retrofitService.updateUser(user)
                    }
                    job.join()
                }

                title.setText("Hello, ${user.userId}\n" +
                        "You have ${user.energy} points.\n" +
                        "You have ${user.treesPlanted} trees planted.")

                Toast.makeText(this, "You earned $newEnergy points", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Invalid input", Toast.LENGTH_SHORT).show()
            }
        }

        btnPlantTree.setOnClickListener {
            if(user.energy < 10000) {
                Toast.makeText(applicationContext, "Failed to Plant! You must have 10000 points for a tree", Toast.LENGTH_LONG).show()
            } else {
                user = user.copy(energy = user.energy - 10000, treesPlanted = user.treesPlanted + 1)

                // network activity to update user data
                runBlocking {
                    val job = launch {
                        CarbonEnergyApi.retrofitService.updateUser(user)
                    }
                    job.join()
                }

                title.setText("Hello, ${user.userId}\n" +
                        "You have ${user.energy} points.\n" +
                        "You have ${user.treesPlanted} trees planted.")

                Toast.makeText(this, "Yeah! You just planted a new tree", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        running = true
        val stepSensor = sensorManager?.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)

        if(stepSensor == null) {
            Toast.makeText(this, "No step counter detected", Toast.LENGTH_SHORT).show()
        } else {
            sensorManager?.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_UI)
        }
    }

    override fun onPause() {
        super.onPause()
        running = false
        sensorManager?.unregisterListener(this)
    }

    @SuppressLint("SetTextI18n")
    override fun onSensorChanged(event: SensorEvent?) {
        Toast.makeText(this, "sensor changed", Toast.LENGTH_LONG).show()
        if(running) {
            totalSteps = event!!.values[0]
            val currentSteps = totalSteps.toInt() - previousTotalSteps.toInt()
            findViewById<TextView>(R.id.textStepValue).text = ("$currentSteps")
        }
    }

    @SuppressLint("SetTextI18n")
    private fun collectSteps(user: User, title: TextView) {
        findViewById<TextView>(R.id.textStepValue).setOnClickListener {
            val netSteps = totalSteps - previousTotalSteps
            val newEnergy = (netSteps / 200.0).toInt()
            val updatedUser = user.copy(energy = user.energy + newEnergy)

            // network activity to update user data
            runBlocking {
                val job = launch {
                    CarbonEnergyApi.retrofitService.updateUser(updatedUser)
                }
                job.join()
            }

            title.text = ("Hello, ${user.userId}\n" +
                    "You have ${user.energy} points.\n" +
                    "You have ${user.treesPlanted} trees planted.")

            Toast.makeText(this, "You earned $newEnergy points", Toast.LENGTH_SHORT).show()

            previousTotalSteps = totalSteps

            findViewById<TextView>(R.id.textStepValue).text = 0.toString()
            saveData()
            Toast.makeText(this, "You just collect your energy", Toast.LENGTH_SHORT).show()
        }
    }

    private fun saveData() {
        val sharedPreferences = getSharedPreferences ( "myPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putFloat ("savedSteps", previousTotalSteps)
        editor.apply()
    }

    private fun loadData() {
        val sharedPreferences = getSharedPreferences ( "myPrefs", Context.MODE_PRIVATE)
        val savedNumber = sharedPreferences.getFloat("savedSteps", 0f)
        previousTotalSteps = savedNumber
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
    }
}
