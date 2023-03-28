package com.example.carbonenergy.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.carbonenergy.model.User

@Composable
fun HomeScreen(carbonEnergyUiState: CarbonEnergyUiState, modifier: Modifier = Modifier) {
    when (carbonEnergyUiState) {
        is CarbonEnergyUiState.Success -> ResultScreen(carbonEnergyUiState.user, modifier)
        is CarbonEnergyUiState.Error -> ErrorScreen(modifier)
        is CarbonEnergyUiState.Loading -> LoadingScreen(modifier)
    }
}

@Composable
fun ResultScreen(user: User, modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {
         Text("Hi " + user.user_id + "\n" + "You have " + user.energy + " points!")
    }
}

@Composable
fun ErrorScreen(modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {
        Text("Error to fetch user data")
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {
        Text("Loading...")
    }
}