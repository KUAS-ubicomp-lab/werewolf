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
    var hour = zonedDateTime.hour.toString()
    if(hour.length == 1){
        hour = "0$hour"
    }
    var minute = zonedDateTime.minute.toString()
    if(minute.length == 1){
        minute = "0$minute"
    }
    var second = zonedDateTime.second.toString()
    if(second.length == 1){
        second = "0$second"
    }
    val timeString = "$hour:$minute:$second"
    return timeString
}