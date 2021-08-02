package com.example.demo.controller

import com.example.demo.model.User
import com.example.demo.view.Person
import javafx.collections.FXCollections
import tornadofx.*

class PersonController : Controller() {
    var users = FXCollections.observableArrayList<User>()

    init {
        users.plusAssign(User("bob", "1234"))
        users.plusAssign(User("jack", "1234"))
    }
}