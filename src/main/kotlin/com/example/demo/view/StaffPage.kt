package com.example.demo.view

import com.example.demo.app.Styles
import javafx.scene.layout.HBox
import tornadofx.*

class StaffPage: View("Staff") {

    override val root = borderpane {
        addClass(Styles.userscreen, Styles.defaultSpacing, Styles.defaultContentPadding)
        top = vbox {
            addClass(Styles.rowWrapper)
            button("Go Back", javafx.scene.control.Label().addClass(Styles.commentIcon, Styles.icon)) {
                addClass(Styles.successButton)
                action {
                    find(StaffPage::class).replaceWith(MainView::class, sizeToScene = true, centerOnScreen = true)
                }
            }
            spacer()
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