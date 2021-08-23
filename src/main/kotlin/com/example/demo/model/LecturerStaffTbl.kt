package com.example.demo.model

import javafx.beans.property.SimpleDoubleProperty
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleStringProperty
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import tornadofx.*
import java.math.BigDecimal

object LecturerStaffTbl : Table() {
    val lecturerId: Column<Int> = integer("lId").autoIncrement().primaryKey()
    val lecturerName: Column<String> = varchar("lName", length = 50)
    val lecturerSurname: Column<String> = varchar("lSurname", length = 50)
    val lecturerSubject: Column<String> = varchar("lSubject", length = 50)
    val lecturerSalary: Column<BigDecimal> = decimal("lSalary", scale = 2, precision = 9)
}

fun ResultRow.toLecturerEntry() = LecturerEntry (
    this[LecturerStaffTbl.lecturerId],
    this[LecturerStaffTbl.lecturerName],
    this[LecturerStaffTbl.lecturerSurname],
    this[LecturerStaffTbl.lecturerSubject],
    this[LecturerStaffTbl.lecturerSalary].toDouble()
)

class LecturerEntry(lecturerId: Int, lecturerName: String, lecturerSurname: String, lecturerSubject: String, lecturerSalary: Double) {
    val lecturerIdProperty = SimpleIntegerProperty(lecturerId)
    var lecturerId by lecturerIdProperty

    val lecturerNameProperty = SimpleStringProperty(lecturerName)
    var lecturerName by lecturerNameProperty

    val lecturerSurnameProperty = SimpleStringProperty(lecturerSurname)
    var lecturerSurname by lecturerSurnameProperty

    val lecturerSubjectProperty = SimpleStringProperty(lecturerSubject)
    var lecturerSubject by lecturerSubjectProperty

    val lecturerSalaryProperty = SimpleDoubleProperty(lecturerSalary)
    var lecturerSalary by lecturerSalaryProperty

    var totalLecturerSalaryExpenses = javafx.beans.binding.Bindings.add(lecturerSalaryProperty, 0)

    override fun toString(): String {
        return "LecturerEntry(id=$lecturerId, name=$lecturerName, surname=$lecturerSurname, lecturerNumber=$lecturerSubject, lecturerFees=$lecturerSalary, totalLecturerSalary=$totalLecturerSalaryExpenses)"
    }
}

class LecturerEntryModel : ItemViewModel<LecturerEntry>() {
    val lecturerId = bind{ item?.lecturerIdProperty }
    val lecturerName = bind { item?.lecturerNameProperty }
    val lecturerSurname = bind { item?.lecturerSurnameProperty }
    val lecturerSubject = bind { item?.lecturerSubjectProperty }
    val lecturerSalary = bind { item?.lecturerSalaryProperty }
    var totalLecturerSalaryExpenses = itemProperty.select (LecturerEntry::totalLecturerSalaryExpenses)

}