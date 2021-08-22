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

class LecturerController : Controller() {

    //get all items
    private val listOfLecturers: ObservableList<LecturerEntryModel> = execute {
        LecturerStaffTbl.selectAll().map {
            LecturerEntryModel().apply {
                item = it.toLecturerEntry()
            }
        }.observable()
    }
    var items: ObservableList<LecturerEntryModel> by singleAssign()
    var pieItemsData = FXCollections.observableArrayList<PieChart.Data>()

    init {
        items = listOfLecturers


    }


    fun add(newLecturerName: String, newLecturerSurname: String, newLecturerSubject: String, newLecturerSalary: Double) : LecturerEntry {
        val newEntry = execute {
            LecturerStaffTbl.insert {
                it[lecturerName] = newLecturerName
                it[lecturerSurname] = newLecturerSurname
                it[lecturerSubject] = newLecturerSubject
                it[lecturerSalary] = BigDecimal.valueOf(newLecturerSalary)
            }
        }

        listOfLecturers.add(
            LecturerEntryModel().apply {
                item = LecturerEntry(newEntry[LecturerStaffTbl.lecturerId], newLecturerName, newLecturerSurname, newLecturerSubject, newLecturerSalary)
            }
        )

        return LecturerEntry(newEntry[LecturerStaffTbl.lecturerId], newLecturerName, newLecturerSurname, newLecturerSubject, newLecturerSalary)
    }

    fun update(updatedItem: LecturerEntryModel): Int {
        return execute {
            LecturerStaffTbl.update ( { LecturerStaffTbl.lecturerId eq(updatedItem.lecturerId.value.toInt()) }) {
                it[lecturerName] = updatedItem.lecturerName.value
                it[lecturerSurname] = updatedItem.lecturerSurname.value
                it[lecturerSubject] = updatedItem.lecturerSubject.value
                it[lecturerSalary] = BigDecimal.valueOf(updatedItem.lecturerSubject.value.toDouble())
            }
        }
    }

    fun delete(model: LecturerEntryModel) {
        execute {
            LecturerStaffTbl.deleteWhere {
                LecturerStaffTbl.lecturerId eq (model.lecturerId.value.toInt())
            }
        }
        listOfLecturers.remove(model)
    }

}