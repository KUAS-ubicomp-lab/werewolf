package com.example.werewolf.presentation

import java.time.Instant
import java.time.ZoneId

//Gets an date in the format needed for the app.
fun formattedDate(instant : Instant) : String{
    val zonedDateTime = instant.atZone(ZoneId.systemDefault())
    val year = zonedDateTime.getYear().toString()
    val month = zonedDateTime.getMonthValue().toString()
    val day = zonedDateTime.getDayOfMonth().toString()
    val dateString = "$year-$month-$day"
    return dateString
}

//Gets a time in the format needed for the app
fun formattedTime(instant : Instant) : String{
    val instant = Instant.now()
    val zonedDateTime = instant.atZone(ZoneId.systemDefault())
    val hour = zonedDateTime.hour.toString()
    val minute = zonedDateTime.minute.toString()
    val second = zonedDateTime.second.toString()
    val timeString = "$hour:$minute:$second"
    return timeString
}