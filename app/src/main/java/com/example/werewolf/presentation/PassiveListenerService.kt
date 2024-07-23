package com.example.werewolf.presentation

import android.content.Context
import android.util.Log
import androidx.health.services.client.PassiveListenerService
import androidx.health.services.client.data.DataPointContainer
import androidx.health.services.client.data.UserActivityInfo
import androidx.health.services.client.data.UserActivityState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.Instant
import java.time.ZoneId

class PassiveDataService : PassiveListenerService() {

    private val healthViewModel : HealthViewModel by lazy {
        ViewModelHolder.healthViewModel
    }


    override fun onUserActivityInfoReceived(info: UserActivityInfo) {
        val sharedPref = getSharedPreferences("health_data", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        val userActivityState: UserActivityState = info.userActivityState
        if (userActivityState == UserActivityState.USER_ACTIVITY_ASLEEP) {
            editor.putStringSet("sleep_start", setOf(formattedDate(), formattedTime())).apply()

        } else {
            editor.putStringSet("sleep_start", setOf(formattedDate(), formattedTime())).apply()
        }
    }

    override fun onNewDataPointsReceived(dataPoints: DataPointContainer) {
        var steps = 0;
        for (dataPoint in dataPoints.intervalDataPoints) {
            Log.i("WearApp", "Interval data point: ${dataPoint.value}")
            val value = dataPoint.value
            if(value is Long){
                steps = value.toInt()
            } else {
                Log.e("WearApp", "Step value is not an integer.")
            }
        }
        val sharedPref = getSharedPreferences("health_data", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        for (dataPoint in dataPoints.sampleDataPoints) {
            editor.putInt("steps${System.currentTimeMillis()}", steps)
        }
        editor.apply()

        // Update ViewModel using GlobalScope
        GlobalScope.launch(Dispatchers.Main) {
            withContext(Dispatchers.Main) {
                healthViewModel.setSteps(steps)
            }
        }
    }

}