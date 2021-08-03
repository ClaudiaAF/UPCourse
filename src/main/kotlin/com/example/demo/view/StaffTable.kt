package com.example.demo.view

import com.example.demo.app.Styles
import com.example.demo.controller.PersonController
import com.example.demo.model.User
import com.example.demo.model.UserModel
import javafx.scene.layout.HBox
import tornadofx.*

class StaffTable: View ("Staff") {
    val personController: PersonController by inject()
    val model: UserModel by inject()


    override val root = tableview(personController.users) {

        column("Username", User::usernameProperty)
        column("Password", User::passwordProperty)

        columnResizePolicy = SmartResize.POLICY

        bindSelected(model)


    }
}