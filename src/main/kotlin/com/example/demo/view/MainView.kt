package com.example.demo.view

import Styles.Companion.h1
import javafx.geometry.Insets
import javafx.scene.chart.CategoryAxis
import javafx.scene.chart.NumberAxis
import javafx.scene.layout.HBox
import tornadofx.*

class MainView : View("User Screen") {

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