package com.example.demo.view

import com.example.demo.controller.SalariesAdminController
import com.example.demo.controller.SalariesLecturerController
import com.example.demo.model.Salaries
import tornadofx.*
import tornadofx.Stylesheet.Companion.title

class SalariesLecturers : View("Lecturer Salaries") {
    val salariesLecturerController: SalariesLecturerController by inject()
    val model: Salaries by inject()

    override val root = tableview(salariesLecturerController.salaries) {


        column("Name", Salaries::nameSalary)
        column("Designation", Salaries::designationSalary)
        column("Next Payment", Salaries::nextPaymentSalary)
        column("Status", Salaries::statusSalary)
        column("Amount", Salaries::amountSalary)

        columnResizePolicy = SmartResize.POLICY


    }
}