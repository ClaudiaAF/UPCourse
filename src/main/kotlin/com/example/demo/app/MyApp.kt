package com.example.demo.app

import Styles
import com.example.demo.view.LoginScreen
import com.example.demo.view.MainView
import javafx.stage.Stage
import tornadofx.App

class MyApp: App(LoginScreen::class, Styles::class){
    override fun start(stage: Stage) {
        super.start(stage)
        stage.width = 800.0
        stage.height = 600.0
    }
}