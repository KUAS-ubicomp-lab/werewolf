/* While this template provides a good starting point for using Wear Compose, you can always
 * take a look at https://github.com/android/wear-os-samples/tree/main/ComposeStarter and
 * https://github.com/android/wear-os-samples/tree/main/ComposeAdvanced to find the most up to date
 * changes to the libraries and their usages.
 */

package com.example.werewolf.presentation


import android.content.Context
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
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.health.services.client.HealthServices
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.wear.compose.material.MaterialTheme
import com.example.healthserviceslearn.presentation.HealthServicesManager
import com.example.werewolf.presentation.theme.WerewolfTheme
import kotlinx.coroutines.launch
import java.time.Instant

object ViewModelHolder {
    lateinit var healthViewModel: HealthViewModel
}

class MainActivity : ComponentActivity() {
    private lateinit var permissionLauncher: ActivityResultLauncher<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)

        ViewModelHolder.healthViewModel = ViewModelProvider(this)[HealthViewModel::class.java]

        val healthViewModel : HealthViewModel by lazy {
            ViewModelHolder.healthViewModel
        }


        val healthServicesManager = HealthServicesManager(HealthServices.getClient(this))

        val stepsData = getSharedPreferences("steps_data", Context.MODE_PRIVATE)
        val counterData = getSharedPreferences("counter_data", Context.MODE_PRIVATE)
        val counterEditor = counterData.edit()
        val sleepData = getSharedPreferences("sleep_data", Context.MODE_PRIVATE)

        healthViewModel.setDailySteps(processStepData(stepsData))
        healthViewModel.setSteps(stepsData.getInt("steps${formattedDate(Instant.now())}", 0))
        healthViewModel.setSleep(processSleepData(sleepData))

        if(sleepData.getString("sleep_start${formattedDate(Instant.now())}0", "") == ""
            && sleepData.getString("sleep_end${formattedDate(Instant.now())}0", "") == "") {
            counterEditor.putInt("start", 0).apply()
            counterEditor.putInt("end", 0).apply()
        }



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
















