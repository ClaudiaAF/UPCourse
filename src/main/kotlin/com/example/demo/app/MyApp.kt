package com.example.demo.app


import Styles
import javafx.stage.Stage
import tornadofx.*

class MyApp: App(UPCourseWorkspace::class, Styles::class){
    override fun start(stage: Stage) {
        super.start(stage)
        stage.width = 1900.0
        stage.height = 1000.0
    }
}