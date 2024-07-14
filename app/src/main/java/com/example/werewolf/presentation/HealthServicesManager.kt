package com.example.healthserviceslearn.presentation

import android.util.Log
import androidx.concurrent.futures.await
import androidx.health.services.client.HealthServicesClient
import androidx.health.services.client.data.DataType
import androidx.health.services.client.data.PassiveListenerConfig
import com.example.werewolf.presentation.PassiveDataService

class HealthServicesManager(healthServicesClient: HealthServicesClient) {
    private val passiveMonitoringClient = healthServicesClient.passiveMonitoringClient
    private val dataTypes = setOf(DataType.HEART_RATE_BPM, DataType.STEPS)

    suspend fun hasStepsCapability(): Boolean {
        val capabilities = passiveMonitoringClient.getCapabilitiesAsync().await()
        return (DataType.STEPS in capabilities.supportedDataTypesPassiveMonitoring)
    }

    suspend fun registerForData() {
        val passiveListenerConfig = PassiveListenerConfig.builder()
            .setDataTypes((dataTypes))
            .build()

        passiveMonitoringClient.setPassiveListenerServiceAsync(
            PassiveDataService::class.java,
            passiveListenerConfig
        ).await()
    }

    suspend fun unregisterForData() {
        passiveMonitoringClient.clearPassiveListenerServiceAsync().await()
    }

}