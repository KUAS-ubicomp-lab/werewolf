package com.example.werewolf.presentation

import android.util.Log

fun selectText(selection: Boolean) : String {
    if(selection){
        return moonPhase()
    }else {
        if(moonPhase() == "Full Moon"){
            return "Fight!"
        }else{
            Log.d("TAG","hello2!")
            return daysUntilFullMoon().toString() + " Days Remaining"
        }

    }
}
