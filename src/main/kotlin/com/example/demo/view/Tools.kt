package com.example.demo.view

import com.example.demo.app.Styles.Companion.content
import com.example.demo.app.Styles.Companion.contentWrapper
import javafx.scene.layout.HBox
import javafx.scene.layout.Pane
import javafx.scene.layout.Priority
import tornadofx.*
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit

fun Pane.contentBox(op: HBox.() -> Unit) {
    hbox {
        addClass(contentWrapper)
        hbox {
            addClass(content)
            hboxConstraints { hGrow = Priority.ALWAYS }
            op()
        }
    }
}