package com.example.werewolf.presentation

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.Text
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import coil.size.Size


@Composable
fun DisplayMoonGif(modifier: Modifier){
    val context = LocalContext.current
    val imageLoader = ImageLoader.Builder(context)
        .components {
            add(ImageDecoderDecoder.Factory())
        }
        .build()
    val selectedAnimation = selectMoonGif()
    Image(
        painter = rememberAsyncImagePainter(
            ImageRequest.Builder(context).data(data = selectedAnimation).apply(block = {
                size(Size.ORIGINAL)
            }).build(), imageLoader = imageLoader
        ),
        contentDescription = null,
        modifier = modifier.fillMaxWidth()
    )
}

@Composable
fun DisplayHealthData(modifier: Modifier, healthViewModel: HealthViewModel){
    val steps = healthViewModel.steps.value
    val currentSleep = healthViewModel.sleep.value
    val dailySteps = healthViewModel.dailySteps.value
    LazyColumn (modifier = modifier.heightIn(max = 100.dp)) {
        item {
            if(dailySteps != null){
                HealthDataItem(dataType = "Steps Average", dataValue = dailySteps)
            }
        }
        item {
            if(steps != null){
                HealthDataItem(dataType = "Steps Today", dataValue = steps)
            }
        }
        item {
            if (currentSleep != null) {
                HealthDataItem(dataType = "Sleep", dataValue = currentSleep)
            }
        }
        item {
            HealthDataItem(dataType = "Distance", dataValue = "N/A")
        }
        item {
            HealthDataItem(dataType = "Calories", dataValue = "N/A")
        }
        item {
            HealthDataItem(dataType = "Distance", dataValue = "N/A")
        }
    }
}

@Composable
fun DisplayDaysLeft(modifier: Modifier){
    var moonPhaseCalculator : MoonPhaseCalculator = MoonPhaseCalculator()
    var selectedText = moonPhaseCalculator.daysUntilFullMoon().toString() + " Days Remaining"

    Text(
        modifier = modifier.fillMaxWidth(),
        textAlign = TextAlign.Center,
        color = Color.White,
        text = selectedText,
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun DisplayPetGif(
    modifier: Modifier = Modifier
) {

    val context = LocalContext.current
    val imageLoader = ImageLoader.Builder(context)
        .components {
            add(ImageDecoderDecoder.Factory())
        }
        .build()
    val selectedAnimation = selectPetGif()
    Image(
        painter = rememberAsyncImagePainter(
            ImageRequest.Builder(context).data(data = selectedAnimation).apply(block = {
                size(Size.ORIGINAL)
            }).build(), imageLoader = imageLoader
        ),
        contentDescription = null,
        modifier = modifier.fillMaxWidth()
    )
}

@Composable
fun MoonPhaseText(modifier: Modifier) {
    val moonPhaseCalculator : MoonPhaseCalculator = MoonPhaseCalculator()

    var selectedText = moonPhaseCalculator.moonPhase()

    Text(
        modifier = modifier.fillMaxWidth(),
        textAlign = TextAlign.Center,
        color = Color.White,
        text = selectedText,
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun HealthDataHeader(modifier: Modifier) {
    Text(
        modifier = modifier.fillMaxWidth(),
        textAlign = TextAlign.Center,
        color = Color.White,
        text = "Health Data",
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun HealthDataItem(dataType: String, dataValue: Any) {
    Box(
        modifier = Modifier
            .width(170.dp)
            .padding(2.dp)
            .border(1.dp, Color.White, shape = RoundedCornerShape(8.dp)) // Add rounded border
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp), // Padding inside the border
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "$dataType",
                color = Color.White,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Start
            )
            Text(
                text = "$dataValue",
                color = Color.White,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.End
            )
        }
    }
}