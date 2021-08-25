package com.example.demo.view

import Styles.Companion.darkTextColor
import Styles.Companion.lightBackgroundColor
import com.example.demo.controller.DiplomaStudentController
import com.example.demo.controller.ItemController
import com.example.demo.controller.TotalAmountsController
import com.example.demo.model.LecturerEntryModel
import com.sun.prism.paint.Color
import javafx.animation.FadeTransition
import javafx.animation.Interpolator
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.chart.CategoryAxis
import javafx.scene.chart.NumberAxis
import javafx.scene.control.Label
import javafx.scene.image.ImageView
import javafx.scene.layout.HBox
import javafx.scene.text.FontWeight
import javafx.util.Duration
import tornadofx.*

class MainView : View("User Screen") {
    val controller: TotalAmountsController by inject()
    var logoIcon: Label by singleAssign()

    override val root = hbox {
        hboxConstraints {
            alignment = Pos.CENTER_LEFT

        }
        val myBox = vbox {

            vboxConstraints {
                alignment = Pos.CENTER_LEFT
            }

            imageview("https://drive.google.com/uc?export=view&id=1C6Dp5N2TOUNv1YbTbvYKNhkbkQV5cCUi"){
                style {
                    fitHeight = 120.0
                    fitWidth = 120.0
                }
            }

           label("Welcome to UPCourse") {
              style {
                  fontSize = 40.pt
                  fontFamily = "Open Sans"
                  fontWeight = FontWeight.BOLD
              }
           }

            text("Manage your Institution the right way, the UP way.") {

                style {
                    fontSize = 12.pt
                    fontFamily = "Open Sans"
                    fontWeight = FontWeight.LIGHT
                    opacity = 0.7
                    padding = box(17.px, 21.px)
                }
                Pos.BOTTOM_CENTER
            }

            text("Version 1.0.0"){
                style {
                    paddingTop = 30.0
                    fontSize = 10.pt
                    fontFamily = "Open Sans"
                    fontWeight = FontWeight.LIGHT
                    opacity = 0.5
                    alignment = Pos.BOTTOM_LEFT
                }
            }

            text("Developed by Claudia Ferreira") {
                style {
                    fontSize = 10.pt
                    fontFamily = "Open Sans"
                    fontWeight = FontWeight.LIGHT
                    opacity = 0.5
                    alignment = Pos.BOTTOM_LEFT
                }
            }

            padding = Insets(10.0, 0.0, 10.0, 50.0)
            spacing = 15.0
        }

    } //main hbox
}