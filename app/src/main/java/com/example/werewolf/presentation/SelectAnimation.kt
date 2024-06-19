package com.example.werewolf.presentation

import com.example.werewolf.R

private val animationsMap: MutableMap<String, List<Int>> = mutableMapOf(
    "Waning Crescent" to listOf(R.drawable.fighting2, R.drawable.moon_phases),
    "Waning Gibbous" to listOf(R.drawable.lost, R.drawable.moon_phases),
    "Waxing Gibbous" to listOf(R.drawable.won1, R.drawable.moon_phases),
    "Waxing Crescent" to listOf(R.drawable.training1, R.drawable.moon_phases),
    "New Moon" to listOf(R.drawable.sleeping1, R.drawable.moon_phases),
    "Full Moon" to listOf(R.drawable.won1, R.drawable.moon_phases),
    "First Quarter" to listOf(R.drawable.lost, R.drawable.moon_phases),
    "Third Quarter" to listOf(R.drawable.fighting_animation, R.drawable.moon_phases),
    "Default" to listOf(R.drawable.won1, R.drawable.moon_phases)
)

fun selectAnimation(selection: Boolean) : Int {
    val moonPhase = moonPhase()
    if(selection){
        return animationsMap[moonPhase]?.get(0) ?: animationsMap["Default"]?.get(0) ?: animationsMap["Default"]!![0]
    }else {
        return animationsMap[moonPhase]?.get(1) ?: animationsMap["Default"]?.get(1) ?: animationsMap["Default"]!![1]
    }
}

