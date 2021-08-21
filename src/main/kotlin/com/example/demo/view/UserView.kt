package com.example.demo.view

import com.example.demo.controller.PersonController
import com.example.demo.model.User
import com.example.demo.model.UserModel
import javafx.geometry.Insets
import javafx.scene.control.Alert
import javafx.scene.control.TextField
import javafx.scene.layout.HBox
import javafx.scene.paint.Color
import tornadofx.*

class UserView : View("User Screen") {
    val personController : PersonController by inject()
    var usernameField : TextField by singleAssign()
    var passwordField : TextField by singleAssign()

    override val root = vbox {
        hbox {
            label("Info") {
                graphic = label()
            }


        }
        form {
            fieldset("Personal Information") {
                field("Name") {
                    textfield {
                        usernameField = this
                    }
                }

                field("Surname") {
                    textfield{
                        passwordField = this
                    }
                }
            }


            button("Add User") {
                action {
                    personController.addUser(usernameField.text, passwordField.text)
                    usernameField.text = ""
                    passwordField.text = ""
                }
            }
        }

    }

}