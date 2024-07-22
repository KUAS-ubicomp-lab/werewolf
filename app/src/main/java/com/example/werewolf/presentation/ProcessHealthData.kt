package com.example.werewolf.presentation

import android.content.SharedPreferences

fun processHealthData(sharedPref: SharedPreferences) {
    var allSteps = 0
    var sleep = 0

    sharedPref.all.forEach {
        if (it.key.contains("steps")) {
            allSteps += it.value.toString().toInt()
        }
        if (it.key.contains("sleep")) {

        }
    }
}
