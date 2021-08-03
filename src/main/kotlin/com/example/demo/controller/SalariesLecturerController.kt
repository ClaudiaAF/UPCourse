package com.example.demo.controller

import com.example.demo.model.Salaries
import javafx.collections.FXCollections
import tornadofx.*

class SalariesLecturerController : Controller() {
    var salaries = FXCollections.observableArrayList<Salaries>()

    init {
        salaries.plusAssign(Salaries("john smith", "Information Technology", "25/03/2022", "Pending", "R5 300"))
        salaries.plusAssign(Salaries("sarah pandit", "History", "25/03/2022", "Pending", "R6 000"))
    }
}