package com.example.demo.controller

import com.example.demo.model.*
import com.example.demo.util.execute
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.scene.chart.PieChart
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.update
import tornadofx.*
import java.math.BigDecimal

class SubjectsController : Controller() {

    //get all items
    private val listOfSubjects: ObservableList<SubjectsEntryModel> = execute {
        SubjectsTbl.selectAll().map {
            SubjectsEntryModel().apply {
                item = it.toSubjectEntry()
            }
        }.observable()
    }
    var items: ObservableList<SubjectsEntryModel> by singleAssign()
    var pieItemsData = FXCollections.observableArrayList<PieChart.Data>()

    init {
        items = listOfSubjects


    }


    fun add(newSubjectName: String, newSubjectCode: String, newSubjectCredits: Int, newHoursPerWeek: Int, newPricePerMonth: Double) : SubjectEntry {
        val newEntry = execute {
            SubjectsTbl.insert {
                it[subjectName] = newSubjectName
                it[subjectCode] = newSubjectCode
                it[subjectCredits] = newSubjectCredits
                it[hoursPerWeek] = newHoursPerWeek
                it[pricePerMonth] = BigDecimal.valueOf(newPricePerMonth)
            }
        }

        listOfSubjects.add(
            SubjectsEntryModel().apply {
                item = SubjectEntry(newEntry[SubjectsTbl.subjectId], newSubjectName, newSubjectCode, newSubjectCredits, newHoursPerWeek, newPricePerMonth)
            }
        )

        return SubjectEntry(newEntry[SubjectsTbl.subjectId], newSubjectName, newSubjectCode, newSubjectCredits, newHoursPerWeek, newPricePerMonth)
    }

    fun update(updatedItem: SubjectsEntryModel): Int {
        return execute {
            SubjectsTbl.update ( { SubjectsTbl.subjectId eq(updatedItem.subjectId.value.toInt()) }) {
                it[subjectName] = updatedItem.subjectName.value
                it[subjectCode] = updatedItem.subjectCode.value
                it[subjectCredits] = updatedItem.subjectCredits.value
                it[hoursPerWeek] = updatedItem.hoursPerWeek.value
                it[pricePerMonth] = BigDecimal.valueOf(updatedItem.pricePerMonth.value.toDouble())
            }
        }
    }

    fun delete(model: SubjectsEntryModel) {
        execute {
            SubjectsTbl.deleteWhere {
                SubjectsTbl.subjectId eq (model.subjectId.value.toInt())
            }
        }
        listOfSubjects.remove(model)
    }

}