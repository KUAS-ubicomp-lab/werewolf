package com.example.werewolf.presentation

import android.content.SharedPreferences
import android.util.Log
import kotlinx.datetime.Instant
import java.time.Duration
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

private var allSteps = 0

fun processHealthData(sharedPref: SharedPreferences) {
    var stepRecords = 0
    var sleep = 0
    val sleepMap = mutableMapOf<String, MutableSet<String>>()

    sharedPref.all.forEach {
        if (it.key.contains("steps")) {
            val stepsVal = it.value
            if(stepsVal is Int){
                allSteps += stepsVal
                stepRecords++
            }else{
                Log.e("WearApp", "Steps value is not an integer")
            }
        }
        if (it.key.contains("sleep")) {
//            var sleepValue = it.value
//            if(sleepValue is Set<*>){
//                val day = sleepValue.elementAt(0);
//                val time = sleepValue.elementAt(1);
//                if(day is String && time is String) {
//                    val timesSet = sleepMap.getOrPut(day) { mutableSetOf() }
//                    timesSet.add(time)
//                }
//            } else {
//                Log.e("WearApp", "Sleep value is not a string set")
//            }
        }

        if(stepRecords > 0){
            allSteps /= stepRecords
        }

    }

//    for (day in sleepMap.keys) {
//        val times = sleepMap[day]!!.sorted() // Sort times to ensure they are in the correct order
//        val localTimes = times.map { LocalTime.parse(it) } // Convert strings to LocalTime
//        var totalSleepMinutes : Long = 0
//        for (i in 0 until localTimes.size step 2) {
//            if (i + 1 < localTimes.size) {
//                val sleepTime = localTimes[i]
//                val wakeTime = localTimes[i + 1]
//                val duration = Duration.between(sleepTime, wakeTime)
//                totalSleepMinutes += duration.toMinutes()
//            }
//        }
//    }


}

fun getSteps(): Int {
    return allSteps;
}
