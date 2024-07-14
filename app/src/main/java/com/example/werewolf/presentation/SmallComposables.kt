package com.example.werewolf.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
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
    Text(
        modifier = modifier.fillMaxWidth(),
        textAlign = TextAlign.Center,
        color = Color.White,
        text = "Steps: " + stepsViewModel.steps.value.toString(),
    )
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