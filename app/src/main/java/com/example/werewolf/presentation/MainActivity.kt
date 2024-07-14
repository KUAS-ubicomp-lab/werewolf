/* While this template provides a good starting point for using Wear Compose, you can always
 * take a look at https://github.com/android/wear-os-samples/tree/main/ComposeStarter and
 * https://github.com/android/wear-os-samples/tree/main/ComposeAdvanced to find the most up to date
 * changes to the libraries and their usages.
 */

package com.example.werewolf.presentation

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.health.services.client.HealthServices
import androidx.health.services.client.PassiveListenerService
import androidx.health.services.client.data.DataPointContainer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.wear.compose.material.MaterialTheme
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.healthserviceslearn.presentation.HealthServicesManager
import com.example.werewolf.presentation.theme.WerewolfTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

object ViewModelHolder {
    lateinit var stepsViewModel: StepsViewModel
}

class MainActivity : ComponentActivity() {
    private lateinit var permissionLauncher: ActivityResultLauncher<String>


    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        val moonPhaseCalculator = MoonPhaseCalculator()

        ViewModelHolder.stepsViewModel = ViewModelProvider(this)[StepsViewModel::class.java]

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
            if(healthServicesManager.hasStepsCapability()){
                healthServicesManager.registerForData()
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
        permissionLauncher.launch(android.Manifest.permission.ACTIVITY_RECOGNITION)
        permissionLauncher.launch(android.Manifest.permission.BODY_SENSORS_BACKGROUND)
    }
}

class PassiveDataService : PassiveListenerService() {

    private val stepsViewModel : StepsViewModel by lazy {
        ViewModelHolder.stepsViewModel
    }

    override fun onNewDataPointsReceived(dataPoints: DataPointContainer) {
        var steps = 0;
        for (dataPoint in dataPoints.intervalDataPoints) {
            Log.i("WearApp", "Interval data point: ${dataPoint.value}")
            val value = dataPoint.value
            val intValue = when (value) {
                is Int -> value
                is Float -> value.toInt()
                is Double -> value.toInt()
                is Long -> value.toInt()
                is Short -> value.toInt()
                is Byte -> value.toInt()
                else -> {
                    Log.e(
                        "WearApp",
                        "Unsupported data point value type: ${value::class.simpleName}"
                    )
                    continue
                }
            }
            steps += intValue;
        }

        // Update ViewModel using GlobalScope
        GlobalScope.launch(Dispatchers.Main) {
            withContext(Dispatchers.Main) {
                stepsViewModel.setSteps(steps)
            }
        }
    }

}

class StepsViewModel : ViewModel() {
    private val _steps = MutableLiveData<Int>()
    val steps: LiveData<Int> get() = _steps

    fun setSteps(steps: Int) {
        _steps.value = steps
    }
}




@Composable
fun WearApp() {
    var childState by remember { mutableIntStateOf(0) }

    WerewolfTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background)
                .clickable { childState++
                           if(childState > 2){
                               childState = 0
                           }
                           Log.i("Click", "childState: $childState")},
            contentAlignment = Alignment.Center
        ) {
            if(childState == 0){
                PetScreen(modifier = Modifier)
            } else if (childState == 1){
                DaysLeftScreen(modifier = Modifier)
            } else {
                HealthScreen(modifier = Modifier)
            }
        }
    }

}
















