package com.example.werewolf.presentation

import android.util.Log



fun selectText(selection: Int) : String {
    val moonPhaseCalculator : MoonPhaseCalculator = MoonPhaseCalculator()
    if(selection == 0){
        return  moonPhaseCalculator.moonPhase()
    }else if (selection == 1) {
        if( moonPhaseCalculator.moonPhase() == "Full Moon"){
            return "Fight!"
        }else{
            Log.d("TAG","hello2!")
            return moonPhaseCalculator.daysUntilFullMoon().toString() + " Days Remaining"
        }
    } else {
        return "Health Data Here"
    }
}
