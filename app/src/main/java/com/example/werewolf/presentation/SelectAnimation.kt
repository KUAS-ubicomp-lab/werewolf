package com.example.werewolf.presentation

import com.example.werewolf.R

fun selectAnimation() : Int {
    val moonPhase = moonPhase()

    val animation = when (moonPhase) {
        "Waning Crescent" -> R.drawable.fighting_animation
        "Waning Gibbous" -> R.drawable.lost
        "Waxing Gibbous" -> R.drawable.won1
        "Waxing Crescent" -> R.drawable.eating1
        "New Moon" -> R.drawable.sleeping1
        "Full Moon" -> R.drawable.lost
        "First Quarter" -> R.drawable.training1
        "Third Quarter" -> R.drawable.training2
        else -> {R.drawable.fighting2}
    }

    return animation
}