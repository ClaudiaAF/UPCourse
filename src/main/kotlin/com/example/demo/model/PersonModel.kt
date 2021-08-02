package com.example.demo.model

import javafx.collections.FXCollections
import tornadofx.*

class PhoneNumber(countryCode: String, number: String) {
    var countryCode by property(countryCode)
    fun countryCodeProperty() = getProperty(PhoneNumber::countryCode)

    var number by property(number)
    fun numberProperty() = getProperty(PhoneNumber::number)
}

class Person(id: Int, name: String, phoneNumbers: List<PhoneNumber>) {
    var name by property(name)
    fun nameProperty() = getProperty(Person::name)

    var phoneNumbers by property(FXCollections.observableArrayList(phoneNumbers))
    fun phoneNumbersProperty() = getProperty(Person::phoneNumbers)
}

class PersonModel : ItemViewModel<Person>() {
    val name = bind { item?.nameProperty() }
    val phoneNumbers = bind { item?.phoneNumbersProperty() }
}