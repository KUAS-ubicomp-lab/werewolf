package com.example.werewolf.presentation

import android.content.SharedPreferences

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


