package com.example.demo.view

import javafx.scene.layout.HBox
import javafx.scene.layout.Pane
import javafx.scene.layout.Priority
import tornadofx.*
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit

fun Pane.contentBox(op: HBox.() -> Unit) {
    hbox {
        hbox {
            hboxConstraints { hGrow = Priority.ALWAYS }
            op()
        }
    }
}