package com.example.demo.view

import com.example.demo.app.Styles
import com.example.demo.model.User
import com.example.demo.model.UserModel
import javafx.geometry.Insets
import javafx.scene.control.Alert
import javafx.scene.layout.HBox
import javafx.scene.paint.Color
import tornadofx.*

class UserView : View("User Screen") {
    val model : UserModel by inject()

    override val root = vbox {
        addClass(Styles.detail, Styles.defaultSpacing, Styles.defaultContentPadding)
        hbox {
            label("Info") {
                addClass(Styles.h2)
                graphic = label().addClass(Styles.repoIcon, Styles.icon)
            }


        }
        form {
            fieldset("Personal Information") {
                field("Name") {
                    textfield(model.username)
                }

                field("Surname") {
                    textfield(model.password)
                }
            }


            button("Save") {
                action {
                    model.commit()
                }

                enableWhen(model.valid)
            }
        }

    }

}