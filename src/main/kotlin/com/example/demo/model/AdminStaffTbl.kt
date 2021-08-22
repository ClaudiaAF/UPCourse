package com.example.demo.model

import javafx.beans.property.SimpleDoubleProperty
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleStringProperty
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import tornadofx.*
import java.math.BigDecimal

object AdminStaffTbl : Table() {
    val adminId: Column<Int> = integer("aId").autoIncrement().primaryKey()
    val adminName: Column<String> = varchar("aName", length = 50)
    val adminSurname: Column<String> = varchar("aSurname", length = 50)
    val adminRole: Column<String> = varchar("aRole", length = 50)
    val adminSalary: Column<BigDecimal> = decimal("aSalary", scale = 2, precision = 9)
}

fun ResultRow.toAdminEntry() = AdminEntry (
    this[AdminStaffTbl.adminId],
    this[AdminStaffTbl.adminName],
    this[AdminStaffTbl.adminSurname],
    this[AdminStaffTbl.adminRole],
    this[AdminStaffTbl.adminSalary].toDouble()
)

class AdminEntry(adminId: Int, adminName: String, adminSurname: String, adminRole: String, adminSalary: Double) {
    val adminIdProperty = SimpleIntegerProperty(adminId)
    var adminId by adminIdProperty

    val adminNameProperty = SimpleStringProperty(adminName)
    var adminName by adminNameProperty

    val adminSurnameProperty = SimpleStringProperty(adminSurname)
    var adminSurname by adminSurnameProperty

    val adminRoleProperty = SimpleStringProperty(adminRole)
    var adminRole by adminRoleProperty

    val adminSalaryProperty = SimpleDoubleProperty(adminSalary)
    var adminSalary by adminSalaryProperty

    var totalAdminSalaryExpenses = javafx.beans.binding.Bindings.add(adminSalaryProperty, 0)

    override fun toString(): String {
        return "AdminEntry(id=$adminId, name=$adminName, surname=$adminSurname, studentNumber=$adminRole, studentFees=$adminSalary)"
    }
}

class AdminEntryModel : ItemViewModel<AdminEntry>() {
    val adminId = bind{ item?.adminIdProperty }
    val adminName = bind { item?.adminNameProperty }
    val adminSurname = bind { item?.adminSurnameProperty }
    val adminRole = bind { item?.adminRoleProperty }
    val adminSalary = bind { item?.adminSalaryProperty }
    var totalAdminSalaryExpenses = itemProperty.select (AdminEntry::totalAdminSalaryExpenses)

}