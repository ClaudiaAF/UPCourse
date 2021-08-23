package com.example.demo.model

import com.example.demo.model.DegreeStudentsTbl.degreeStudentFees
import com.example.demo.model.DegreeStudentsTbl.degreeStudentId
import com.example.demo.model.DegreeStudentsTbl.degreeStudentName
import com.example.demo.model.DegreeStudentsTbl.degreeStudentNumber
import com.example.demo.model.DegreeStudentsTbl.degreeStudentSurname
import javafx.beans.property.SimpleDoubleProperty
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleStringProperty
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import tornadofx.*
import java.math.BigDecimal

object DegreeStudentsTbl : Table() {
    val degreeStudentId: Column<Int> = integer("dStudentId").autoIncrement().primaryKey()
    val degreeStudentName: Column<String> = varchar("dStudentName", length = 50)
    val degreeStudentSurname: Column<String> = varchar("dStudentSurname", length = 50)
    val degreeStudentSubject: Column<String> = varchar("dStudentSubject", length = 50)
    val degreeStudentNumber: Column<Int> = integer("dStudentNumber")
    val degreeStudentFees: Column<BigDecimal> = decimal("dFees", scale = 2, precision = 9)
}

fun ResultRow.toDegreeStudentsEntry() = DegreeStudentsEntry (
    this[DegreeStudentsTbl.degreeStudentId],
    this[DegreeStudentsTbl.degreeStudentName],
    this[DegreeStudentsTbl.degreeStudentSurname],
    this[DegreeStudentsTbl.degreeStudentSubject],
    this[DegreeStudentsTbl.degreeStudentNumber],
    this[DegreeStudentsTbl.degreeStudentFees].toDouble()
        )

class DegreeStudentsEntry(degreeStudentId: Int, degreeStudentName: String, degreeStudentSurname: String, degreeStudentSubject: String, degreeStudentNumber: Int, degreeStudentFees: Double) {
    val dStudentIdProperty = SimpleIntegerProperty(degreeStudentId)
    var degreeStudentId by dStudentIdProperty

    val dStudentNameProperty = SimpleStringProperty(degreeStudentName)
    var degreeStudentName by dStudentNameProperty

    val dStudentSurnameProperty = SimpleStringProperty(degreeStudentSurname)
    var degreeStudentSurname by dStudentSurnameProperty

    val dStudentSubjectProperty = SimpleStringProperty(degreeStudentSubject)
    var degreeStudentSubject by dStudentSubjectProperty

    val dStudentNumberProperty = SimpleIntegerProperty(degreeStudentNumber)
    var degreeStudentNumber by dStudentNumberProperty

    val dStudentFeesProperty = SimpleDoubleProperty(degreeStudentFees)
    var degreeStudentFees by dStudentFeesProperty

    var totalFeesExpenses = javafx.beans.binding.Bindings.add(dStudentFeesProperty, 0)

    override fun toString(): String {
        return "DegreeStudentsEntry(id=$degreeStudentId, name=$degreeStudentName, surname=$degreeStudentSurname, subject=$degreeStudentSubject studentNumber=$degreeStudentNumber, studentFees=$degreeStudentFees)"
    }
}

class DegreeStudentsEntryModel : ItemViewModel<DegreeStudentsEntry>() {
    val degreeStudentId = bind{ item?.dStudentIdProperty }
    val degreeStudentName = bind { item?.dStudentNameProperty }
    val degreeStudentSurname = bind { item?.dStudentSurnameProperty }
    val degreeStudentSubject = bind { item?.dStudentSubjectProperty }
    val degreeStudentNumber = bind { item?.dStudentNumberProperty }
    val degreeStudentFees = bind { item?.dStudentFeesProperty }
    var totalFeesExpenses = itemProperty.select (DegreeStudentsEntry::totalFeesExpenses)

}

