package com.example.werewolf.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.math.round

public class HealthViewModel : ViewModel() {
    private val _steps = MutableLiveData<Int>(0)
    private val _sleep = MutableLiveData<Float>(0.0f)
    private val _dailySteps = MutableLiveData<Int>(0)
    val steps: LiveData<Int> get() = _steps
    val sleep: LiveData<Float> get() = _sleep
    val dailySteps: LiveData<Int> get() = _dailySteps

    fun setSteps(steps: Int) {
        _steps.value = steps
    }

    fun setSleep(sleep: Float) {
        _sleep.value = round(sleep * 10) / 10
    }

    fun setDailySteps(dailySteps: Int) {
        _dailySteps.value = dailySteps
    }
}

