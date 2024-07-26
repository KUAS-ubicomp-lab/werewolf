package com.example.werewolf.presentation

import android.util.Log
import dev.jamesyox.kastro.luna.LunarEvent
import dev.jamesyox.kastro.luna.LunarEventSequence
import dev.jamesyox.kastro.luna.LunarPhase
import dev.jamesyox.kastro.luna.LunarPhaseSequence
import dev.jamesyox.kastro.luna.calculateLunarState
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toJavaInstant
import kotlinx.datetime.toJavaZoneId
import kotlinx.datetime.toKotlinInstant
import kotlinx.datetime.toLocalDateTime
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import kotlin.time.Duration.Companion.days

class MoonPhaseCalculator {

    private var stringPhase = ""
    private val tz = TimeZone.currentSystemDefault()
    private val now = kotlinx.datetime.Clock.System.now()
    private val localDateTime = now.toLocalDateTime(tz)
    private val beginDay = LocalDateTime.of(localDateTime.year, localDateTime.month, localDateTime.dayOfMonth, 0, 0)
    private val endDay = beginDay.plusDays(1)
    private val beginInstant = beginDay.atZone(tz.toJavaZoneId()).toInstant().toKotlinInstant()
    private val endInstant = endDay.atZone(tz.toJavaZoneId()).toInstant().toKotlinInstant()
    private val beginPhase = beginInstant.calculateLunarState(35.0, 135.0).phase
    private val endPhase = endInstant.calculateLunarState(35.0, 135.0).phase
    private val beginAngle = beginPhase.midpointAngle
    private val endAngle = endPhase.midpointAngle

    fun moonPhase() : String {
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
        val nextFullMoon = getMoonSequence().get(0)
        val fullMoonInstant = nextFullMoon.time
        val duration = fullMoonInstant - beginInstant
        return duration.inWholeDays.toInt()
    }

    fun getMoonSequence() : List<LunarEvent> {
         return LunarEventSequence(
            start = beginInstant,
            latitude = 35.0,
            longitude = 135.0,
            requestedLunarEvents = listOf(LunarEvent.PhaseEvent.FullMoon),
            limit = 30.days
        ).toList()
    }

    fun getLastFullMoon() : Instant {
        val oneMonthAgo = ZonedDateTime.ofInstant(Instant.now(), ZoneId.systemDefault())
            .minusMonths(1)
            .toInstant().toKotlinInstant()

        val moonSequence = LunarEventSequence(
            start = oneMonthAgo,
            latitude = 35.0,
            longitude = 135.0,
            requestedLunarEvents = listOf(LunarEvent.PhaseEvent.FullMoon),
            limit = 30.days
        ).toList()

        return moonSequence.get(0).time.toJavaInstant()
    }


}

