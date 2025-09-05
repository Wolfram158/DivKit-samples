package ru.wolfram.divkitsamples.presentation

import android.app.Application
import ru.wolfram.divkitsamples.di.AppComponent

class App: Application() {
    val component = AppComponent()
}