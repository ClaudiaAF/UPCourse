package com.example.demo.view

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
                action {
                }
            }
        }
    }
}