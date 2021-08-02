package com.example.demo.view

import javafx.beans.property.SimpleStringProperty
import javafx.scene.layout.BorderPane
import tornadofx.*
import java.awt.TextField
import java.time.LocalDate
import javax.swing.text.TableView

class Person(name: String? = null, title: String? = null) {
    val nameProperty = SimpleStringProperty(this, "name", name)
    var name by nameProperty

    val titleProperty = SimpleStringProperty(this, "title", title)
    var title by titleProperty
}

class EditView : View("Person Editor") {
    override val root = tabpane {

        tab("Students List") {
            tableview<Person> {
                items = listOf(
                    Person("James Blake","Degree"),
                    Person("Amanda Johnson","Degree"),
                    Person("William Knight","Diploma"),
                    Person("Sam Turner","Diploma")
                ).observable()


                column("Name", Person::name)
                column("Qualification", Person::title)

            }
        }
    }
}
