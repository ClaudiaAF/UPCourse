package com.example.demo.controller

import com.example.demo.model.*
import com.example.demo.util.execute
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.scene.chart.PieChart
import org.jetbrains.exposed.sql.selectAll
import tornadofx.*

class TotalAmountsController : Controller() {

    private val listOfItems: ObservableList<DiplomaStudentsEntryModel> = execute {
        DiplomaStudentsTbl.selectAll().map {
            DiplomaStudentsEntryModel().apply {
                item = it.toDiplomaStudentsEntry()
            }
        }.observable()
    }
    var pieItemsData = FXCollections.observableArrayList<PieChart.Data>()
    var items: ObservableList<DiplomaStudentsEntryModel> by singleAssign()
    var mTableView: TableViewEditModel<LecturerEntryModel> by singleAssign()

    init {

    }

}