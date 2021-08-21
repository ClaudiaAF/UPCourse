package com.example.demo.view

import Styles.Companion.h1
import com.example.demo.controller.PersonController
import com.example.demo.model.User
import com.example.demo.view.TopBar
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.chart.CategoryAxis
import javafx.scene.chart.NumberAxis
import javafx.scene.control.Alert
import javafx.scene.image.Image
import javafx.scene.layout.GridPane
import javafx.scene.layout.HBox
import javafx.scene.layout.Priority
import javafx.scene.paint.Color
import javafx.scene.text.FontWeight
import tornadofx.*
import tornadofx.WizardStyles.Companion.bold
import java.awt.Button
import java.awt.Event.HOME
import java.awt.Label
import java.awt.TextArea
import javax.swing.GroupLayout

class MainView : View("User Screen") {
    val controller: PersonController by inject()

    override val root = borderpane {
        top = vbox {
            addClass(Styles.rowWrapper)
            add(TopBar::class)
            padding = Insets(10.0, 0.0, 10.0, 0.0)
            spacing = 6.0
            label("Dashboard").addClass(h1)
        }

        center = hbox {
            addClass(Styles.rowWrapper)
            chartView()
        }
    }


    fun HBox.chartView() = gridpane(){
        row {

        }
        row {
            linechart("linechart", CategoryAxis(), NumberAxis()) {
                series("month") {
                    data("jan", 10)
                    data("feb", 20)
                    data("mar", 5)
                }
                series("week") {
                    data("jan", 1)
                    data("feb", 2)
                }
            }
        }
    }
}