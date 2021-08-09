package com.example.demo.model

import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleStringProperty
import tornadofx.*

class Admin {
    val adminIdProperty = SimpleIntegerProperty()
    var adminId by adminIdProperty

    val adminNameProperty = SimpleStringProperty()
    var adminName by adminNameProperty

    val adminSurnameProperty = SimpleStringProperty()
    var adminSurname by adminSurnameProperty

}

class AdminModel : ItemViewModel<Admin>(Admin()) {
    val adminId = bind(Admin::adminIdProperty)
    val adminName = bind(Admin::adminNameProperty)
    val adminSurname = bind(Admin::adminSurnameProperty)
}

class AdminDetailModel : ItemViewModel<Admin>() {
    val adminId = bind { item?.adminIdProperty }
    val adminName = bind { item?.adminNameProperty }
    val adminSurname = bind { item?.adminSurnameProperty }
}