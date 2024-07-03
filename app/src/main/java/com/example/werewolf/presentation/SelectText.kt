package com.example.werewolf.presentation

import android.util.Log

fun selectText(selection: Int) : String {
    if(selection == 0){
        return moonPhase()
    }else if (selection == 1) {
        if(moonPhase() == "Full Moon"){
            return "Fight!"
        }else{
            Log.d("TAG","hello2!")
            return daysUntilFullMoon().toString() + " Days Remaining"
        }
    } else {
        return "Health Data Here"
    }
}
