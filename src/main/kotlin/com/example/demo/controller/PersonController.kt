package com.example.demo.controller

import com.example.demo.model.PersonModel
import com.example.demo.model.PhoneNumber
import com.example.demo.view.Person
import javafx.collections.FXCollections
import tornadofx.*

class PersonController : Controller() {
    val persons = FXCollections.observableArrayList<Person>()

    init {
        // Add some test persons for the demo
        persons.add(Person("", ""))
    }
}