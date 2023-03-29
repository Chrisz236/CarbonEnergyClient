package com.example.carbonenergy

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.carbonenergy.ui.CarbonEnergyApp
import com.example.carbonenergy.ui.screens.CarbonEnergyViewModel
import com.example.carbonenergy.ui.theme.CarbonEnergyTheme

class Home : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menu)
        intent = getIntent()
        val userId = intent.getStringExtra("user_id")
        val carbonEnergyViewModel: CarbonEnergyViewModel

//        setContent {
//            CarbonEnergyTheme {
//                if (userId != null) {
//                    CarbonEnergyApp(userId)
//                }
//            }
//        }
    }
}