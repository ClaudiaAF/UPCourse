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

class AdminController : Controller() {

    //get all items
    private val listOfAdmin: ObservableList<AdminEntryModel> = execute {
        AdminStaffTbl.selectAll().map {
            AdminEntryModel().apply {
                item = it.toAdminEntry()
            }
        }.observable()
    }
    var items: ObservableList<AdminEntryModel> by singleAssign()
    var pieItemsData = FXCollections.observableArrayList<PieChart.Data>()

    init {
        items = listOfAdmin
        items.forEach {
            pieItemsData.add(PieChart.Data(it.adminName.value, it.adminSalary.value.toDouble()))
        }


    }


    fun add(newAdminName: String, newAdminSurname: String, newAdminRole: String, newAdminSalary: Double) : AdminEntry {
        val newEntry = execute {
            AdminStaffTbl.insert {
                it[adminName] = newAdminName
                it[adminSurname] = newAdminSurname
                it[adminRole] = newAdminRole
                it[adminSalary] = BigDecimal.valueOf(newAdminSalary)
            }
        }

        listOfAdmin.add(
            AdminEntryModel().apply {
                item = AdminEntry(newEntry[AdminStaffTbl.adminId], newAdminName, newAdminSurname, newAdminRole, newAdminSalary)
            }
        )

        return AdminEntry(newEntry[AdminStaffTbl.adminId], newAdminName, newAdminSurname, newAdminRole, newAdminSalary)
    }

    fun update(updatedItem: AdminEntryModel): Int {
        return execute {
            AdminStaffTbl.update ( { AdminStaffTbl.adminId eq(updatedItem.adminId.value.toInt()) }) {
                it[adminName] = updatedItem.adminName.value
                it[adminSurname] = updatedItem.adminSurname.value
                it[adminRole] = updatedItem.adminRole.value
                it[adminSalary] = BigDecimal.valueOf(updatedItem.adminSalary.value.toDouble())
            }
        }
    }

    fun delete(model: AdminEntryModel) {
        execute {
            AdminStaffTbl.deleteWhere {
                AdminStaffTbl.adminId eq (model.adminId.value.toInt())
            }
        }
        listOfAdmin.remove(model)
    }

}