package com.example.demo.model

import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleStringProperty
import tornadofx.*

class Student {
    val studentIdProperty = SimpleIntegerProperty()
    var studentId by studentIdProperty

    val studentNameProperty = SimpleStringProperty()
    var studentName by studentNameProperty

    val studentSurnameProperty = SimpleStringProperty()
    var studentSurname by studentSurnameProperty

    val studentSubjectProperty = SimpleStringProperty()
    var studentSubject by studentSubjectProperty
}

class StudentModel : ItemViewModel<Student>(Student()) {
    val studentId = bind(Student::studentIdProperty)
    val studentName = bind(Student::studentNameProperty)
    val studentSurname = bind(Student::studentSurnameProperty)
    val studentSubject = bind(Student::studentSubjectProperty)
}

class StudentDetailModel : ItemViewModel<Student>() {
    val studentId = bind { item?.studentIdProperty }
    val studentName = bind { item?.studentNameProperty }
    val studentSurname = bind { item?.studentSurnameProperty }
    val studentSubject = bind { item?.studentSubjectProperty }
}