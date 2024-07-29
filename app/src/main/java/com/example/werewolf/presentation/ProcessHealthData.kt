package com.example.werewolf.presentation

import android.content.SharedPreferences
import android.util.Log
import java.time.Duration
import java.time.Instant
import java.time.ZoneId
import kotlin.time.Duration.Companion.days

// returns the average number of steps per day
fun processStepData(sharedPref: SharedPreferences) : Int {
    if(sharedPref.all.isEmpty()){
        return 0
    }
    val dayMap = mutableMapOf<String, Int>()
    for (entry in sharedPref.all.entries) {
        val currentValue = dayMap[entry.key]
        val thisValue = entry.value
        if(thisValue is Int){
            if(currentValue is Int){
                if(thisValue > currentValue){
                    dayMap[entry.key] = thisValue
                }
            } else {
                dayMap[entry.key] = thisValue
            }
        }
    }
    return dayMap.values.average().toInt()
}

//returns a list of all the days since the last full moon, not including
// the day of the moon or the current day
fun getDaysSinceLastFullMoon() : List<String> {
    val moonPhaseCalculator = MoonPhaseCalculator()
    val lastFullMoon = moonPhaseCalculator.getLastFullMoon()
    val daysList: MutableList<String> = mutableListOf()
    daysList.add(formattedDate(lastFullMoon))
    var i : Long = 1
    while(true){
        if(formattedDate(lastFullMoon.plus(Duration.ofDays(i))) == formattedDate(Instant.now())) {
            return daysList
        }
        daysList.add(formattedDate(lastFullMoon.plus(Duration.ofDays(i))))
        i++
    }
}

//returns a list of all the sleep times for a given day
fun getSleepTimesByDay(day : String, sharedPref: SharedPreferences) : List<String>{
    val timesList: MutableList<String> = mutableListOf()
    for(entry in sharedPref.all.entries){
        if(entry.key.contains(day)){
            timesList.add(entry.value.toString())
        }
    }
    return timesList
}

//sorts a list of times in the format "hh:mm:ss"
fun sortTimes(times : List<String>) : List<String>{
    val timeComparator = Comparator<String> { time1, time2 -> compareTimes(time1, time2) }
    return times.sortedWith(timeComparator)
}


// comparator for times in the format "hh:mm:ss"
fun compareTimes(time1: String, time2: String): Int {
    val time1Split = time1.split(":").map { it.toInt() }
    val time2Split = time2.split(":").map { it.toInt() }

    return when {
        time1Split[0] != time1Split[0] -> time1Split[0] - time2Split[0]
        time1Split[1] != time2Split[1] -> time1Split[1] - time2Split[1]
        else -> time1Split[2] - time2Split[2]
    }
}


//returns the average hours of sleep per day
fun processSleepData(sharedPref: SharedPreferences) : Float{
    var totalHours = 0.0f
    var totalDays = 0
    if(sharedPref.all.isEmpty()){
        return 0.0f
    }
    for (day in getDaysSinceLastFullMoon()){
        var sleepToday = 0.0f
        val sortedTimes = sortTimes(getSleepTimesByDay(day, sharedPref))
        var sleepMode = -1
        if(sortedTimes.isNotEmpty()){
            totalDays++;
            sleepMode = getSleepModeFromDateTime(day, sortedTimes[0], sharedPref)
        }
        if(sleepMode == 0){
            sleepToday = sleepTimeTodayStartSleep(sortedTimes)
        } else if(sleepMode == 1) {
            sleepToday = sleepTimeTodayStartWakeup(sortedTimes)
        }
        totalHours += sleepToday
    }
    return totalHours / totalDays
}


//given a day and a time, returns 0 if this sleep was a sleep start or 1 if it was a sleep end
fun getSleepModeFromDateTime(day : String, time: String, sharedPref: SharedPreferences) : Int{
    for (entry in sharedPref.all.entries){
        if(entry.key.contains(day) && entry.value.toString().contains(time)){
            if(entry.key.contains("start")){
                return 0
            }else{
                return 1
            }
        }
    }
    return -1
}

//returns the difference of time2 - time1, both must be in string format "hh:mm:ss"
fun timeDifference(time1: String, time2: String): Float{
    val time2Split = time2.split(":")
    val time1Split = time1.split(":")

    val hourDifference = time2Split[0].toInt() - time1Split[0].toInt()
    val minuteDifference = (time2Split[1].toInt() - time1Split[1].toInt())
    val secondDifference = (time2Split[2].toInt() - time1Split[2].toInt())
    val totalDifference = hourDifference + minuteDifference.toFloat()/60 + secondDifference.toFloat()/3600

    return totalDifference
}


//given a list of sleep times, gets the amount of time spent sleeping that day if the first
// sleep time is a start sleep
fun sleepTimeTodayStartSleep(times: List<String>): Float{
    var sleepTime = 0.0f
    var i = 0
    if(times.size == 1){
        return timeDifference(times[0], "23:59:59")
    }
    if(times.size % 2 != 0){
        for (i in 0  until times.size -1 step 2){
            sleepTime += timeDifference(times[i], times[i+1])
        }
        sleepTime += timeDifference(times[times.size - 1], "23:59:59")
    } else {
        for (i in 0  until times.size -1 step 2){
            sleepTime += timeDifference(times[i], times[i+1])
        }
    }
    return sleepTime
}


// same as before but if it's a wakeup
fun sleepTimeTodayStartWakeup(times: List<String>) : Float{
    var sleepTime = timeDifference("00:00:00", times[0])
    if(times.size == 1){
        return sleepTime
    }
    if(times.size % 2 != 0){
        for (i in 1  until times.size -1 step 2){
            sleepTime += timeDifference(times[i], times[i+1])
        }
    } else {
        for (i in 1  until times.size -1 step 2){
            sleepTime += timeDifference(times[i], times[i+1])
        }
        sleepTime += timeDifference(times[times.size - 1], "23:59:59")
    }
    return sleepTime
}


