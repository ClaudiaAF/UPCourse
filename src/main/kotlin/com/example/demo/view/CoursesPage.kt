package com.example.demo.view

import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.chart.CategoryAxis
import javafx.scene.chart.NumberAxis
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.scene.paint.Color
import tornadofx.*

class CoursesPage : View("Courses") {
    val subjects = listOf("Information Technology", "Social Studies", "Psychological Studies", "Historic Studies", "Accounting", "Business Studies", "Graphic Design")
    val description = listOf("the best way to become a techie", "learn about the ways in which we understand society", "understand the workings of the human brain")

    override val root = borderpane {
        prefWidth = 1000.0
        prefHeight = 600.0
        top = hbox {
            label("Subjects")
            spacer()
            button("Go Back") {
                action {
                    find(CoursesPage::class).replaceWith(MainView::class, sizeToScene = true, centerOnScreen = true)
                }
            }

        }

        center = vbox {
            padding = Insets(10.0, 0.0, 10.0, 0.0)
            spacing = 6.0
            chartView()
        }
    }


    fun VBox.chartView() = datagrid(subjects) {
        cellHeight = 375.0
        cellWidth = 275.0

        cellCache {
            vbox {
                imageview("https://ps.w.org/announcer/assets/icon-256x256.png?rev=2325341") {
                    fitHeight = 150.0
                    fitWidth = 150.0
                    alignment = Pos.CENTER
                }
                label(it) {
                    alignment = Pos.CENTER
                }
                label()
            }
        }
    }

}