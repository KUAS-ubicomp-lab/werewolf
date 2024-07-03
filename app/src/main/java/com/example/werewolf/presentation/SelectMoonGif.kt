package com.example.werewolf.presentation

import com.example.werewolf.R

fun selectMoonGif() : Int {
    val moonPhase = moonPhase()

    val animation = when (moonPhase) {
        "Full Moon" -> R.drawable.full_moon
        else -> {
            R.drawable.moon_phases}
    }

    return animation
}