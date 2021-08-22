package com.example.demo.model

import javafx.beans.property.SimpleDoubleProperty
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleStringProperty
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import tornadofx.*
import java.math.BigDecimal

object SubjectsTbl : Table() {
    val subjectId: Column<Int> = integer("subId").autoIncrement().primaryKey()
    val subjectName: Column<String> = varchar("subName", length = 50)
    val subjectCode: Column<String> = varchar("subCode", length = 50)
    val subjectCredits: Column<Int> = integer("subCredits")
    val hoursPerWeek: Column<Int> = integer("hours")
    val pricePerMonth: Column<BigDecimal> = decimal("priceMonthly", scale = 2, precision = 9)
}

fun ResultRow.toSubjectEntry() = SubjectEntry (
    this[SubjectsTbl.subjectId],
    this[SubjectsTbl.subjectName],
    this[SubjectsTbl.subjectCode],
    this[SubjectsTbl.subjectCredits],
    this[SubjectsTbl.hoursPerWeek],
    this[SubjectsTbl.pricePerMonth].toDouble()
)

class SubjectEntry(subjectId: Int, subjectName: String, subjectCode: String, subjectCredits: Int, hoursPerWeek: Int, pricePerMonth: Double) {
    val subjectIdProperty = SimpleIntegerProperty(subjectId)
    var subjectId by subjectIdProperty

    val subjectNameProperty = SimpleStringProperty(subjectName)
    var subjectName by subjectNameProperty

    val subjectCodeProperty = SimpleStringProperty(subjectCode)
    var subjectCode by subjectCodeProperty

    val subjectCreditsProperty = SimpleIntegerProperty(subjectCredits)
    var subjectCredits by subjectCreditsProperty

    val hoursPerWeekProperty = SimpleIntegerProperty(hoursPerWeek)
    var hoursPerWeek by hoursPerWeekProperty

    val pricePerMonthProperty = SimpleDoubleProperty(pricePerMonth)
    var pricePerMonth by pricePerMonthProperty

    var totalPricePerMonthExpenses = javafx.beans.binding.Bindings.add(pricePerMonthProperty, 0)

    override fun toString(): String {
        return "SubjectEntry(id=$subjectId, name=$subjectName, code=$subjectCode, subjectCredits=$subjectCredits, hours=$hoursPerWeek, pricePerMonth=$pricePerMonth )"
    }
}

class SubjectsEntryModel : ItemViewModel<SubjectEntry>() {
    val subjectId = bind{ item?.subjectIdProperty }
    val subjectName = bind { item?.subjectNameProperty }
    val subjectCode = bind { item?.subjectCodeProperty }
    val subjectCredits = bind { item?.subjectCreditsProperty }
    val hoursPerWeek = bind { item?.hoursPerWeekProperty }
    val pricePerMonth = bind { item?.pricePerMonthProperty }

    var totalPricePerMonthExpenses = itemProperty.select (SubjectEntry::totalPricePerMonthExpenses)

}