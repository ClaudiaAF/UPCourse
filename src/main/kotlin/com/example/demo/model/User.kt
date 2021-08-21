package com.example.demo.model

import com.sun.javafx.binding.ContentBinding.bind
import javafx.beans.property.SimpleStringProperty
import org.jetbrains.exposed.dao.*
import tornadofx.*

object Users : IntIdTable(){
    val username = varchar("username", 255).uniqueIndex()
    val password = varchar("password", 255)
}

class User(id : EntityID<Int>): IntEntity(id){
    companion object : IntEntityClass<User>(Users)

    var username by Users.username
    var password by Users.password

    override fun toString(): String {
        return "User(username=\"$username\", password=\"$password\")"
    }
}

class UserModel(): ItemViewModel<User>() {
    val username = bind(User::username)
    val password = bind(User::password)
}
