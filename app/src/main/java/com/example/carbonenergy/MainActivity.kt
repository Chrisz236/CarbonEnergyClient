package com.example.carbonenergy

import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        val userId = findViewById<EditText>(R.id.textUserID)
        val submitButton = findViewById<Button>(R.id.buttonSubmit)
        submitButton.setOnClickListener {
            var intent = Intent(this, Home::class.java)
            intent.putExtra("user_id", userId.text.toString())
            startActivity(intent)
        }
    }
}