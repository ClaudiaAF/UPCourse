package com.example.demo.model

import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import tornadofx.*

class User (username: String, password: String){
    val usernameProperty = SimpleStringProperty(username)
    val passwordProperty = SimpleStringProperty(password)
}

class UserModel(): ItemViewModel<User>() {
    val username = bind{item?.usernameProperty}
    val password = bind{item?.passwordProperty}
}
