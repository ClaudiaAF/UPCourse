package com.example.demo.app

import com.example.demo.controller.LoginController
import com.example.demo.view.LoginScreen
import javafx.stage.Stage
import tornadofx.*

class LoginApp : App(LoginScreen::class, Styles::class) {
    val loginController: LoginController by inject()

    override fun start(stage: Stage) {
        super.start(stage)
        loginController
    }
}

fun main(args: Array<String>) {
    launch<LoginApp>(args)
}