package com.example.carbonenergy.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carbonenergy.model.User
import com.example.carbonenergy.network.CarbonEnergyApi
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface CarbonEnergyUiState {
    data class Success(val user: String) : CarbonEnergyUiState
    object Error : CarbonEnergyUiState
    object Loading : CarbonEnergyUiState
}

class CarbonEnergyViewModel : ViewModel() {
    var carbonEnergyUiState: CarbonEnergyUiState by mutableStateOf(CarbonEnergyUiState.Loading)
        private set

    fun setUserId(id: String) {
        getUserInfo(id)
    }

    private fun getUserInfo(userId: String) {
        viewModelScope.launch {
            carbonEnergyUiState = try{
                val user = CarbonEnergyApi.retrofitService.getUser(userId)
                CarbonEnergyUiState.Success(user)
            } catch (e: IOException) {
                CarbonEnergyUiState.Error
            } catch (e: HttpException) {
                CarbonEnergyUiState.Error
            }
        }
    }
}