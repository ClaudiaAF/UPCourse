package com.example.demo.controller

import com.example.demo.model.ExpensesEntry
import com.example.demo.model.ExpensesEntryModel
import com.example.demo.model.ExpensesEntryTbl
import com.example.demo.model.toExpensesEntry
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

class ItemController : Controller() {

    //get all items
    private val listOfItems: ObservableList<ExpensesEntryModel> = execute {
        ExpensesEntryTbl.selectAll().map {
            ExpensesEntryModel().apply {
                item = it.toExpensesEntry()
            }
        }.observable()
    }
    var items: ObservableList<ExpensesEntryModel> by singleAssign()
    var pieItemsData = FXCollections.observableArrayList<PieChart.Data>()

    init {
        items = listOfItems
        items.forEach {
            pieItemsData.add(PieChart.Data(it.itemName.value, it.itemPrice.value.toDouble()))
        }


    }


    fun add(newEntryDate: String, newItem: String, newPrice: Double) : ExpensesEntry {
        val newEntry = execute {
            ExpensesEntryTbl.insert {
                it[entryDate] = newEntryDate
                it[itemName] = newItem
                it[itemPrice] = BigDecimal.valueOf(newPrice)
            }
        }

        listOfItems.add(
            ExpensesEntryModel().apply {
                item = ExpensesEntry(newEntry[ExpensesEntryTbl.id], newEntryDate, newItem, newPrice)
            }
        )
        pieItemsData.add(PieChart.Data(newItem, newPrice))

        return ExpensesEntry(newEntry[ExpensesEntryTbl.id], newEntryDate, newItem, newPrice)
    }

    fun update(updatedItem: ExpensesEntryModel): Int {
        return execute {
            ExpensesEntryTbl.update ( { ExpensesEntryTbl.id eq(updatedItem.id.value.toInt()) }) {
                it[entryDate] = updatedItem.entryDate.value
                it[itemName] = updatedItem.itemName.value
                it[itemPrice] = BigDecimal.valueOf(updatedItem.itemPrice.value.toDouble())
            }
        }
    }

    fun delete(model: ExpensesEntryModel) {
        execute {
            ExpensesEntryTbl.deleteWhere {
                ExpensesEntryTbl.id eq (model.id.value.toInt())
            }
        }
        listOfItems.remove(model)
        removeModelFromPie(model)
    }

    fun updatePiecePie(model: ExpensesEntryModel) {
        val modelId = model.id
        var currIndex: Int
        items.forEachIndexed { index, data ->
            if (modelId == data.id) {
                //we have the correct object to update
                currIndex = index
                pieItemsData[currIndex].name = data.itemName.value
                pieItemsData[currIndex].pieValue = data.itemPrice.value.toDouble()
            } else {

            }
        }

    }

    private fun removeModelFromPie(model: ExpensesEntryModel) {
        var currIndex = 0

        pieItemsData.forEachIndexed { index, data ->
            if(data.name == model.itemName.value && index != -1) {
                currIndex = index
            }
        }
        pieItemsData.removeAt(currIndex)
    }
}