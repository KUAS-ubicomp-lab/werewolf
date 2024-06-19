package com.example.werewolf.presentation

fun selectText(selection: Boolean) : String {
    if(selection){
        return moonPhase()
    }else {
        return daysUntilFullMoon().toString() + " days remaining"
    }
}
