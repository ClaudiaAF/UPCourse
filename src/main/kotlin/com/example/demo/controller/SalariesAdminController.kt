package com.example.demo.controller

import com.example.demo.model.Salaries
import com.example.demo.model.User
import javafx.collections.FXCollections
import tornadofx.*

class SalariesAdminController : Controller() {
    var salaries = FXCollections.observableArrayList<Salaries>()

    init {
        salaries.plusAssign(Salaries("bob smith", "Librarian", "25/03/2022", "Pending", "R5 300"))
        salaries.plusAssign(Salaries("jack nichols", "Receptionist", "25/03/2022", "Pending", "R6 000"))
    }
}