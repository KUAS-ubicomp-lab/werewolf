package com.example.werewolf.presentation

import android.util.Log
import dev.jamesyox.kastro.luna.LunarPhase
import dev.jamesyox.kastro.luna.calculateLunarState
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toJavaZoneId
import kotlinx.datetime.toKotlinInstant
import kotlinx.datetime.toLocalDateTime
import java.time.LocalDateTime

private var beginAngle = 0.0;

fun moonPhase() : String {
    var stringPhase = ""
    val tz = TimeZone.currentSystemDefault()
    val now = kotlinx.datetime.Clock.System.now()
    val localDateTime = now.toLocalDateTime(tz)
    val beginDay = LocalDateTime.of(localDateTime.year, localDateTime.month, localDateTime.dayOfMonth, 0, 0)
    val endDay = beginDay.plusDays(1)

    val beginInstant = beginDay.atZone(tz.toJavaZoneId()).toInstant().toKotlinInstant()
    val endInstant = endDay.atZone(tz.toJavaZoneId()).toInstant().toKotlinInstant()

    val beginPhase = beginInstant.calculateLunarState(35.0, 135.0).phase
    val endPhase = endInstant.calculateLunarState(35.0, 135.0).phase

    beginAngle = beginPhase.midpointAngle
    val endAngle = endPhase.midpointAngle

    if(beginAngle > endAngle){
        stringPhase = "New Moon"
    } else if (beginAngle <= 90 && endAngle > 90){
        stringPhase = "First Quarter"
    } else if (beginAngle <= 180 && endAngle > 180){
        stringPhase = "Full Moon"
    }  else if (beginAngle <= 270 && endAngle > 270){
        stringPhase = "Third Quarter"
    } else {
        stringPhase = when (beginPhase) {
            LunarPhase.Intermediate.WaningCrescent -> "Waning Crescent"
            LunarPhase.Intermediate.WaningGibbous -> "Waning Gibbous"
            LunarPhase.Intermediate.WaxingGibbous -> "Waxing Gibbous"
            LunarPhase.Intermediate.WaxingCrescent -> "Waxing Crescent"
        }
    }
    return stringPhase
}

fun daysUntilFullMoon() : Int {
    var days = 0.0
    Log.d("angle", beginAngle.toString())
    if(beginAngle > 180){
        days = ((beginAngle - 180) / 12.2) + 15.0
    }else{
        days = (180 - beginAngle) / 12.2
    }
    return days.toInt()
}