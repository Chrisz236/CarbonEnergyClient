package com.example.carbonenergy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.compose.setContent
import com.example.carbonenergy.ui.CarbonEnergyApp
import com.example.carbonenergy.ui.theme.CarbonEnergyTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)
        val userId = findViewById<EditText>(R.id.textUserID)
        val submitButton = findViewById<Button>(R.id.buttonSubmit)
        submitButton.setOnClickListener {
            setContent {
                CarbonEnergyTheme {
                    CarbonEnergyApp(userId)
                }
            }
        }
    }
}