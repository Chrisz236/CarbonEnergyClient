package com.example.carbonenergy.ui

import android.widget.EditText
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.carbonenergy.ui.screens.CarbonEnergyViewModel
import com.example.carbonenergy.ui.screens.HomeScreen

@Composable
fun CarbonEnergyApp(id: EditText, modifier: Modifier = Modifier) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            color = MaterialTheme.colors.background
        ) {
            val carbonEnergyViewModel: CarbonEnergyViewModel = viewModel()
            carbonEnergyViewModel.setUserId(id.text.toString())
            HomeScreen(carbonEnergyViewModel.carbonEnergyUiState)
        }
    }
}