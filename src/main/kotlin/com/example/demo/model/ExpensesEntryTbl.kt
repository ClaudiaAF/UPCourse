package com.example.demo.model

import javafx.beans.property.SimpleDoubleProperty
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleStringProperty
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import java.math.BigDecimal
import tornadofx.*
import javax.script.Bindings
import javafx.beans.binding.Bindings.add

object ExpensesEntryTbl : Table() {
    val id: Column<Int> = integer("id").autoIncrement().primaryKey()
    val entryDate: Column<String> = varchar("entry_date", length = 50)
    val itemName: Column<String> = varchar("name", length = 50)
    val itemPrice: Column<BigDecimal> = decimal("price", scale=2, precision = 9)
}

fun ResultRow.toExpensesEntry() = ExpensesEntry (
        this[ExpensesEntryTbl.id],
        this[ExpensesEntryTbl.entryDate],
        this[ExpensesEntryTbl.itemName],
        this[ExpensesEntryTbl.itemPrice].toDouble()
    )

//items to be entered
class ExpensesEntry(id: Int, entryDate: String, itemName: String, itemPrice: Double) {
    val idProperty = SimpleIntegerProperty(id)
    var id by idProperty

    val itemNameProperty = SimpleStringProperty(itemName)
    var itemName by itemNameProperty

    val entryDateProperty = SimpleStringProperty(entryDate)
    var entryDate by entryDateProperty

    val itemPriceProperty = SimpleDoubleProperty(itemPrice)
    var itemPrice by itemPriceProperty

    var totalExpenses = javafx.beans.binding.Bindings.add(itemPriceProperty, 0)

    //returning string for when object is added
    override fun toString(): String {
        return "ExpensesEntry(id=$id, entryDate=$entryDate, itemName=$itemName, itemPrice=$itemPrice)"
    }


}

class ExpensesEntryModel : ItemViewModel<ExpensesEntry>() {
    val id = bind { item?.idProperty }
    val entryDate = bind { item?.entryDateProperty }
    val itemName = bind { item?.itemNameProperty }
    val itemPrice = bind { item?.itemPriceProperty }
    var totalExpenses = itemProperty.select (ExpensesEntry::totalExpenses)
}