/* While this template provides a good starting point for using Wear Compose, you can always
 * take a look at https://github.com/android/wear-os-samples/tree/main/ComposeStarter and
 * https://github.com/android/wear-os-samples/tree/main/ComposeAdvanced to find the most up to date
 * changes to the libraries and their usages.
 */

package com.example.werewolf.presentation

import android.content.Context
import android.health.connect.HealthConnectManager
import android.health.connect.datatypes.Record
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.TimeText
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import coil.size.Size
import com.example.werewolf.presentation.theme.WerewolfTheme
import com.example.werewolf.R
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.impl.converters.datatype.RECORDS_TYPE_NAME_MAP
import androidx.health.connect.client.permission.HealthPermission
import androidx.health.connect.client.readRecord
import androidx.health.connect.client.records.BodyTemperatureRecord
import androidx.health.connect.client.request.ReadRecordsRequest
import androidx.health.connect.client.time.TimeRangeFilter
import androidx.health.services.client.HealthServices
import androidx.health.services.client.PassiveListenerService
import androidx.health.services.client.data.DataPointContainer
import androidx.health.services.client.data.ExerciseCapabilities
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.health.services.client.data.ExerciseType
import androidx.lifecycle.lifecycleScope
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.ScalingLazyListScope
import androidx.wear.compose.foundation.lazy.items
import androidx.wear.compose.material.Button
import com.example.healthserviceslearn.presentation.HealthServicesManager
import java.time.Instant


class MainActivity : ComponentActivity() {
    private lateinit var permissionLauncher: ActivityResultLauncher<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)

        val healthServicesManager = HealthServicesManager(HealthServices.getClient(this))

        permissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { result ->
                when (result) {
                    true -> {
                        Log.i("WearApp", "Body sensors permission granted")
                    }
                    false -> {
                        Log.i("WearApp", "Body sensors permission not granted")
                    }
                }
            }


        lifecycleScope.launch {
            val hasHeartRateCapability = healthServicesManager.hasHeartRateCapability()
            Log.i("WearApp", "hasHeartRateCapability: $hasHeartRateCapability")
            if (hasHeartRateCapability) {
                healthServicesManager.registerForHeartRateData()
            }
        }

        setTheme(android.R.style.Theme_DeviceDefault)

        setContent {
            WearApp()
        }
    }

    override fun onStart() {
        super.onStart()
        permissionLauncher.launch(android.Manifest.permission.BODY_SENSORS)
        permissionLauncher.launch(android.Manifest.permission.BODY_SENSORS_BACKGROUND)
    }
}

class PassiveDataService : PassiveListenerService() {
    override fun onNewDataPointsReceived(dataPoints: DataPointContainer) {
        for (dataPoint in dataPoints.sampleDataPoints) {
            val heartRate = dataPoint.value
            Log.i("WearApp", "Received heart rate: $heartRate")
        }
        Log.i("WearApp", "receiving data")
    }
}




@Composable
fun WearApp() {

    var childState by remember { mutableStateOf<Boolean>(true) }
    WerewolfTheme {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background)
                .clickable { childState = !childState },
            contentAlignment = Alignment.Center
        ) {
            TimeText()
            MoonPhaseText(modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(0.dp, 40.dp), state = childState)
            DisplayGif(modifier = Modifier
                .size(110.dp)
                .align(Alignment.BottomCenter)
                .offset(0.dp, (-10).dp), state = childState)

        }
    }
}



@Composable
fun MoonPhaseText(modifier: Modifier, state: Boolean) {

    var selectedText = selectText(selection = state)
    
    Text(
        modifier = modifier.fillMaxWidth(),
        textAlign = TextAlign.Center,
        color = Color.White,
        text = selectedText,
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun DisplayGif(
    modifier: Modifier = Modifier, state: Boolean
    ) {
    val context = LocalContext.current
    val imageLoader = ImageLoader.Builder(context)
        .components {
            add(ImageDecoderDecoder.Factory())
        }
        .build()

    val selectedAnimation = selectAnimation(state)

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

@Preview(device = Devices.WEAR_OS_SMALL_ROUND, showSystemUi = true)
@Composable
fun DefaultPreview() {
    WearApp()
}