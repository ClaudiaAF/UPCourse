package com.example.demo.controller

import com.example.demo.model.DegreeStudentsEntry
import com.example.demo.model.DegreeStudentsEntryModel
import com.example.demo.model.DegreeStudentsTbl
import com.example.demo.model.toDegreeStudentsEntry
import com.example.demo.util.execute
import javafx.collections.ObservableList
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.update
import tornadofx.*
import java.math.BigDecimal

class DegreeStudentController : Controller() {

    private val listOfDegreeStudents: ObservableList<DegreeStudentsEntryModel> = execute {
        DegreeStudentsTbl.selectAll().map {
            DegreeStudentsEntryModel().apply {
                item = it.toDegreeStudentsEntry()
            }
        }.observable()
    }

    var items: ObservableList<DegreeStudentsEntryModel> by singleAssign()

    init {
        items = listOfDegreeStudents
    }


    fun add(newDegreeStudentName: String, newDegreeStudentSurname: String, newDegreeStudentNumber: Int, newDegreeStudentFees: Double) : DegreeStudentsEntry {
        val newEntry = execute {
            DegreeStudentsTbl.insert {
                it[degreeStudentName] = newDegreeStudentName
                it[degreeStudentSurname] = newDegreeStudentSurname
                it[degreeStudentNumber] = newDegreeStudentNumber
                it[degreeStudentFees] = BigDecimal.valueOf(newDegreeStudentFees)
            }
        }

        listOfDegreeStudents.add(
            DegreeStudentsEntryModel().apply {
                item = DegreeStudentsEntry(newEntry[DegreeStudentsTbl.degreeStudentId], newDegreeStudentName, newDegreeStudentSurname, newDegreeStudentNumber, newDegreeStudentFees)
            }
        )
        return DegreeStudentsEntry(newEntry[DegreeStudentsTbl.degreeStudentId], newDegreeStudentName, newDegreeStudentSurname, newDegreeStudentNumber, newDegreeStudentFees)
    }

    fun update(updatedItem: DegreeStudentsEntryModel): Int {
        return execute {
            DegreeStudentsTbl.update ({ DegreeStudentsTbl.degreeStudentId eq(updatedItem.degreeStudentId.value.toInt()) }) {
                it[degreeStudentName] = updatedItem.degreeStudentName.value
                it[degreeStudentSurname] = updatedItem.degreeStudentSurname.value
                it[degreeStudentNumber] = updatedItem.degreeStudentNumber.value
                it[degreeStudentFees] = BigDecimal.valueOf(updatedItem.degreeStudentFees.value)
            }
        }
    }

    fun delete(model: DegreeStudentsEntryModel) {
        execute {
            DegreeStudentsTbl.deleteWhere {
                DegreeStudentsTbl.degreeStudentId eq (model.degreeStudentId.value.toInt())
            }
        }
        listOfDegreeStudents.remove(model)
    }
}