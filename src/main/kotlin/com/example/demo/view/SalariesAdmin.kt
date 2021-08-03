package com.example.demo.view

import com.example.demo.controller.PersonController
import com.example.demo.controller.SalariesAdminController
import com.example.demo.model.Salaries
import com.example.demo.model.User
import com.example.demo.model.UserModel
import tornadofx.*

class SalariesAdmin : View ("Administrative Salaries") {
    val salariesAdminController: SalariesAdminController by inject()
    val model: Salaries by inject()

    override val root = tableview(salariesAdminController.salaries) {


        column("Name", Salaries::nameSalary)
        column("Designation", Salaries::designationSalary)
        column("Next Payment", Salaries::nextPaymentSalary)
        column("Status", Salaries::statusSalary)
        column("Amount", Salaries::amountSalary)

        columnResizePolicy = SmartResize.POLICY


    }
}