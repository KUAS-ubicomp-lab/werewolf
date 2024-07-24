package com.example.werewolf.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

public class HealthViewModel : ViewModel() {
    private val _steps = MutableLiveData<Int>(0)
    private val _sleep = MutableLiveData<Int>(0)
    private val _dailySteps = MutableLiveData<Int>(0)
    val steps: LiveData<Int> get() = _steps
    val sleep: LiveData<Int> get() = _sleep
    val dailySteps: LiveData<Int> get() = _dailySteps

    fun setSteps(steps: Int) {
        _steps.value = steps
    }

    fun setSleep(sleep: Int) {
        _sleep.value = sleep
    }

    fun setDailySteps(dailySteps: Int) {
        _dailySteps.value = dailySteps
    }
}

