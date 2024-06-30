package com.example.healthserviceslearn.presentation

import android.util.Log
import androidx.concurrent.futures.await
import androidx.health.services.client.HealthServicesClient
import androidx.health.services.client.data.DataType
import androidx.health.services.client.data.PassiveListenerConfig
import com.example.werewolf.presentation.PassiveDataService

class HealthServicesManager(healthServicesClient: HealthServicesClient) {
    private val passiveMonitoringClient = healthServicesClient.passiveMonitoringClient
    private val dataTypes = setOf(DataType.HEART_RATE_BPM)

    suspend fun hasHeartRateCapability(): Boolean {
        val capabilities = passiveMonitoringClient.getCapabilitiesAsync().await()
        return (DataType.HEART_RATE_BPM in capabilities.supportedDataTypesPassiveMonitoring)
    }

    suspend fun registerForHeartRateData() {
        val passiveListenerConfig = PassiveListenerConfig.builder()
            .setDataTypes(dataTypes)
            .build()

        Log.i("WearApp", "registering for heart rate data")

        passiveMonitoringClient.setPassiveListenerServiceAsync(
            PassiveDataService::class.java,
            passiveListenerConfig
        ).await()
    }

    suspend fun unregisterForHeartRateData() {
        passiveMonitoringClient.clearPassiveListenerServiceAsync().await()
    }

}