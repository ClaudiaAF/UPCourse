package com.example.demo.app

import Styles
import javafx.stage.Stage
import tornadofx.App

class MyApp: App(UPCourseWorkspace::class, Styles::class){
    override fun start(stage: Stage) {
        super.start(stage)
        stage.width = 1200.0
        stage.height = 600.0
    }
}