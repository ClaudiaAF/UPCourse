package com.example.demo.model

import com.example.demo.model.DiplomaStudentsTbl.diplomaStudentFees
import com.example.demo.model.DiplomaStudentsTbl.diplomaStudentId
import com.example.demo.model.DiplomaStudentsTbl.diplomaStudentNumber
import com.example.demo.model.DiplomaStudentsTbl.diplomaStudentSurname
import javafx.beans.property.SimpleDoubleProperty
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleStringProperty
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import tornadofx.*
import java.math.BigDecimal

object DiplomaStudentsTbl : Table() {
    val diplomaStudentId: Column<Int> = integer("dpStudentId").autoIncrement().primaryKey()
    val diplomaStudentName: Column<String> = varchar("dpStudentName", length = 50)
    val diplomaStudentSurname: Column<String> = varchar("dpStudentSurname", length = 50)
    val diplomaStudentNumber: Column<Int> = integer("dpStudentNumber")
    val diplomaStudentFees: Column<BigDecimal> = decimal("dpFees", scale = 2, precision = 9)
}

fun ResultRow.toDiplomaStudentsEntry() = DiplomaStudentsEntry (
    this[DiplomaStudentsTbl.diplomaStudentId],
    this[DiplomaStudentsTbl.diplomaStudentName],
    this[DiplomaStudentsTbl.diplomaStudentSurname],
    this[DiplomaStudentsTbl.diplomaStudentNumber],
    this[DiplomaStudentsTbl.diplomaStudentFees].toDouble()
)

class DiplomaStudentsEntry(diplomaStudentId: Int, diplomaStudentName: String, diplomaStudentSurname: String, diplomaStudentNumber: Int, diplomaStudentFees: Double) {
    val dpStudentIdProperty = SimpleIntegerProperty(diplomaStudentId)
    var diplomaStudentId by dpStudentIdProperty

    val dpStudentNameProperty = SimpleStringProperty(diplomaStudentName)
    var diplomaStudentName by dpStudentNameProperty

    val dpStudentSurnameProperty = SimpleStringProperty(diplomaStudentSurname)
    var diplomaStudentSurname by dpStudentSurnameProperty

    val dpStudentNumberProperty = SimpleIntegerProperty(diplomaStudentNumber)
    var diplomaStudentNumber by dpStudentNumberProperty

    val dpStudentFeesProperty = SimpleDoubleProperty(diplomaStudentFees)
    var diplomaStudentFees by dpStudentFeesProperty

    var totalFeesDiplomaExpenses = javafx.beans.binding.Bindings.add(dpStudentFeesProperty, 0)

    override fun toString(): String {
        return "DiplomaStudentsEntry(id=$diplomaStudentId, name=$diplomaStudentName, surname=$diplomaStudentSurname, studentNumber=$diplomaStudentNumber, studentFees=$diplomaStudentFees)"
    }
}

class DiplomaStudentsEntryModel : ItemViewModel<DiplomaStudentsEntry>() {
    val diplomaStudentId = bind{ item?.dpStudentIdProperty }
    val diplomaStudentName = bind { item?.dpStudentNameProperty }
    val diplomaStudentSurname = bind { item?.dpStudentSurnameProperty }
    val diplomaStudentNumber = bind { item?.dpStudentNumberProperty }
    val diplomaStudentFees = bind { item?.dpStudentFeesProperty }
    var totalFeesDiplomaExpenses = itemProperty.select (DiplomaStudentsEntry::totalFeesDiplomaExpenses)

}

