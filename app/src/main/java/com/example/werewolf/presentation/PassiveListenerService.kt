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
        Log.i("WearApp", "Receiving Sleep Data")

        val sleepData = getSharedPreferences("sleep_data", Context.MODE_PRIVATE)
        val sleepEditor = sleepData.edit()
        val countData = getSharedPreferences("counter_data", Context.MODE_PRIVATE)
        val counterEditor = countData.edit()

        val userActivityState: UserActivityState = info.userActivityState
        if (userActivityState == UserActivityState.USER_ACTIVITY_ASLEEP) {
            if(sleepData.getString("sleep_start${formattedDate(Instant.now())}0", "") == ""){
                counterEditor.putInt("start", 0).apply()
                sleepEditor.putString("sleep_start${formattedDate(Instant.now())}0", formattedTime(Instant.now())).apply()
            } else {
                counterEditor.putInt("start", countData.getInt("start", 0) + 1).apply()
                sleepEditor.putString("sleep_start${formattedDate(Instant.now())}${countData.getInt("start", 0)}", formattedTime(Instant.now())).apply()
            }
        } else {
            if(sleepData.getString("sleep_end${formattedDate(Instant.now())}0", "") == ""){
                counterEditor.putInt("end", 0).apply()
                sleepEditor.putString("sleep_end${formattedDate(Instant.now())}0", formattedTime(Instant.now())).apply()
            } else {
                counterEditor.putInt("end", countData.getInt("end", 0) + 1).apply()
                sleepEditor.putString("sleep_end${formattedDate(Instant.now())}${countData.getInt("end", 0)}", formattedTime(Instant.now())).apply()
            }
        }
    }

    override fun onNewDataPointsReceived(dataPoints: DataPointContainer) {
        Log.i("WearApp", "Receiving Step Data")
        var steps = 0;
        val sharedPref = getSharedPreferences("steps_data", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        for (dataPoint in dataPoints.intervalDataPoints) {
            val value = dataPoint.value
            if(value is Long){
                steps = value.toInt()
            } else {
                Log.e("WearApp", "Step value is not an integer.")
            }
        }
        editor.putInt("steps${formattedDate(Instant.now())}", steps)
        editor.apply()

        // Update ViewModel using GlobalScope
        GlobalScope.launch(Dispatchers.Main) {
            withContext(Dispatchers.Main) {
                healthViewModel.setSteps(steps)
                healthViewModel.setDailySteps(processStepData(sharedPref = getSharedPreferences("steps_data", Context.MODE_PRIVATE)))
            }
        }
    }

}