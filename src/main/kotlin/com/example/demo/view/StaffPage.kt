package com.example.demo.view

import com.example.demo.app.Styles
import javafx.scene.layout.HBox
import tornadofx.*

class StaffPage: View("Staff") {

    override val root = borderpane {
        addClass(Styles.userscreen, Styles.defaultSpacing, Styles.defaultContentPadding)
        top = hbox {
            this += SearchView::class
            spacer()
            addClass(Styles.rowWrapper)
            button("Go Back") {
                addClass(Styles.successButton)
                action {
                    find(StaffPage::class).replaceWith(MainView::class, sizeToScene = true, centerOnScreen = true)
                }
            }
        }

        center = hbox {
            addClass(Styles.rowWrapper)
            StaffView()
        }
    }

    fun HBox.StaffView() = hbox {
        this += StaffTable::class
        this += UserView::class
    }
}