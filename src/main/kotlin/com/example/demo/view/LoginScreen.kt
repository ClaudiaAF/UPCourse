package com.example.demo.view

import com.example.demo.app.Styles
import com.example.demo.app.Styles.Companion.errorMessage
import com.example.demo.app.Styles.Companion.footer
import com.example.demo.app.Styles.Companion.h1
import com.example.demo.app.Styles.Companion.loginScreen
import com.example.demo.app.Styles.Companion.newToGitHub
import com.example.demo.app.Styles.Companion.successButton
import com.example.demo.controller.LoginController
import javafx.animation.KeyFrame
import javafx.animation.Timeline
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleStringProperty
import javafx.event.EventHandler
import javafx.geometry.Orientation
import javafx.scene.control.Button
import javafx.scene.control.PasswordField
import javafx.scene.layout.StackPane
import javafx.util.Duration
import sun.applet.Main
import tornadofx.*

class LoginScreen : View("Sign in to GitHub") {

    val messageWrapper by cssid()
    val passwordField by cssid()

    override val root = vbox {
        addClass(loginScreen)

//        label().addClass(Styles.logoIcon, Styles.icon, Styles.large)
//        label(title).addClass(h1)
        stackpane().setId(messageWrapper)
        form {
            fieldset(labelPosition = Orientation.VERTICAL) {
                field("Username") {
                    textfield(){

                    }
                }
                field("Password") {
                    passwordfield (){

                    }
                }.forgotPasswordLink()
            }

            button("Sign in") {
                isDefaultButton = true
                addClass(successButton)
                action {
                    replaceWith<MainView>()
                }
            }
        }

        hbox {
            addClass(footer)
            label("UPcourse Application")
        }
    }

    private fun Button.login() {
        fun signalSigningIn() {
            properties["originalText"] = text
            text = "Signing in..."
            opacity = 0.5
        }

        fun signalSigningComplete() {
            text = properties["originalText"] as String
            opacity = 1.0
        }


    }

    private fun loginFailed() {
        root.select<StackPane>(messageWrapper).replaceChildren {
            hbox {
                addClass(errorMessage)
                label("Incorrect username or password.")
                spacer()
                button {
                    addClass(Styles.crossIcon, Styles.icon, Styles.small)
                    action {
                        this@hbox.removeFromParent()
                    }
                }
            }
        }

        root.select<PasswordField>(passwordField).requestFocus()
    }

    fun Field.forgotPasswordLink() {
        label.style { minWidth = 170.px }
        labelContainer.hyperlink("Forgot password?") {
            isFocusTraversable = false
            style { fontSize = 12.px }
        }
    }
}
