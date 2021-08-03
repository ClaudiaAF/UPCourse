package com.example.demo.model

import javafx.beans.property.SimpleStringProperty
import tornadofx.*

class Salaries (name: String, designation: String, nextPayment: String, status: String, amount: String){
    val nameSalary = SimpleStringProperty(name)
    val designationSalary = SimpleStringProperty(designation)
    val nextPaymentSalary = SimpleStringProperty(nextPayment)
    val statusSalary = SimpleStringProperty(status)
    val amountSalary = SimpleStringProperty(amount)
}

class SalariesModel(): ItemViewModel<Salaries>() {
    val name = bind{item?.nameSalary}
    val designation = bind{item?.designationSalary}
    val nextPayment = bind{item?.nextPaymentSalary}
    val status = bind{item?.statusSalary}
    val amount = bind{item?.amountSalary}
}