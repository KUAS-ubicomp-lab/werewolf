package com.example.werewolf.presentation

import android.graphics.SurfaceTexture
import android.view.Surface
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.TimeText
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
fun DisplayHealthData(modifier: Modifier, stepsViewModel: StepsViewModel){

    val currentSteps = stepsViewModel.steps.value
    var totalSteps = 0
    if(currentSteps != null){
       totalSteps = stepsViewModel.steps.value.toString().toInt() + getSteps();
    }

    LazyColumn (modifier = modifier.heightIn(max = 100.dp)) {
        item {
            HealthDataItem(dataType = "Steps", dataValue = totalSteps)
        }
        item {
            HealthDataItem(dataType = "HR", dataValue = "N/A")
        }
        item {
            HealthDataItem(dataType = "Sleep", dataValue = "N/A")
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