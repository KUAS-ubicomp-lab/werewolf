package com.example.werewolf.presentation

import android.content.SharedPreferences
import java.time.Duration
import java.time.Instant
import kotlin.time.Duration.Companion.days

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

fun getSleepTimesByDay(day : String, sharedPref: SharedPreferences) : List<String>{
    val timesList: MutableList<String> = mutableListOf()
    for(entry in sharedPref.all.entries){
        if(entry.key.contains(day)){
            timesList.add(entry.value.toString())
        }
    }
    return timesList
}

fun sortTimes(times : List<String>) : List<String>{
    val timeComparator = Comparator<String> { time1, time2 -> compareTimes(time1, time2) }
    return times.sortedWith(timeComparator)
}

fun compareTimes(time1: String, time2: String): Int {
    val time1Split = time1.split(":").map { it.toInt() }
    val time2Split = time2.split(":").map { it.toInt() }

    return when {
        time1Split[0] != time1Split[0] -> time1Split[0] - time2Split[0]
        time1Split[1] != time2Split[1] -> time1Split[1] - time2Split[1]
        else -> time1Split[2] - time2Split[2]
    }
}
