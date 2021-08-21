package com.example.demo.view

import javafx.scene.layout.HBox
import tornadofx.*

class StaffPage: View("Staff") {

    override val root = borderpane {
        top = hbox {
            this += SearchView::class
            spacer()
            button("Go Back") {
                action {
                    find(StaffPage::class).replaceWith(MainView::class, sizeToScene = true, centerOnScreen = true)
                }
            }
        }

        center = hbox {
            StaffView()
        }
    }

    fun HBox.StaffView() = hbox {

        this += UserView::class
    }
}