package com.example.demo.view

import com.example.demo.app.Styles
import tornadofx.*

class SearchView : View("search") {
    override val root = hbox {
        form {
            fieldset("Staff Search") {
                field("ID") {
                    textfield() {

                    }
                }

                field("Name") {
                    textfield() {

                    }
                }
            }

            button("Search") {
                addClass(Styles.successButton)
                action {
                }
            }
        }
    }
}