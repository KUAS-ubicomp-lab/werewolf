package com.example.werewolf.presentation

fun selectText(selection: Boolean) : String {
    if(selection){
        return moonPhase()
    }else {
        if(moonPhase() == "FullMoon"){
            return "Full Moon!"
        }else{
            return daysUntilFullMoon().toString() + " Days Remaining"
        }

    }
}
