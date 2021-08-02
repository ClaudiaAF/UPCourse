package com.example.demo.model

import javafx.beans.property.Property
import javafx.beans.property.StringProperty
import tornadofx.*
import java.time.LocalDate

class UserModel {
    var name by property<String>()
    fun nameProperty() = getProperty(UserModel::name)

    var birthday by property<LocalDate>()
    fun birthdayProperty() = getProperty(UserModel::birthday)

    var street by property<String>()
    fun streetProperty() = getProperty(UserModel::street)

    var zip by property<String>()
    fun zipProperty() = getProperty(UserModel::zip)

    var city by property<String>()
    fun cityProperty() = getProperty(UserModel::city)

    override fun toString() = name
}

class User : ItemViewModel<UserModel>(UserModel()) {
    val name: StringProperty = bind { item?.nameProperty() }
    val birthday: Property<LocalDate> = bind { item?.birthdayProperty() }
    val street: StringProperty = bind { item?.streetProperty() }
    val zip:StringProperty = bind { item?.zipProperty() }
    val city:StringProperty = bind { item?.cityProperty() }
}