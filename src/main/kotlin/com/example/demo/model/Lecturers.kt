package com.example.demo.model

import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleStringProperty
import tornadofx.*

class Lecturers {
    val lecturerIdProperty = SimpleIntegerProperty()
    var lecturerId by lecturerIdProperty

    val lecturerNumberProperty = SimpleStringProperty()
    var lecturerNumber by lecturerNumberProperty

    val lecturerNameProperty = SimpleStringProperty()
    var lecturerName by lecturerNameProperty

    val lecturerSurnameProperty = SimpleStringProperty()
    var lecturerSurname by lecturerSurnameProperty

    val lecturerTeachProperty = SimpleStringProperty()
    var lecturerTeach by lecturerTeachProperty
}

class LecturersModel : ItemViewModel<Lecturers>(Lecturers()) {
    val lecturerId = bind(Lecturers::lecturerIdProperty)
    val lecturerNumber = bind(Lecturers::lecturerNumberProperty)
    val lecturerName = bind(Lecturers::lecturerNameProperty)
    val lecturerSurname = bind(Lecturers::lecturerSurnameProperty)
    val lecturerTeach = bind(Lecturers::lecturerTeachProperty)
}

class LecturersDetailModel : ItemViewModel<Lecturers>() {
    val lecturerId = bind { item?.lecturerIdProperty }
    val lecturerNumber = bind { item?.lecturerNumberProperty }
    val lecturerName = bind { item?.lecturerNameProperty }
    val lecturerSurname = bind { item?.lecturerSurnameProperty }
    val lecturerTeach = bind { item?.lecturerTeachProperty }
}