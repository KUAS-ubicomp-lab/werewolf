package com.example.werewolf.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.TimeText

@Composable
fun DaysLeftScreen(modifier: Modifier){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
        contentAlignment = Alignment.Center
    ) {
        TimeText()
        DisplayDaysLeft(modifier = Modifier
            .align(Alignment.TopCenter)
            .offset(0.dp, 40.dp))
        DisplayMoonGif(modifier = Modifier
            .size(110.dp)
            .align(Alignment.BottomCenter)
            .offset(0.dp, (-10).dp))
    }
}

@Composable
fun PetScreen(modifier: Modifier){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent),
        contentAlignment = Alignment.Center
    ) {
        TimeText()
        MoonPhaseText(modifier = Modifier
            .align(Alignment.TopCenter)
            .offset(0.dp, 40.dp))
        DisplayPetGif(modifier = Modifier
            .size(110.dp)
            .align(Alignment.BottomCenter)
            .offset(0.dp, (-10).dp))
    }
}

@Composable
fun HealthScreen(modifier: Modifier){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
        contentAlignment = Alignment.Center
    ) {
        TimeText()
        HealthDataHeader(modifier = Modifier
            .align(Alignment.TopCenter)
            .offset(0.dp, 40.dp))
        DisplayHealthData(modifier = Modifier
            .align(Alignment.BottomCenter)
            .offset(0.dp, -20.dp), healthViewModel = ViewModelHolder.healthViewModel)
    }
}