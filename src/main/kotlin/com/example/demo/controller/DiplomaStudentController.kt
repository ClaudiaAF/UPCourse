package com.example.demo.controller

import com.example.demo.model.*
import com.example.demo.util.execute
import javafx.collections.ObservableList
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.update
import tornadofx.*
import java.math.BigDecimal

class DiplomaStudentController : Controller() {

    private val listOfDiplomaStudents: ObservableList<DiplomaStudentsEntryModel> = execute {
        DiplomaStudentsTbl.selectAll().map {
            DiplomaStudentsEntryModel().apply {
                item = it.toDiplomaStudentsEntry()
            }
        }.observable()
    }

    var items: ObservableList<DiplomaStudentsEntryModel> by singleAssign()

    init {
        items = listOfDiplomaStudents
    }


    fun add(newDiplomaStudentName: String, newDiplomaStudentSurname: String, newDiplomaStudentSubject: String, newDiplomaStudentNumber: Int, newDiplomaStudentFees: Double) : DiplomaStudentsEntry {
        val newEntry = execute {
            DiplomaStudentsTbl.insert {
                it[diplomaStudentName] = newDiplomaStudentName
                it[diplomaStudentSurname] = newDiplomaStudentSurname
                it[diplomaStudentSubject] = newDiplomaStudentSubject
                it[diplomaStudentNumber] = newDiplomaStudentNumber
                it[diplomaStudentFees] = BigDecimal.valueOf(newDiplomaStudentFees)
            }
        }

        listOfDiplomaStudents.add(
            DiplomaStudentsEntryModel().apply {
                item = DiplomaStudentsEntry(newEntry[DiplomaStudentsTbl.diplomaStudentId], newDiplomaStudentName, newDiplomaStudentSurname, newDiplomaStudentSubject, newDiplomaStudentNumber, newDiplomaStudentFees)
            }
        )
        return DiplomaStudentsEntry(newEntry[DiplomaStudentsTbl.diplomaStudentId], newDiplomaStudentName, newDiplomaStudentSurname, newDiplomaStudentSubject, newDiplomaStudentNumber, newDiplomaStudentFees)
    }

    fun update(updatedItem: DiplomaStudentsEntryModel): Int {
        return execute {
            DiplomaStudentsTbl.update ({ DiplomaStudentsTbl.diplomaStudentId eq(updatedItem.diplomaStudentId.value.toInt()) }) {
                it[diplomaStudentName] = updatedItem.diplomaStudentName.value
                it[diplomaStudentSurname] = updatedItem.diplomaStudentSurname.value
                it[diplomaStudentSubject] = updatedItem.diplomaStudentSubject.value
                it[diplomaStudentNumber] = updatedItem.diplomaStudentNumber.value
                it[diplomaStudentFees] = BigDecimal.valueOf(updatedItem.diplomaStudentFees.value)
            }
        }
    }

    fun delete(model: DiplomaStudentsEntryModel) {
        execute {
            DiplomaStudentsTbl.deleteWhere {
                DiplomaStudentsTbl.diplomaStudentId eq (model.diplomaStudentId.value.toInt())
            }
        }
        listOfDiplomaStudents.remove(model)
    }
}