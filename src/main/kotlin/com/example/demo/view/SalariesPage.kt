package com.example.demo.view

import com.example.demo.app.Styles
import com.example.demo.app.Styles.Companion.h1
import javafx.scene.layout.HBox
import tornadofx.*

class SalariesPage: View("Salaries") {

    override val root = borderpane {
        prefWidth = 1000.0
        prefHeight = 600.0
        addClass(Styles.userscreen, Styles.defaultSpacing, Styles.defaultContentPadding)
        top = hbox {
            label ("Salaries"){
                addClass(Styles.defaultContentPadding, h1)
            }
            spacer()
            addClass(Styles.rowWrapper)
            button("Go Back") {
                addClass(Styles.successButton)
                action {
                    find(SalariesPage::class).replaceWith(MainView::class, sizeToScene = true, centerOnScreen = true)
                }
            }
        }

        center = hbox {
            addClass(Styles.rowWrapper)
            SalariesView()
        }
    }

    fun HBox.SalariesView() = hbox {
        this += SalariesLecturers::class
        this += SalariesAdmin::class
        addClass(Styles.defaultSpacing)
    }
}